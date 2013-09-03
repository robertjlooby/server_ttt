package TicTacToe;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class ConsoleIOTest {
    ConsoleIO playerIO;
    StringWriter playerWriter;

    @Before
    public void setUp() {
        initialize("4\n0\n8\n14\n");
    }

    public void initialize(String inputForPlayerIO) {
        Reader reader = new StringReader(inputForPlayerIO);
        playerWriter = new StringWriter();
        playerIO = new ConsoleIO(reader, playerWriter);
    }

    @Test
    public void shouldAskForMoveInOutputStream() {
        playerIO.askForMove();
        assertTrue(playerWriter.toString().trim().matches(".*Where.*move.*cell number.*"));
    }

    @Test
    public void shouldNotifyInvalidCell() {
        playerIO.notifyInvalidCell();
        assertTrue(playerWriter.toString().trim().matches(".*not valid.*"));
    }

    @Test
    public void shouldReadMoveFromInputStream() throws IOException {
        assertTrue(playerIO.readMove().matches("4"));
    }

    @Test
    public void shouldReadMultipleMovesFromInputStream() throws IOException {
        assertTrue(playerIO.readMove().matches("4"));
        assertTrue(playerIO.readMove().matches("0"));
        assertTrue(playerIO.readMove().matches("8"));
        assertTrue(playerIO.readMove().matches("14"));
    }
}
