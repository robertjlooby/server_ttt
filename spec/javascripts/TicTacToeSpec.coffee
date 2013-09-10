describe "request builder", ->
  it "should construct an XMLHttpRequest object", ->
    expect(TicTacToe.requestBuilder() instanceof XMLHttpRequest).toBe(true)

  it "should be opened but not sent", ->
    expect(TicTacToe.requestBuilder().readyState).toBe(1)

  it "can see method?", ->
    expect(TicTacToe.requestBuilder().getMethod).toBe("GET")

describe "makeMove", ->
  it "should call with the next board state string", ->
    expect(TicTacToe.makeMove("O", "X________")).toBe("X________")
