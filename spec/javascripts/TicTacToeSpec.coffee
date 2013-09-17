describe "resetBoard", ->
  beforeEach ->
    affix("#board")

  it "should reset the contents of #board", ->
    TicTacToe.resetBoard "X"
    expect($("div#board").html()).toMatch("TicTacToe\.makeMove\\('X', '_________', 4\\)")

  it "should reset the contents of #board with the given marker", ->
    TicTacToe.resetBoard "O"
    expect($("div#board").html()).toMatch("TicTacToe\.makeMove\\('O', '_________', 8\\)")

describe "button visibility", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard "X"

  it "should hide all .button 's", ->
    TicTacToe.hideButtons()
    $(".button").each ->
      expect($(this).css("display")).toBe("none")

  it "should show all .button 's", ->
    TicTacToe.hideButtons()
    TicTacToe.showButtons()
    $(".button").each ->
      expect($(this).css("display")).toBe("inline-block")

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

  it "should hide all .button 's", ->
    TicTacToe.setUpBoard("X")
    $(".button").each ->
      expect($(this).css("display")).toBe("none")

describe "updateBoardHuman", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard "X"
  
  it "should make the human move in a given cell", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    expect($("#cell0").html()).toBe("X")

  it "should hide all the buttons", ->
    TicTacToe.updateBoardHuman "X", "_________", 0
    $(".button").each ->
      expect($(this).css("display")).toMatch("none")

  it "should update the boardState in all the buttons", ->
    TicTacToe.updateBoardHuman "X", "X________", 0
    $(".button").each ->
      expect($(this).attr("onclick")).toMatch("X________")

describe "updateBoardAI", ->
  beforeEach ->
    affix("form")
    $("form").hide()
    affix("#board")
    TicTacToe.resetBoard "X"
    TicTacToe.hideButtons()

  it "should make the aiMove in the given cell", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    expect($("#cell4").html()).toBe("O")

  it "should update the boardState in all the buttons", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    $(".button").each ->
      expect($(this).attr("onclick")).toMatch("X___O____")

  it "should make all buttons visible again", ->
    TicTacToe.updateBoardAI "O", "X___O____", 4, null
    $(".button").each ->
      expect($(this).css("display")).toMatch("inline-block")

  it "should display win message and form when player wins, not buttons", ->
    TicTacToe.updateBoardAI "O", "X_OOX_XOX", 7, "W"
    expect($("h1").html()).toMatch("Win")
    expect($("form").css("display")).toBe("block")
    $(".button").each ->
      expect($(this).css("display")).toMatch("none")

  it "should display lose message and form when player loses, not buttons", ->
    TicTacToe.updateBoardAI "X", "X_OOX_XOX", 8, "L"
    expect($("h1").html()).toMatch("Lose")
    expect($("form").css("display")).toBe("block")
    $(".button").each ->
      expect($(this).css("display")).toMatch("none")

  it "should display tie message and form when player ties, not buttons", ->
    TicTacToe.updateBoardAI "X", "XOXOOXXXO", 5, "T"
    expect($("h1").html()).toMatch("Tie")
    expect($("form").css("display")).toBe("block")
    $(".button").each ->
      expect($(this).css("display")).toMatch("none")

describe "makeMove", ->
  beforeEach ->
    affix("#board")
    TicTacToe.resetBoard("X")
    affix("form").hide()

  it "should send an asynchronous POST request to /game", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "X___O____", "aiMove": 4, "result": null}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.makeMove("X", "_________", 0)
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($.ajax).toHaveBeenCalled()
      expect($.ajax.mostRecentCall.args[0].async).toBe(true)
      expect($.ajax.mostRecentCall.args[0].dataType).toBe("json")
      expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
      expect($.ajax.mostRecentCall.args[0].url).toBe("/game")
      expect($("form").css("display")).toBe("none")
      expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', 'X___O____', 3\\)")
      $(".button").each ->
        expect($(this).css("display")).toBe("inline-block")

  it "show 'Tie'/form and not buttons for tie", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "XOXXOOOXX", "aiMove": -1, "result": "T"}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.makeMove("X", "XOXXOOOX_", 8)
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Tie")
      expect($("form").css("display")).toBe("block")
      $(".button").each ->
        expect($(this).css("display")).toBe("none")

  it "show 'Tie'/form and not buttons for tie after AI move", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "OXOOXXXO_", "aiMove": 8, "result": "T"}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.makeMove("X", "OXOOXX_O_", 6)
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Tie")
      expect($("form").css("display")).toBe("block")
      $(".button").each ->
        expect($(this).css("display")).toBe("none")

  it "show 'Win'/form and not buttons for player win", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "X_OOO_XXX", "aiMove": -1, "result": "W"}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.makeMove("X", "X_OOO_X_X", 7)
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Win")
      expect($("form").css("display")).toBe("block")
      $(".button").each ->
        expect($(this).css("display")).toBe("none")

  it "show 'Lose'/form and not buttons for player loss", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "OXXOO_O_X", "aiMove": 3, "result": "L"}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.makeMove("X", "OX__O_O_X", 2)
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call makeMove.", 1000)

    runs ->
      expect($("h1").html()).toMatch("Lose")
      expect($("form").css("display")).toBe("block")
      $(".button").each ->
        expect($(this).css("display")).toBe("none")

describe "initializeGame", ->
  beforeEach ->
    form = affix("form")
    form.affix('input[name="marker"][type="radio"][value="X"]').prop("checked", true)
    form.affix('input[name="move"][type="radio"][value="0"]').prop("checked", true)
    affix("#board")

  it "should send an asynchronous POST request to / and initialize game", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "_________", "aiMove": -1, "result": null}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.initializeGame()
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call initialize game.", 1000)

    runs ->
      expect($.ajax).toHaveBeenCalled()
      expect($.ajax.mostRecentCall.args[0].async).toBe(true)
      expect($.ajax.mostRecentCall.args[0].dataType).toBe("json")
      expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
      expect($.ajax.mostRecentCall.args[0].url).toBe("/")
      expect($("form").css("display")).toBe("none")
      expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', '_________', 4\\)")
      $(".button").each ->
        expect($(this).css("display")).toBe("inline-block")

  it "should initialize game when moving second", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success({"boardState": "O________", "aiMove": 0, "result": null}))
    flag = false
    runs ->
      setTimeout((->
                    TicTacToe.initializeGame()
                    flag = true),
                  0)

    waitsFor((-> flag),
             "Should call initialize game.", 1000)

    runs ->
      expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', 'O________', 4\\)")
