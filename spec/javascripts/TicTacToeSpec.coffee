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
  xit "should call ajax", ->
    spyOn $, "ajax"
    expect($.ajax).toHaveBeenCalled

  xit "should call ajax with the board state and marker", ->
    spyOn $, "ajax"
    TicTacToe.makeMove "X", "_________"
    expect($.ajax.mostRecentCall.args[0].data.marker).toBe("X")
    expect($.ajax.mostRecentCall.args[0].data.board_state).toBe("_________")

  xit "should send an asynchronous POST request to /game", ->
    spyOn $, "ajax"
    TicTacToe.makeMove "X", "_________"
    expect($.ajax.mostRecentCall.args[0].async).toBe(true)
    expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
    expect($.ajax.mostRecentCall.args[0].url).toBe("/game")

  xit "should call replace board on success with response", ->
    spyOn TicTacToe, "replaceBoard"
    spyOn($, "ajax").andCallFake(
      (params) -> params.success("hey"))
    TicTacToe.makeMove "X", "_________"
    expect(TicTacToe.replaceBoard.mostRecentCall.args[0]).toBe("hey")

describe "initializeGame", ->
  beforeEach ->
    form = affix("form")
    form.affix('input[name="marker"][value="X"]:checked')
    form.affix('input[name="move"][value="0"]:checked')
    affix("#board")

  it "should send an asynchronous POST request to / and initialize game", ->
    spyOn($, "ajax").andCallFake(
      (params) -> params.success("O", "_________", -1, null))
    flag = false
    runs ->
      setTimeout((->
        TicTacToe.initializeGame
        flag = true), 0)

    waitsFor((-> flag),
             "Should call initialize game.", 1000)

    runs ->
      expect($.ajax).toHaveBeenCalled()
      expect($.ajax.mostRecentCall.args[0].async).toBe(true)
      expect($.ajax.mostRecentCall.args[0].dataType).toBe("json")
      expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
      expect($.ajax.mostRecentCall.args[0].url).toBe("/")
      expect($("form").css("display")).toBe("none")
      $(".button").each ->
        expect($(this).css("display")).toMatch("inline-block")
      expect($("#board").html()).toMatch("TicTacToe\.makeMove\\('X', '_________', 4\\)")

  xit "should call replace board on success with response", ->
    spyOn TicTacToe, "replaceBoard"
    spyOn($, "ajax").andCallFake(
      (params) -> params.success("hey"))
    TicTacToe.initializeGame "X", "0"
    expect(TicTacToe.replaceBoard.mostRecentCall.args[0]).toBe("hey")
