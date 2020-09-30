/*
 * Course: CS2852 - 061
 * Spring
 * Lab 8 - Morse Code Encoder
 * Name: Nigel Nelson
 * Created: 05/10/20
 */
package msoe.nelsonni.lab08;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that manipulates the LookupTable to
 * Encode Morse Code
 */
public class MorseEncoder {

    /**
     * Instance of the LookupTable class that is referenced
     * by several methods
     */
    private static LookupTable lookupTable;

    /**
     * Main method that runs the Encoder
     *
     * @param args ignored
     */
    public static void main(String[] args) {

        loadEncoder(Paths.get("MorseCode.txt"));
        File input = getInputFile();
        File outPut = getOutPutFile();
        decode(input, outPut);
    }

    /**
     * Method to initialize a morse code key
     *
     * @param path the path of a morse code key
     */
    private static void loadEncoder(Path path) {
        lookupTable = new LookupTable<>();

        try (Scanner in = new Scanner(path.toFile())) {

            while (in.hasNextLine()) {
                String unformattedLine = in.nextLine();
                if (unformattedLine.charAt(0) == '\\') {
                    String symbol = "\n";
                    String code = unformattedLine.substring(2);
                    lookupTable.put(symbol, code);
                } else {
                    String symbol = "" + unformattedLine.charAt(0);
                    String code = unformattedLine.substring(1);
                    lookupTable.put(symbol, code);
                }
            }
        } catch (FileNotFoundException e) {
            showInitializationError();
        }
    }

    /**
     * Method that gets an output file from the user
     *
     * @return a file that will have the Encoded Morse code written to
     * it
     */
    private static File getOutPutFile() {
        Scanner in = new Scanner(System.in);
        String extension;
        String fileString;
        Path path;
        File outPutFile;

        try {
            do {
                do {
                    do {
                        System.out.println("Enter an output filename:");

                        fileString = in.next();

                    } while (fileString.lastIndexOf(".") == -1);

                    extension = fileString.substring(fileString.lastIndexOf("."));

                } while (!extension.equals(".txt"));
                path = Paths.get(fileString);
            } while (!path.toFile().createNewFile());


            outPutFile = path.toFile();


        } catch (InputMismatchException | IOException e) {
            showInputFileError();
            outPutFile = getOutPutFile();
        }

        return outPutFile;

    }

    /**
     * Method that decodes input from the input file, and writes it to
     * the output file
     *
     * @param input  The file containing text
     * @param outPut The file that will contain morse code
     *               based on the input file
     */
    private static void decode(File input, File outPut) {

        try (Scanner in = new Scanner(input); PrintWriter writer = new PrintWriter(outPut)) {

            while (in.hasNextLine()) {
                String currentLine = in.nextLine();
                if (currentLine.trim().length() < 1) {
                    writer.println(lookupTable.get("\n"));
                } else {
                    for (char c : currentLine.toCharArray()) {
                        String s = Character.toUpperCase(c) + "";
                        if (lookupTable.containsKey(s)) {
                            writer.print(lookupTable.get(s));
                            writer.print(" ");
                        } else {
                            System.err.println("Warning: skipping " + s);
                        }
                    }
                    if (in.hasNextLine()) {
                        writer.println(lookupTable.get("\n"));
                    } else {
                        writer.print(lookupTable.get("\n"));
                    }
                }
            }
            System.out.println("Your file was successfully written to.");
        } catch (IOException e) {
            System.out.println("Error encountered when writing to files");
        }
    }

    /**
     * Method that attains an input file from the user
     *
     * @return A file that is to be Encoded
     */
    private static File getInputFile() {
        Scanner in = new Scanner(System.in);
        String extension;
        String fileString;
        Path path;
        File inPutFile;

        try {
            do {
                do {
                    do {
                        System.out.println("Enter an input filename");

                        fileString = in.next();

                    } while (fileString.lastIndexOf(".") == -1);

                    extension = fileString.substring(fileString.lastIndexOf("."));

                } while (!extension.equals(".txt"));
                path = Paths.get(fileString);
            } while (path.toFile().createNewFile());


            inPutFile = path.toFile();


        } catch (InputMismatchException | IOException e) {
            showInputFileError();
            inPutFile = getInputFile();
        }

        return inPutFile;
    }

    /**
     * Method that alerts the user that there was an input
     * file error
     */
    private static void showInputFileError() {
        System.err.println("An error was encountered when the input file" +
                "was attempted to be initialized");
    }

    /**
     * Method that alerts the user that there was an
     * initialization error
     */
    private static void showInitializationError() {
        System.err.println("An error was encountered when the Morse Code " +
                "attempted to initialize");
    }
}
