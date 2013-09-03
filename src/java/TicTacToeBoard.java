package TicTacToe;

import java.util.Arrays;
import static TicTacToe.BoardMarker.*;

public class TicTacToeBoard {
    private int size, dimensions;
    private BoardMarker[][] state;

    public TicTacToeBoard(int size, int dimensions) {
        state = new BoardMarker[size][size];
        this.dimensions = dimensions;
        this.size = size;
        for (BoardMarker[] aState : state) {
            Arrays.fill(aState, _);
        }
    }

    public TicTacToeBoard(){
        this(3, 2);
    }

    public BoardMarker[][] getState() {
        return state;
    }


    public void setState(BoardMarker[][] newState) {
        state = newState;
    }

    public void makeMove(int row, int col, BoardMarker player) {
        state[row][col] = player;
    }

    public BoardMarker[] getRow(int row) {
        return state[row];
    }

    public BoardMarker[] getColumn(int col) {
        BoardMarker[] column = new BoardMarker[size];
        for (int i = 0; i < size; i++) {
            column[i] = state[i][col];
        }
        return column;
    }

    public BoardMarker[] getForwardDiagonal() {
        BoardMarker[] diag = new BoardMarker[size];
        for (int i = 0; i < size; i++) {
            diag[i] = state[i][i];
        }
        return diag;
    }

    public BoardMarker[] getBackwardDiagonal() {
        BoardMarker[] diag = new BoardMarker[size];
        for (int i = 0; i < size; i++) {
            diag[i] = state[i][size - 1 - i];
        }
        return diag;
    }

    public boolean hasWinner(BoardMarker[] row) {
        BoardMarker firstElem = row[0];
        if (firstElem == _) {
            return false;
        }
        for (int i = 1; i < size; i++) {
            if (row[i] != firstElem) {
                return false;
            }
        }
        return true;
    }

    public BoardMarker winner() {
        for (int i = 0; i < size; i++) {
            if (hasWinner(getRow(i)) || hasWinner(getColumn(i))) {
                return getState()[i][i];
            }
        }
        if (hasWinner(getForwardDiagonal())) {
            return getState()[0][0];
        } else if (hasWinner(getBackwardDiagonal())) {
            return getState()[0][size - 1];
        } else if (full()){
            return T;
        } else {
            return _;
        }
    }

    public boolean full() {
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(state[row][col] == _){
                    return false;
                }
            }
        }
        return true;
    }
}
