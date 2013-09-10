describe("makeMove", function() {
  it("should call with the next board state string", function() {
    /*spyOn(TicTacToe, "makeMove");*/
    expect(TicTacToe.makeMove("X________")).toBe("X________");
    /*expect(TicTacToe.makeMove).toHaveBeenCalled();*/
  });
});
