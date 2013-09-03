package TicTacToe;

import java.io.*;
import java.util.Scanner;

public class ConsoleIO {
    private PrintWriter outputStream;
    private Scanner inputStream;

    public ConsoleIO(Readable reader, Writer writer) {
        inputStream = new Scanner(reader);
        outputStream = new PrintWriter(writer, true);
    }

    public void askForMove() {
        outputStream.println("Where would you like to move? (enter the cell number):");
    }

    public String readMove() {
        return inputStream.nextLine();
    }

    public void notifyInvalidCell() {
        outputStream.println("The cell you entered is not valid!");
    }
}
