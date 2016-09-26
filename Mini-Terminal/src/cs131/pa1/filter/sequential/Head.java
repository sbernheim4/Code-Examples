package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import cs131.pa1.filter.Filter;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

public class Head extends SequentialFilter {

    private String command;
    private Boolean containsHyphen = false;
    private Boolean setNum = false;

    public Head(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();
        this.command = command;
    }

    @Override
    public void process() {
        // Default number of lines to read
        int num = getNumber();
        String fileName = getFileName();

        File file = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + fileName);

        // ERROR HANDLING ON THE INPUT COMMAND
        if (!this.input.isEmpty() || this.prev != null) {
            System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.command));
        } else if (fileName == null) {
            System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.command));
        } else if (containsHyphen && !setNum) {
            System.out.print(Message.INVALID_PARAMETER.with_parameter(this.command));
        } else if (!file.exists()) {
            System.out.print(Message.FILE_NOT_FOUND.with_parameter(this.command));
        } else {
            // If no errors were encountered, then process the command
            try {
                Scanner fileScanner = new Scanner(file);
                // Try creating a scanner on the file specified

                for (int i = 0; fileScanner.hasNextLine() && i < num; i++) {
                    this.output.add(fileScanner.nextLine());
                    // Add the first num lines of the file using the scanner
                }

            } catch (FileNotFoundException e) {
                // Catching this error because Scanner requires it
                // Catch a file not found exception and print the corresponding error
                System.out.print(Message.FILE_NOT_FOUND.with_parameter(this.command));
            }
        }
    }


    // Gets the number the user enters as a parameter. If no number is entered the default 10 is returned
    private int getNumber() {
        try {
            if (this.command.charAt(5) == '-') {
                this.containsHyphen = true;

                Scanner scan = new Scanner(this.command.substring(5));
                // Create a new scanner for the the part of the command after Head

                if (scan.hasNextInt()) {
                    // First see if an int exists in the parameter part of the command

                    // If so set containsHyphen boolean to be true and return its absolute value
                    this.setNum = true;
                    return Math.abs(scan.nextInt());
                }
            } else {
                // Otherwise return the default value of 10
                return 10;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return 10;
        }

        return 10;
    }


    // Gets the filename specified in the command as a parameter
    private String getFileName() {

        try {
            Scanner scan = new Scanner(this.command.substring(5));
            // Create a new scanner for the the part of the command after head
            if (this.containsHyphen) {
                // If there is an integer parameter skip past it in the scanner
                scan.next();

                if (scan.hasNext()) {
                    // Determine if there is more text after the integer

                    // If so return that text removing any trailing or beginning /
                    return removeSlash(scan.next());
                } else {
                    // Otherwise return null
                    return null;
                }
            } else if (scan.hasNext()) {
                // If there is no integer parameter just return the next part of the text

                // If so return that text removing any trailing or beginning /
                return removeSlash(scan.next());
            } else {
                // In any other case return null
                return null;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    // Remove any trailing or leading slashes from the file name
    private String removeSlash(String fileName) {
        if (fileName.substring(0, 1).equals(Filter.FILE_SEPARATOR)) {
            // If the user put a / at the beginning of the filename remove it
            fileName = fileName.substring(1);
        }

        if (fileName.substring(fileName.length() - 1).equals(Filter.FILE_SEPARATOR)) {
            // If the user put a / at the end of the filename remove it
            fileName = fileName.substring(0, fileName.length() - 1);
        }

        return fileName;
    }

    @Override
    protected String processLine(String line) {
        return null;
    }
}
