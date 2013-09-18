describe "TicTacToe.resetBoard", ->
  beforeEach ->
    affix("#board")
    spyOn(TicTacToe.display, "resetBoard")

  it "should reset TicTacToe.boardState to an empty board", ->
    TicTacToe.boardState = "blahblah"
    TicTacToe.resetBoard "X"
    expect(TicTacToe.boardState).toBe("_________")

  it "should reset TicTacToe.buttonsEnabled to false", ->
    TicTacToe.buttonsEnabled = true
    TicTacToe.resetBoard "X"
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should pass event handler to display.resetBoard which calls makeMove", ->
    spyOn(TicTacToe, "makeMove")
    TicTacToe.resetBoard "X"
    eventFn = TicTacToe.display.resetBoard.calls[0].args[0]
    expect(eventFn(0)).toBe(undefined)
    TicTacToe.buttonsEnabled = true
    eventFn(0)
    expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0)

describe "TicTacToe.displayForm", ->
  it "passes an event handler to call initializeGame to  display.displayForm()", ->
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

  it "buttons should do nothing by default", ->
    eventFn(0)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

  it "should enable all buttons", ->
    TicTacToe.enableButtons()
    eventFn(0)
    expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", 0)

  it "should be able to disable buttons again", ->
    TicTacToe.enableButtons()
    TicTacToe.disableButtons()
    eventFn(0)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

describe "TicTacToe.getNewBoardState", ->
  it "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")
describe "TicTacToe.getNewBoardState", ->
  it "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")

  it "should replace an element in the middle", ->
    expect(TicTacToe.getNewBoardState("O", "X_OX_____", 6)).toBe("X_OX__O__")

  it "should replace an element at the end", ->
    expect(TicTacToe.getNewBoardState("X", "X_OX__O__", 8)).toBe("X_OX__O_X")

describe "TicTacToe.setUpBoard", ->
  beforeEach ->
    affix("#board").affix("#newGameForm")

  it "should remove the form", ->
    TicTacToe.setUpBoard("X")
    expect($("#newGameForm").size()).toBe(0)

  it "should reset the board", ->
    TicTacToe.setUpBoard("X")
    expect($(".button").size()).toBe(9)
    expect(TicTacToe.boardState).toBe("_________")

describe "TicTacToe.updateBoardHuman", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#cell0")
  
  it "should make the human move in a given cell", ->
    spyOn(TicTacToe.display, "makeMove").andCallFake(
      (marker, cellNum) -> $("#cell#{cellNum}").html(marker))
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect($("#cell0").html()).toBe("X")

  it "should disable buttons", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should update the boardState", ->
    TicTacToe.updateBoardHuman "X", "X________", 0
    expect(TicTacToe.boardState).toBe("X________")

describe "TicTacToe.updateBoardAI", ->
  beforeEach ->
    board = affix("#board")
    board.affix("#cell4")
    TicTacToe.buttonsEnabled = false

  it "should make the aiMove in the given cell", ->
    spyOn(TicTacToe.display, "makeMove").andCallFake(
      (marker, cellNum) -> $("#cell#{cellNum}").html(marker))
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect($("#cell4").html()).toBe("O")

  it "should update the boardState", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect(TicTacToe.boardState).toBe("X___O____")

  it "should enable buttons again", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect(TicTacToe.buttonsEnabled).toBe(true)

  it "should display win message and form when player wins, buttons should be disabled", ->
    TicTacToe.updateBoardAI "O", "X_OOX_XOX", 7, "W"
    expect($("h1").html()).toMatch("Win")
    expect($("#board #newGameForm").size()).toBe(1)
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should display lose message and form when player loses, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "X_OOX_XOX", 8, "L"
    expect($("h1").html()).toMatch("Lose")
    expect($("#board #newGameForm").size()).toBe(1)
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should display tie message and form when player ties, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "XOXOOXXXO", 5, "T"
    expect($("h1").html()).toMatch("Tie")
    expect($("#board #newGameForm").size()).toBe(1)
    expect(TicTacToe.buttonsEnabled).toBe(false)

describe "TicTacToe.makeMove", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard("X")
    TicTacToe.enableButtons()

  it "should send an asynchronous POST request to /game", ->
    flag = false
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
      expect($("#board #newGameForm").size()).toBe(0)
      expect($("#cell0").html()).toBe("X")
      expect($("#cell4").html()).toBe("O")
      expect(TicTacToe.boardState).toBe("X___O____")
      expect(TicTacToe.buttonsEnabled).toBe(true)

    TicTacToe.makeMove("X", "_________", 0)

  it "show 'Tie'/form and disabled buttons for tie", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "XOXXOOOXX", "aiMove": -1, "result": "T"})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Tie")
      expect($("#board #newGameForm").size()).toBe(1)
      expect(TicTacToe.boardState).toBe("XOXXOOOXX")
      expect(TicTacToe.buttonsEnabled).toBe(false)
      
    TicTacToe.makeMove("X", "XOXXOOOX_", 0)

  it "show 'Tie'/form and disabled buttons for tie after AI move", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "OXOOXXXOO", "aiMove": 8, "result": "T"})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Tie")
      expect($("#board #newGameForm").size()).toBe(1)
      expect(TicTacToe.boardState).toBe("OXOOXXXOO")
      expect(TicTacToe.buttonsEnabled).toBe(false)

    TicTacToe.makeMove("X", "OXOOXX_O_", 6)

  it "show 'Win'/form and disabled buttons for player win", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "X_OOO_XXX", "aiMove": -1, "result": "W"})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Win")
      expect($("#board #newGameForm").size()).toBe(1)
      expect(TicTacToe.boardState).toBe("X_OOO_XXX")
      expect(TicTacToe.buttonsEnabled).toBe(false)

    TicTacToe.makeMove("X", "X_OOO_X_X", 7)

  it "show 'Lose'/form and disabled buttons for player loss", ->
    flag = false
    spyOn($, "ajax").andCallFake(
      (params) ->
        setTimeout(( ->
          params.success({"boardState": "OXXOO_O_X", "aiMove": 3, "result": "L"})
          flag = true)
          0))

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Lose")
      expect($("#board #newGameForm").size()).toBe(1)
      expect(TicTacToe.boardState).toBe("OXXOO_O_X")
      expect(TicTacToe.buttonsEnabled).toBe(false)

    TicTacToe.makeMove("X", "OX__O_O_X", 2)

  it "should disable buttons while waiting for server, enable buttons when move is returned", ->
    flag = false
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
    form = affix("form")
    form.affix('input[name="marker"][type="radio"][value="X"]').prop("checked", true)
    form.affix('input[name="move"][type="radio"][value="0"]').prop("checked", true)
    affix("#board")

  it "should send an asynchronous POST request to / and initialize game", ->
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
      expect($("#board #newGameForm").size()).toBe(0)
      expect(TicTacToe.boardState).toBe("_________")
      expect(TicTacToe.buttonsEnabled).toBe(true)

    TicTacToe.initializeGame()

  it "should initialize game when moving second", ->
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
      expect(TicTacToe.boardState).toBe("O________")
      expect(TicTacToe.buttonsEnabled).toBe(true)

    TicTacToe.initializeGame()
