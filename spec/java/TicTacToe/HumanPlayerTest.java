package TicTacToe;

import org.junit.Before;
import org.junit.Test;
import static TicTacToe.BoardMarker.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HumanPlayerTest {
    HumanPlayer player;
    ConsoleIO io;
    StringWriter playerWriter;

    @Before
    public void setUp() {
        initialize("0");
    }

    public void initialize(String inputForPlayer) {
        playerWriter = new StringWriter();
        io = new ConsoleIO(new StringReader(inputForPlayer), playerWriter);
        player = new HumanPlayer(X, io);
    }

    @Test
    public void shouldReturnSymbol() {
        assertEquals(X, player.getSymbol());
    }

    @Test
    public void symbolShouldBeSettable() {
        player = new HumanPlayer(O, io);
        assertEquals(O, player.getSymbol());
    }

    @Test
    public void shouldGetMove() {
        assertArrayEquals(new int[]{0, 0}, player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}));
    }

    @Test
    public void shouldGetMoveFromInput() {
        initialize("8");
        assertArrayEquals(new int[]{2, 2}, player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}));
    }

    @Test
    public void shouldAskForMoveWhenGettingMove() {
        player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}});
        assertTrue(playerWriter.toString().trim().matches(".*Where.*move.*cell number.*"));
    }

    @Test
    public void shouldNotifyAndRepromptIfBadResponseIsGiven() {
        initialize("bad\n3stillbad\n5");
        assertArrayEquals(new int[]{1, 2}, player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}));
        Scanner reader = new Scanner(new StringReader(playerWriter.toString()));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
    }

    @Test
    public void shouldNotifyAndRepromptIfNegativeResponseIsGiven() {
        initialize("-1\n3\n");
        assertArrayEquals(new int[]{1, 0}, player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}));
        Scanner reader = new Scanner(new StringReader(playerWriter.toString()));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
    }

    @Test
    public void shouldNotifyAndRepromptIfTooLargeResponseIsGiven() {
        initialize("9\n12\n8\n");
        assertArrayEquals(new int[]{2, 2}, player.getMove(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}));
        Scanner reader = new Scanner(new StringReader(playerWriter.toString()));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
    }

    @Test
    public void shouldNotifyAndRepromptIfTakenCellIsGiven(){
        initialize("0\n1\n2\n3\n");
        assertArrayEquals(new int[]{1, 0}, player.getMove(new BoardMarker[][]{{X, O, X}, {_, _, _}, {_, _, _}}));
        Scanner reader = new Scanner(new StringReader(playerWriter.toString()));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
        assertTrue(reader.nextLine().trim().matches(".*not valid.*"));
        assertTrue(reader.nextLine().trim().matches(".*Where.*move.*cell number.*"));
    }
}
