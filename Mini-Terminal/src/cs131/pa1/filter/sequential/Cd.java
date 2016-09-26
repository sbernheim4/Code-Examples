package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.util.LinkedList;
import java.io.File;

public class Cd extends SequentialFilter {
    private String command;
    private Boolean printedError = false;
    // boolean to keep track if an error was already printed

    public Cd(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();

        this.command = command;
    }

    @Override
    public void process() {

        String relativePath = null;

        // test if the user entered a parameter for the directory they want to cd into
        try {
            relativePath = this.command.substring(3);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.command));
            printedError = true;
        }

        // ERROR HANDLING ON THE INPUT
        if (!printedError) {
            if (this.prev != null) {
                System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.command));
                printedError = true;
            } else if (this.next != null) {
                System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(this.command));
                printedError = true;
            } else {
                movePath(relativePath);
                // movePath will handle the error of the path being invalid
            }
        }

    }

    private void movePath(String path) {

        File currDir = new File(SequentialREPL.currentWorkingDirectory);
        // Create a new File at the current directory

        switch (path) {
            case ".":
                // If the parameter is . do nothing
                break;
            case "..":
                // If the parameter is .. go up a directory
                SequentialREPL.currentWorkingDirectory = currDir.getParent();
                break;
            default:
                // Otherwise a directory was specified by name

                File f = new File(currDir.getAbsolutePath() + FILE_SEPARATOR + path);
                // Create a new file at that location

                if (f.isDirectory()) {
                    // Test if the file is actually a directory and if so update the currentWorkingDirectory String
                    SequentialREPL.currentWorkingDirectory = currDir.getAbsolutePath() + FILE_SEPARATOR + path;
                } else {
                    // Otherwise the parameter does not lead to a directory and so the corresponding error message
                    // will be printed
                    System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(this.command));
                    printedError = true;
                }
                break;
        }
    }

    @Override
    protected String processLine(String line) {
        return null;
    }
}
