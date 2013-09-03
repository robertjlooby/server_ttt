package TicTacToe;

import java.io.IOException;

public interface Player {
    BoardMarker getSymbol();

    int[] getMove(BoardMarker[][] boardState);
}
