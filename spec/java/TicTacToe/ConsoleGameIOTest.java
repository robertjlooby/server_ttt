package TicTacToe;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static TicTacToe.BoardMarker.*;

public class ConsoleGameIOTest {
    ConsoleGameIO io;
    StringWriter outputWriter;

    @Before
    public void setup(){
        initialize("");
    }
    public void initialize(String input){
        outputWriter = new StringWriter();
        io = new ConsoleGameIO(new StringReader(input), outputWriter);
    }

    @Test
    public void shouldAskToPlayAIPlayer(){
        io.promptForPlayAI();
        assertTrue(outputWriter.toString().trim().matches(".*play.*AI.*"));
    }

    @Test
    public void shouldAskForPlayFirst(){
        io.promptForPlayFirst();
        assertTrue(outputWriter.toString().trim().matches(".*play first.*"));
    }

    @Test
    public void shouldAskForPlayerSymbol(){
        io.promptForPlayerSymbol();
        assertTrue(outputWriter.toString().trim().matches(".*to be.*Xs.*Os.*"));
    }

    @Test
    public void shouldAskForBoardSize(){
        io.promptForBoardSize();
        assertTrue(outputWriter.toString().trim().matches(".*like to.*3x3.*4x4.*"));
    }

    @Test
    public void shouldGetTrueForYes(){
        initialize("Yes\n");
        assertTrue(io.getYesNo());
    }

    @Test
    public void shouldGetTrueForyes(){
        initialize("yes\n");
        assertTrue(io.getYesNo());
    }

    @Test
    public void shouldGetFalseForNo(){
        initialize("No\n");
        assertFalse(io.getYesNo());
    }

    @Test
    public void shouldGetFalseForno(){
        initialize("no\n");
        assertFalse(io.getYesNo());
    }

    @Test
    public void shouldReGetIfAnswerNotYorN(){
        initialize("bad guess\nYes\n");
        assertTrue(io.getYesNo());
    }

    @Test
    public void shouldNotifyIfBadResponseForGetYesNo(){
        initialize("bad guess\nsecondBadGuess\nYes\n");
        io.getYesNo();
        Scanner responseScanner = new Scanner(outputWriter.toString());
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
    }

    @Test
    public void shouldGetXForX(){
        initialize("X\n");
        assertEquals(X, io.getXorO());
    }

    @Test
    public void shouldGetXForx(){
        initialize("x\n");
        assertEquals(X, io.getXorO());
    }

    @Test
    public void shouldGetOForO(){
        initialize("O\n");
        assertEquals(O, io.getXorO());
    }

    @Test
    public void shouldGetOForo(){
        initialize("o\n");
        assertEquals(O, io.getXorO());
    }

    @Test
    public void shouldNotifyIfBadResponseForGetXorO(){
        initialize("bad guess\nsecondBadGuess\nx\n");
        io.getXorO();
        Scanner responseScanner = new Scanner(outputWriter.toString());
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
    }

    @Test
    public void shouldGet3For3(){
        initialize("3\n");
        assertEquals(3, io.getBoardSize());
    }

    @Test
    public void shouldGet4For4(){
        initialize("4\n");
        assertEquals(4, io.getBoardSize());
    }

    @Test
    public void shouldNotifyBadResponseAndReprompt(){
        initialize("bad\n2\n4");
        io.getBoardSize();
        Scanner responseScanner = new Scanner(outputWriter.toString());
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
    }

    @Test
    public void shouldNotAllowBoardsLargerThan4x4(){
        initialize("bad\n5\n4");
        io.getBoardSize();
        Scanner responseScanner = new Scanner(outputWriter.toString());
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
        assertTrue(responseScanner.nextLine().matches(".*not.*valid.*"));
    }

    @Test
    public void shouldNotifyTie(){
        io.notifyResult(false, X, T);
        assertTrue(outputWriter.toString().trim().matches(".*a tie.*"));
    }

    @Test
    public void shouldNotifyWin(){
        io.notifyResult(true, X, X);
        assertTrue(outputWriter.toString().trim().matches("[yY]ou won.*"));
    }

    @Test
    public void shouldNotifyLost(){
        io.notifyResult(true, X, O);
        assertTrue(outputWriter.toString().trim().matches("[yY]ou lost.*"));
    }

    @Test
    public void shouldNotifyXWon(){
        io.notifyResult(false, X, X);
        assertTrue(outputWriter.toString().trim().matches(".*Xs won.*"));
    }

    @Test
    public void shouldNotifyOWon(){
        io.notifyResult(false, X, O);
        assertTrue(outputWriter.toString().trim().matches(".*Os won.*"));
    }
}
