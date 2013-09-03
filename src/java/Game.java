package TicTacToe;

import static TicTacToe.BoardMarker.*;

public class Game {
    private Player[] players;
    private TicTacToeBoard board;
    private int turn;
    private BoardIO boardIO;

    public Game(Player[] plrs, TicTacToeBoard brd, BoardIO brdIO){
        players = plrs;
        board = brd;
        boardIO = brdIO;
        turn = 0;
    }

    public int getTurn(){
        return turn;
    }

    public BoardMarker takeTurn() {
        boardIO.printBoard(board.getState(), players[0].getSymbol(), players[1].getSymbol());
        int currentPlayer = turn % players.length;
        int[] move = players[currentPlayer].getMove(board.getState());
        board.makeMove(move[0], move[1], players[currentPlayer].getSymbol());
        ++turn;
        return board.winner();
    }

    public BoardMarker play() {
        int size = board.getRow(0).length;
        for(int i = 0; i < size * size; i++){
            BoardMarker winner = takeTurn();
            if (winner != _){
                return winner;
            }
        }
        return _;
    }
}
