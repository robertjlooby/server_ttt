package TicTacToe;

import java.util.Arrays;

public class BoardMarkerArray {
    private final BoardMarker[][] boardState;

    public BoardMarkerArray(BoardMarker[][] arr) {
        boardState = arr;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof BoardMarkerArray)){
            return false;
        }
        BoardMarkerArray other = (BoardMarkerArray) o;
        return Arrays.deepEquals(boardState, other.boardState);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(boardState);
    }
}
