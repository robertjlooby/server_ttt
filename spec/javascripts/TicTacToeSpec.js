describe("makeMove", function() {
  it("should call with the next board state string", function() {
    expect(TicTacToe.makeMove("X________")).toBe("X________");
  });
});
