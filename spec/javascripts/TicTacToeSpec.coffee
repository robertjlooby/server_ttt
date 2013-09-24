describe "TicTacToe.resetBoard", ->
  beforeEach ->
    affix("#board")
    spyOn(TicTacToe.display, "resetBoard")

  xit "should reset TicTacToe.boardState to an empty board", ->
    TicTacToe.boardState = "blahblah"
    TicTacToe.resetBoard "X"
    expect(TicTacToe.boardState).toBe("_________")

  xit "should reset TicTacToe.buttonsEnabled to false", ->
    TicTacToe.buttonsEnabled = true
    TicTacToe.resetBoard "X"
    expect(TicTacToe.buttonsEnabled).toBe(false)

  xit "should pass event handler to display.resetBoard which calls makeMove", ->
    spyOn(TicTacToe, "makeMove")
    TicTacToe.resetBoard "X"
    eventFn = TicTacToe.display.resetBoard.calls[0].args[0]
    expect(eventFn(0)).toBe(undefined)
    TicTacToe.buttonsEnabled = true
    eventFn(0)
    expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0)

describe "TicTacToe.displayForm", ->
  xit "passes an event handler to call initializeGame to  display.displayForm()", ->
    spyOn(TicTacToe, "initializeGame")
    spyOn(TicTacToe.display, "displayForm")
    TicTacToe.displayForm()
    expect(TicTacToe.initializeGame).not.toHaveBeenCalled()
    TicTacToe.display.displayForm.calls[0].args[0]()
    expect(TicTacToe.initializeGame).toHaveBeenCalled()

describe "TicTacToe.disable/enable Buttons", ->
  eventFn = null

  beforeEach ->
    spyOn(TicTacToe, "makeMove")
    spyOn(TicTacToe.display, "resetBoard")
    TicTacToe.resetBoard "X"
    eventFn = TicTacToe.display.resetBoard.calls[0].args[0]

  xit "buttons should do nothing by default", ->
    eventFn(0)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

  xit "should enable all buttons", ->
    TicTacToe.enableButtons()
    eventFn(0)
    expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0)

  xit "should be able to disable buttons again", ->
    TicTacToe.enableButtons()
    TicTacToe.disableButtons()
    eventFn(0)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

describe "TicTacToe.getNewBoardState", ->
  xit "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")
describe "TicTacToe.getNewBoardState", ->
  xit "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")

  xit "should replace an element in the middle", ->
    expect(TicTacToe.getNewBoardState("O", "X_OX_____", 6)).toBe("X_OX__O__")

  xit "should replace an element at the end", ->
    expect(TicTacToe.getNewBoardState("X", "X_OX__O__", 8)).toBe("X_OX__O_X")

describe "TicTacToe.updateBoardHuman", ->
  beforeEach ->
    spyOn(TicTacToe.display, "makeMove")
  
  xit "should call display.makeMove with the given marker/cell", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect(TicTacToe.display.makeMove).toHaveBeenCalledWith("X", 0)

  xit "should disable buttons", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect(TicTacToe.buttonsEnabled).toBe(false)

  xit "should update the boardState", ->
    TicTacToe.updateBoardHuman "X", "X________", 0
    expect(TicTacToe.boardState).toBe("X________")

describe "TicTacToe.updateBoardAI", ->
  beforeEach ->
    TicTacToe.buttonsEnabled = false
    spyOn(TicTacToe.display, "displayWinMessage")
    spyOn(TicTacToe.display, "displayLoseMessage")
    spyOn(TicTacToe.display, "displayTieMessage")
    spyOn(TicTacToe.display, "makeMove")
    spyOn(TicTacToe, "displayForm")

  xit "should call display.makeMove with the given marker/cell", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect(TicTacToe.display.makeMove).toHaveBeenCalledWith("O", 4)

  xit "should update the boardState", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect(TicTacToe.boardState).toBe("X___O____")

  xit "should enable buttons again", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect(TicTacToe.buttonsEnabled).toBe(true)

  xit "should display win message and form when player wins, buttons should be disabled", ->
    TicTacToe.updateBoardAI "O", "X_OOX_XOX", 7, "W"
    expect(TicTacToe.display.displayWinMessage).toHaveBeenCalled()
    expect(TicTacToe.displayForm).toHaveBeenCalled()
    expect(TicTacToe.buttonsEnabled).toBe(false)

  xit "should display lose message and form when player loses, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "X_OOX_XOX", 8, "L"
    expect(TicTacToe.display.displayLoseMessage).toHaveBeenCalled()
    expect(TicTacToe.displayForm).toHaveBeenCalled()
    expect(TicTacToe.buttonsEnabled).toBe(false)

  xit "should display tie message and form when player ties, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "XOXOOXXXO", 5, "T"
    expect(TicTacToe.display.displayTieMessage).toHaveBeenCalled()
    expect(TicTacToe.displayForm).toHaveBeenCalled()
    expect(TicTacToe.buttonsEnabled).toBe(false)

describe "TicTacToe.makeMove", ->
  xit "should send an asynchronous POST request to /game", ->
    flag = false
    spyOn(TicTacToe, "updateBoardHuman")
    spyOn(TicTacToe, "updateBoardAI")
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "X___O____", "aiMove": 4, "result": null})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($.ajax).toHaveBeenCalled()
      expect($.ajax.mostRecentCall.args[0].async).toBe(true)
      expect($.ajax.mostRecentCall.args[0].dataType).toBe("json")
      expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
      expect($.ajax.mostRecentCall.args[0].url).toBe("/game")
      expect(TicTacToe.updateBoardHuman).toHaveBeenCalledWith("X", "X________", 0)
      expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "X___O____", 4, null)

    TicTacToe.makeMove("X", "_________", 0)

  xit "should disable buttons while waiting for server, enable buttons when move is returned", ->
    flag = false
    TicTacToe.buttonsEnabled = true
    spyOn(TicTacToe.display, "makeMove")
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "X___O____", "aiMove": 4, "result": null})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect(TicTacToe.buttonsEnabled).toBe(true)

    expect(TicTacToe.buttonsEnabled).toBe(true)
    TicTacToe.makeMove("X", "_________", 0)
    expect(TicTacToe.buttonsEnabled).toBe(false)

describe "TicTacToe.initializeGame", ->
  beforeEach ->
    spyOn(TicTacToe, "resetBoard")
    spyOn(TicTacToe, "updateBoardAI")
    spyOn(TicTacToe.display, "getMarker").andReturn("X")
    spyOn(TicTacToe.display, "getMove").andReturn("0")

  xit "should send an asynchronous POST request to / and initialize game", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
            params.success({"boardState": "_________", "aiMove": -1, "result": null})
            flag = true)
          0))

    waitsFor((-> flag),
             "Should call initialize game.", 1000)

    runs ->
      expect($.ajax).toHaveBeenCalled()
      expect($.ajax.mostRecentCall.args[0].async).toBe(true)
      expect($.ajax.mostRecentCall.args[0].dataType).toBe("json")
      expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
      expect($.ajax.mostRecentCall.args[0].url).toBe("/")
      expect(TicTacToe.resetBoard).toHaveBeenCalledWith("X")
      expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "_________", -1, null)

    TicTacToe.initializeGame()

  xit "should initialize game when moving second", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
            params.success({"boardState": "O________", "aiMove": 0, "result": null})
            flag = true)
          0))

    waitsFor((-> flag),
             "Should call initialize game.", 1000)

    runs ->
      expect(TicTacToe.resetBoard).toHaveBeenCalledWith("X")
      expect(TicTacToe.updateBoardAI).toHaveBeenCalledWith("O", "O________", 0, null)

    TicTacToe.initializeGame()
