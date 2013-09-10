package TicTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static TicTacToe.BoardMarker.*;

public class AIPlayer implements Player{
    private TicTacToeBoard board;
    private BoardMarker symbol;
    private HashMap<BoardMarkerArray, int[]> cachedMoves;

    public AIPlayer(BoardMarker sym, TicTacToeBoard brd) {
        symbol = sym;
        board = brd;
        cachedMoves = new HashMap<BoardMarkerArray, int[]>();
    }

    @Override
    public int[] getMove(BoardMarker[][] boardState) {
        int[] result = alphaBetaMinimax(boardState, -1, 1, symbol);
        return new int[]{result[0], result[1]};
    }

    private int[] alphaBetaMinimax(BoardMarker[][] boardState, int alpha, int beta, BoardMarker movePlayer) {
        int[] move;
        BoardMarkerArray currentBoardArray = new BoardMarkerArray(boardState);
        if((move = cachedMoves.get(currentBoardArray)) != null){
            return move;
        } else if(movePlayer == symbol){
            move = getMaxMove(boardState, alpha, beta, movePlayer);
        } else {
            move = getMinMove(boardState, alpha, beta, movePlayer);
        }
        cachedMoves.put(currentBoardArray, move);
        return move;
    }

    private int getNextScore(BoardMarker[][] boardState, int alpha, int beta, BoardMarker movePlayer, int row, int col) {
        BoardMarker[][] boardStateCopy = deep2DArrayCopy(boardState);
        boardStateCopy[row][col] = movePlayer;
        int score;
        Integer nullableScore;

        if((nullableScore = evaluateScore(boardStateCopy)) != null)
            score = nullableScore.intValue();
        else {
            BoardMarker nextPlayer = (movePlayer == X) ? O : X;
            score =  alphaBetaMinimax(boardStateCopy, alpha, beta, nextPlayer)[2];
        }
        return score;
    }

    private int[] getMaxMove(BoardMarker[][] boardState, int alpha, int beta, BoardMarker movePlayer){
        int nextRow = -1, nextCol = -1;
        for(int[] emptyCell : emptyCells(boardState)){
            int row = emptyCell[0], col = emptyCell[1];
            int nextScore = getNextScore(boardState, alpha, beta, movePlayer, row, col);
            if(nextScore >= beta)
                return new int[]{row, col, nextScore};
            if(nextRow == -1 && nextScore <= alpha){
                nextRow = row;
                nextCol = col;
            }
            if(nextScore > alpha){
                nextRow = row;
                nextCol = col;
                alpha = nextScore;
            }
        }
        return new int[]{nextRow, nextCol, alpha};
    }

    private int[] getMinMove(BoardMarker[][] boardState, int alpha, int beta, BoardMarker movePlayer){
        int nextRow = -1, nextCol = -1;
        for(int[] emptyCell : emptyCells(boardState)){
            int row = emptyCell[0], col = emptyCell[1];
            int nextScore = getNextScore(boardState, alpha, beta, movePlayer, row, col);
            if(nextScore <= alpha)
                return new int[]{row, col, nextScore};
            if(nextRow == -1 && nextScore >= beta){
                nextRow = row;
                nextCol = col;
            }
            if(nextScore < beta){
                nextRow = row;
                nextCol = col;
                beta = nextScore;
            }
        }
        return new int[]{nextRow, nextCol, beta};
    }

    public Integer evaluateScore(BoardMarker[][] boardState) {
        board.setState(boardState);
        BoardMarker winner = board.winner();
        if(winner == _){
            return null;
        } else if(winner == T){//tie
            return 0;
        } else if(winner == symbol){//AI player wins
            return 1;
        } else if (winner == ((symbol == X) ? O : X)){//other player wins
            return -1;
        }
        return 0;
    }

    public static int[][] emptyCells(BoardMarker[][] boardState){
        int boardSize = boardState.length;
        ArrayList<int[]> emptyCellArray = new ArrayList<int[]>(boardSize * boardSize);
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                if(boardState[row][col] == _)
                    emptyCellArray.add(new int[]{row, col});
            }
        }
        return emptyCellArray.toArray(new int[emptyCellArray.size()][]);
    }

    public static BoardMarker[][] deep2DArrayCopy(BoardMarker[][] arr){
        BoardMarker[][] copy = new BoardMarker[arr.length][];
        for(int i = 0; i < arr.length; i++){
            copy[i] = Arrays.copyOf(arr[i], arr[i].length);
        }
        return copy;
    }

    @Override
    public BoardMarker getSymbol() {
        return symbol;
    }

    public HashMap<BoardMarkerArray, int[]> getCachedMoves() {
        return cachedMoves;
    }
}
