package TicTacToe;

public class MockPlayer implements Player {
    private int[] nextMove;
    private BoardMarker symbol;
    private int timesGetMove;

    public MockPlayer(BoardMarker sym){
        symbol = sym;
        timesGetMove = 0;
        nextMove = new int[]{0, 0};
    }
    @Override
    public BoardMarker getSymbol() {
        return symbol;
    }

    public void setNextMove(int[] move){
        nextMove = move;
    }
    @Override
    public int[] getMove(BoardMarker[][] boardState) {
        ++timesGetMove;
        return nextMove;
    }

    public int getTimesGetMove(){
        return timesGetMove;
    }
}
