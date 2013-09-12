describe "replaceBoard", ->
  it "finds the board", ->
    spy = spyOn $.fn, "find"
    TicTacToe.replaceBoard "hello board"
    expect(spy).toHaveBeenCalledWith("div#board")

  it "should replace the contents of #board with the given string", ->
    spy = spyOn $.fn, "html"
    TicTacToe.replaceBoard "hello board"
    expect(spy).toHaveBeenCalledWith("hello board")

describe "makeMove", ->
  it "should call ajax", ->
    spyOn $, "ajax"
    expect($.ajax).toHaveBeenCalled

  it "should call ajax with the board state and marker", ->
    spyOn $, "ajax"
    TicTacToe.makeMove "X", "_________"
    expect($.ajax.mostRecentCall.args[0].data.marker).toBe("X")
    expect($.ajax.mostRecentCall.args[0].data.board_state).toBe("_________")

  it "should get move asynchronously", ->
    spyOn $, "ajax"
    TicTacToe.makeMove "X", "_________"
    expect($.ajax.mostRecentCall.args[0].async).toBe(true)

  it "should send a POST request to /game", ->
    spyOn $, "ajax"
    TicTacToe.makeMove "X", "_________"
    expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
    expect($.ajax.mostRecentCall.args[0].url).toBe("/game")

  it "should call replace board on success with response", ->
    spyOn TicTacToe, "replaceBoard"
    spyOn($, "ajax").andCallFake(
      (params) -> params.success("hey"))
    TicTacToe.makeMove "X", "_________"
    expect(TicTacToe.replaceBoard.mostRecentCall.args[0]).toBe("hey")

describe "initializeGame", ->
  it "should send an asynchronous POST request to /", ->
    spyOn $, "ajax"
    spy = spyOn $.fn, "find"
    TicTacToe.initializeGame "X", "0"
    expect($.ajax.mostRecentCall.args[0].async).toBe(true)
    expect($.ajax.mostRecentCall.args[0].type).toBe("POST")
    expect($.ajax.mostRecentCall.args[0].url).toBe("/")
    expect(spy.calls[0].args[0]).toBe("input[name='marker']:checked")
    expect(spy.calls[1].args[0]).toBe("input[name='move']:checked")

  it "should call replace board on success with response", ->
    spyOn TicTacToe, "replaceBoard"
    spyOn($, "ajax").andCallFake(
      (params) -> params.success("hey"))
    TicTacToe.initializeGame "X", "0"
    expect(TicTacToe.replaceBoard.mostRecentCall.args[0]).toBe("hey")
