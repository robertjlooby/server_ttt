package TicTacToe;

import static TicTacToe.BoardMarker.*;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class BoardConsoleIOTest {
    BoardConsoleIO io;
    StringWriter ioOutput;


    @Before
    public void initialize() {
        ioOutput = new StringWriter();
        String[] ex = new String[]{"\\ /", " x ", "/ \\"};
        String[] oh = new String[]{"/-\\", "| |", "\\-/"};
        io = new BoardConsoleIO(ioOutput, "-", "|", "+", ex, oh);
    }

    @Test
    public void shouldPrintAnEmptyFirstRow() {
        String emptyRow = "   |   |   \r\n" +
                          "   |   |   \r\n" +
                          "  0|  1|  2\r\n";
        io.printRow(new BoardMarker[]{_, _, _}, 0, new BoardMarker[]{X, O});
        assertEquals(emptyRow, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptySecondRow() {
        String emptyRow = "   |   |   \r\n" +
                          "   |   |   \r\n" +
                          "  3|  4|  5\r\n";
        io.printRow(new BoardMarker[]{_, _, _}, 1, new BoardMarker[]{X, O});
        assertEquals(emptyRow, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptyThirdRow() {
        String emptyRow = "   |   |   \r\n" +
                          "   |   |   \r\n" +
                          "  6|  7|  8\r\n";
        io.printRow(new BoardMarker[]{_, _, _}, 2, new BoardMarker[]{X, O});
        assertEquals(emptyRow, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptyFirstRowFor4() {
        String emptyRow = "   |   |   |   \r\n" +
                          "   |   |   |   \r\n" +
                          "  0|  1|  2|  3\r\n";
        io.printRow(new BoardMarker[]{_, _, _, _}, 0, new BoardMarker[]{X, O});
        assertEquals(emptyRow, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptyLastRowFor4() {
        String emptyRow = "   |   |   |   \r\n" +
                          "   |   |   |   \r\n" +
                          " 12| 13| 14| 15\r\n";
        io.printRow(new BoardMarker[]{_, _, _, _}, 3, new BoardMarker[]{X, O});
        assertEquals(emptyRow, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptyBoard() {
        String emptyBoard = "   |   |   \r\n" +
                            "   |   |   \r\n" +
                            "  0|  1|  2\r\n" +
                            "---+---+---\r\n" +
                            "   |   |   \r\n" +
                            "   |   |   \r\n" +
                            "  3|  4|  5\r\n" +
                            "---+---+---\r\n" +
                            "   |   |   \r\n" +
                            "   |   |   \r\n" +
                            "  6|  7|  8\r\n";
        io.printBoard(new BoardMarker[][]{{_, _, _}, {_, _, _}, {_, _, _}}, X, O);
        assertEquals("\r\n\r\n\r\n\r\n" + emptyBoard, ioOutput.toString());
    }

    @Test
    public void shouldPrintAnEmptyBoardFor4() {
        String emptyBoard = "   |   |   |   \r\n" +
                            "   |   |   |   \r\n" +
                            "  0|  1|  2|  3\r\n" +
                            "---+---+---+---\r\n" +
                            "   |   |   |   \r\n" +
                            "   |   |   |   \r\n" +
                            "  4|  5|  6|  7\r\n" +
                            "---+---+---+---\r\n" +
                            "   |   |   |   \r\n" +
                            "   |   |   |   \r\n" +
                            "  8|  9| 10| 11\r\n" +
                            "---+---+---+---\r\n" +
                            "   |   |   |   \r\n" +
                            "   |   |   |   \r\n" +
                            " 12| 13| 14| 15\r\n";
        io.printBoard(new BoardMarker[][]{{_, _, _, _}, {_, _, _, _}, {_, _, _, _}, {_, _, _, _}}, X, O);
        assertEquals("\r\n\r\n\r\n\r\n" + emptyBoard, ioOutput.toString());
    }

    @Test
    public void shouldPrintAFirstRowWithAnX() {
        String row = "\\ /|   |   \r\n" +
                     " x |   |   \r\n" +
                     "/ \\|  1|  2\r\n";
        io.printRow(new BoardMarker[]{X, _, _}, 0, new BoardMarker[]{X, O});
        assertEquals(row, ioOutput.toString());
    }

    @Test
    public void shouldPrintAFirstRowWithAnO() {
        String row = "/-\\|   |   \r\n" +
                     "| ||   |   \r\n" +
                     "\\-/|  1|  2\r\n";
        io.printRow(new BoardMarker[]{O, _, _}, 0, new BoardMarker[]{O, X});
        assertEquals(row, ioOutput.toString());
    }

    @Test
    public void shouldPrintAFirstRowWithAnOForP2() {
        String row = "/-\\|   |   \r\n" +
                     "| ||   |   \r\n" +
                     "\\-/|  1|  2\r\n";
        io.printRow(new BoardMarker[]{O, _, _}, 0, new BoardMarker[]{X, O});
        assertEquals(row, ioOutput.toString());
    }

    @Test
    public void shouldPrintFull4x4Board() {
        String board = "\\ /|/-\\|/-\\|/-\\\r\n" +
                       " x || ||| ||| |\r\n" +
                       "/ \\|\\-/|\\-/|\\-/\r\n" +
                       "---+---+---+---\r\n" +
                       "\\ /|\\ /|/-\\|/-\\\r\n" +
                       " x | x || ||| |\r\n" +
                       "/ \\|/ \\|\\-/|\\-/\r\n" +
                       "---+---+---+---\r\n" +
                       "\\ /|\\ /|/-\\|\\ /\r\n" +
                       " x | x || || x \r\n" +
                       "/ \\|/ \\|\\-/|/ \\\r\n" +
                       "---+---+---+---\r\n" +
                       "/-\\|\\ /|\\ /|/-\\\r\n" +
                       "| || x | x || |\r\n" +
                       "\\-/|/ \\|/ \\|\\-/\r\n";
        io.printBoard(new BoardMarker[][]{{X, O, O, O}, {X, X, O, O}, {X, X, O, X}, {O, X, X, O}}, X, O);
        assertEquals("\r\n\r\n\r\n\r\n" + board, ioOutput.toString());
    }
}
