package TicTacToe;

import org.junit.Before;
import org.junit.Test;
import static TicTacToe.BoardMarker.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;
    private MockPlayer player0, player1;
    private MockBoardIO boardIO;
    private TicTacToeBoard board;

    @Before
    public void initialize(){
        player0 = new MockPlayer(X);
        player1 = new MockPlayer(O);
        Player[] players = new Player[]{player0, player1};
        boardIO = new MockBoardIO();
        board = new TicTacToeBoard();
        game = new Game(players, board, boardIO);
    }

    @Test
    public void shouldStartAtTurn0(){
        assertEquals(0, game.getTurn());
    }

    @Test
    public void shouldAdvanceTurnEachTurn() {
        assertEquals(0, game.getTurn());
        game.takeTurn();
        assertEquals(1, game.getTurn());
        game.takeTurn();
        assertEquals(2, game.getTurn());
        game.takeTurn();
        assertEquals(3, game.getTurn());
    }

    @Test
    public void shouldGetMoveFromPlayer0OnTurn0() {
        game.takeTurn();
        assertEquals(1, player0.getTimesGetMove());
        assertEquals(0, player1.getTimesGetMove());

    }

    @Test
    public void shouldGetMoveFromPlayer1OnTurn1() {
        game.takeTurn();
        game.takeTurn();
        assertEquals(1, player0.getTimesGetMove());
        assertEquals(1, player1.getTimesGetMove());
    }

    @Test
    public void shouldGetMoveFromCorrectPlayerEachTurn() {
        for(int i = 0; i < 9; i++){
            game.takeTurn();
        }
        assertEquals(5, player0.getTimesGetMove());
        assertEquals(4, player1.getTimesGetMove());
    }

    @Test
    public void shouldPrintTheBoardEachTurn(){
        assertEquals(0, boardIO.getTimesPrintedBoard());
        for(int i = 1; i < 6; i++){
            game.takeTurn();
            assertEquals(i, boardIO.getTimesPrintedBoard());
        }
    }

    @Test
    public void shouldUpdateBoardWithMoves(){
        assertArrayEquals(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}, board.getState());
        player0.setNextMove(new int[]{0, 0});
        game.takeTurn();
        assertArrayEquals(new BoardMarker[][]{{X, _, _}, {_, _, _}, {_, _, _}}, board.getState());
        player1.setNextMove(new int[]{1, 1});
        game.takeTurn();
        assertArrayEquals(new BoardMarker[][]{{X, _, _}, {_, O, _}, {_, _, _}}, board.getState());
        player0.setNextMove(new int[]{2, 0});
        game.takeTurn();
        assertArrayEquals(new BoardMarker[][]{{X, _, _}, {_, O, _}, {X, _, _}}, board.getState());
        player1.setNextMove(new int[]{1, 0});
        game.takeTurn();
        assertArrayEquals(new BoardMarker[][]{{X, _, _}, {O, O, _}, {X, _, _}}, board.getState());
    }

    @Test
    public void shouldReturnWinnerAfterEachTurn(){
        player0.setNextMove(new int[]{0, 0});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{0, 1});
        assertEquals(_, game.takeTurn());
        player0.setNextMove(new int[]{1, 1});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{2, 2});
        assertEquals(_, game.takeTurn());
        player0.setNextMove(new int[]{2, 0});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{0, 2});
        assertEquals(_, game.takeTurn());
        player0.setNextMove(new int[]{1, 0});
        assertEquals(X, game.takeTurn());
    }

    @Test
    public void shouldReturnWinnerAfterEachTurnIfPlayer2(){
        player0.setNextMove(new int[]{0, 0});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{1, 1});
        assertEquals(_, game.takeTurn());
        player0.setNextMove(new int[]{0, 1});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{0, 2});
        assertEquals(_, game.takeTurn());
        player0.setNextMove(new int[]{1, 0});
        assertEquals(_, game.takeTurn());
        player1.setNextMove(new int[]{2, 0});
        assertEquals(O, game.takeTurn());
    }

    @Test
    public void shouldTake9TurnsInAGame(){
        game.play();
        assertEquals(5, player0.getTimesGetMove());
        assertEquals(4, player1.getTimesGetMove());
    }

    @Test
    public void shouldTake16TurnsInA4x4Game(){
        Player[] players = new Player[]{player0, player1};
        boardIO = new MockBoardIO();
        board = new TicTacToeBoard(4, 2);
        game = new Game(players, board, boardIO);

        game.play();
        assertEquals(8, player0.getTimesGetMove());
        assertEquals(8, player1.getTimesGetMove());
    }

    @Test
    public void shouldReturnWinner(){
        assertEquals(_, game.play());
    }

    @Test
    public void shouldReturnWinnerEarlyIfAPlayerWins(){
        board.setState(new BoardMarker[][]{{_, _, _}, {X, O, _}, {X, O, _}});
        assertEquals(X, game.play());
    }

    @Test
    public void shouldReturnWinnerEarlyIfAPlayer2Wins(){
        player0.setNextMove(new int[]{0, 2});
        player1.setNextMove(new int[]{0, 1});
        board.setState(new BoardMarker[][]{{_, _, _}, {X, O, _}, {X, O, _}});
        assertEquals(O, game.play());
    }
}
