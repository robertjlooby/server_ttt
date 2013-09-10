describe "makeMove", ->
  it "should call with the next board state string", ->
    expect(TicTacToe.makeMove("X________")).toBe("X________")
