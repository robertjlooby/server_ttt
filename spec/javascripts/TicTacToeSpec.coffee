describe "resetBoard", ->
  beforeEach ->
    affix("#board")

  it "should reset the contents of #board", ->
    TicTacToe.resetBoard "X"
    expect($("div#board").html()).toMatch("class=\"button\">0<")
    expect($("div#board").html()).toMatch("<div id=\"row2\">")
    expect($("div#board").html()).toMatch("<div id=\"cell8\">")

  it "should reset TicTacToe.boardState to an empty board", ->
    TicTacToe.boardState = "blahblah"
    TicTacToe.resetBoard "X"
    expect(TicTacToe.boardState).toBe("_________")

  it "should reset TicTacToe.buttonsEnabled to false", ->
    TicTacToe.buttonsEnabled = true
    TicTacToe.resetBoard "X"
    expect(TicTacToe.buttonsEnabled).toBe(false)

describe "button clickability", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard "X"

  it "buttons should do nothing by default", ->
    initialBoard = $("#board").html()
    spyOn(TicTacToe, "makeMove")
    $(".button").each ->
      $(this).click()
      expect($("#board").html()).toBe(initialBoard)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

  it "should enable all buttons", ->
    spyOn(TicTacToe, "makeMove")
    TicTacToe.enableButtons()
    $(".button").each ->
      $(this).click()
    expect(TicTacToe.makeMove.calls.length).toBe(9)
    for cell in [0..8]
      expect(TicTacToe.makeMove).toHaveBeenCalledWith("X", "_________", cell)

  it "should be able to disable buttons again", ->
    initialBoard = $("#board").html()
    spyOn(TicTacToe, "makeMove")
    TicTacToe.enableButtons()
    TicTacToe.disableButtons()
    $(".button").each ->
      $(this).click()
      expect($("#board").html()).toBe(initialBoard)
    expect(TicTacToe.makeMove).not.toHaveBeenCalled()

describe "getNewBoardState", ->
  it "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")
describe "getNewBoardState", ->
  it "should replace first element", ->
    expect(TicTacToe.getNewBoardState("X", "__O______", 0)).toBe("X_O______")

  it "should replace an element in the middle", ->
    expect(TicTacToe.getNewBoardState("O", "X_OX_____", 6)).toBe("X_OX__O__")

  it "should replace an element at the end", ->
    expect(TicTacToe.getNewBoardState("X", "X_OX__O__", 8)).toBe("X_OX__O_X")

describe "setUpBoard", ->
  beforeEach ->
    affix("form")
    affix("#board")

  it "should hide the form", ->
    TicTacToe.setUpBoard("X")
    expect($("form").css("display")).toBe("none")

  it "should reset the board", ->
    TicTacToe.setUpBoard("X")
    expect($(".button").size()).toBe(9)
    expect(TicTacToe.boardState).toBe("_________")

describe "updateBoardHuman", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard "X"
  
  it "should make the human move in a given cell", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect($("#cell0").html()).toBe("X")

  it "should disable buttons", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should update the boardState", ->
    TicTacToe.updateBoardHuman "X", "X________", 0
    expect(TicTacToe.boardState).toBe("X________")

describe "updateBoardAI", ->
  beforeEach ->
    affix("form")
    $("form").hide()
    affix("#board")
    TicTacToe.resetBoard "X"

  it "should make the aiMove in the given cell", ->
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
    expect($("form").css("display")).toBe("block")
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should display lose message and form when player loses, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "X_OOX_XOX", 8, "L"
    expect($("h1").html()).toMatch("Lose")
    expect($("form").css("display")).toBe("block")
    expect(TicTacToe.buttonsEnabled).toBe(false)

  it "should display tie message and form when player ties, buttons should be disabled", ->
    TicTacToe.updateBoardAI "X", "XOXOOXXXO", 5, "T"
    expect($("h1").html()).toMatch("Tie")
    expect($("form").css("display")).toBe("block")
    expect(TicTacToe.buttonsEnabled).toBe(false)

describe "makeMove", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard("X")
    TicTacToe.enableButtons()
    affix("form").hide()

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
      expect($("form").css("display")).toBe("none")
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
      expect($("form").css("display")).toBe("block")
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
      expect($("form").css("display")).toBe("block")
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
      expect($("form").css("display")).toBe("block")
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
      expect($("form").css("display")).toBe("block")
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

describe "initializeGame", ->
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
      expect($("form").css("display")).toBe("none")
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
