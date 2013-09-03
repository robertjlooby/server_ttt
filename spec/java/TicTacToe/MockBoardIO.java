package TicTacToe;

public class MockBoardIO implements BoardIO {
    private int timesPrintedBoard;

    public MockBoardIO(){
        timesPrintedBoard = 0;
    }

    @Override
    public void printBoard(BoardMarker[][] boardState, BoardMarker zeroSymbol, BoardMarker oneSymbol) {
        ++timesPrintedBoard;
    }

    public int getTimesPrintedBoard(){
        return timesPrintedBoard;
    }
}
