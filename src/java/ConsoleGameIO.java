package TicTacToe;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;
import static TicTacToe.BoardMarker.*;

public class ConsoleGameIO {
    private PrintWriter outputStream;
    private Scanner inputStream;

    public ConsoleGameIO(Readable reader, Writer writer) {
        inputStream = new Scanner(reader);
        outputStream = new PrintWriter(writer, true);
    }

    public void promptForPlayAI() {
        outputStream.println("Would you like to play against an AI opponent? (yes or no)");
    }

    public boolean getYesNo() {
        String response = inputStream.nextLine();
        if(response.matches("^[yY].*")){
            return true;
        } else if(response.matches("^[nN].*")){
            return false;
        } else {
            notifyBadResponse();
            return getYesNo();
        }
    }

    public void notifyBadResponse(){
        outputStream.println("That response is not valid. Please try again.");
    }

    public void promptForPlayFirst() {
        outputStream.println("Would you like to play first? (yes or no)");
    }

    public void promptForPlayerSymbol() {
        outputStream.println("Would you like to be Xs or Os? (X or O)");
    }

    public void promptForBoardSize() {
        outputStream.println("What size board would you like to play on (ex. 3x3 or 4x4)? (an integer 3 or larger)");
    }

    public BoardMarker getXorO() {
        String response = inputStream.nextLine();
        if(response.matches("^[xX].*")){
            return X;
        } else if(response.matches("^[oO].*")) {
            return O;
        } else {
            notifyBadResponse();
            return getXorO();
        }
    }

    public int getBoardSize() {
        try {
            int response = Integer.parseInt(inputStream.nextLine());
            if(response == 3 || response == 4){
                return response;
            } else {
                notifyBadResponse();
                return getBoardSize();
            }
        } catch (NumberFormatException n){
            notifyBadResponse();
            return getBoardSize();
        }
    }

    public void notifyResult(boolean playAI, BoardMarker playerSymbol, BoardMarker winner) {
        if(winner == T){
            outputStream.println("It was a tie.");
        } else if(playAI) {
            if(playerSymbol == winner) {
                outputStream.println("You won!");
            } else {
                outputStream.println("You lost!");
            }
        } else {
            outputStream.println("Player with " + (winner == X ? "X" : "O") + "s won!");
        }
    }
}
