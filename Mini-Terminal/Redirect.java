package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;
import java.util.LinkedList;

import java.io.*;

public class Redirect extends SequentialFilter {
    private String command;
    private String outputFileName;
    private boolean checked = false;
    private boolean printedError = false;

    public Redirect(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();
        this.command = command;

        // try and get the name the user entered that will be written to
        try {
            this.outputFileName = command.substring(2);
        } catch (StringIndexOutOfBoundsException e) {
            this.outputFileName = null;
        }
    }

    @Override
    // Overriding process to do additional error handling
    public void process(){
        if (this.outputFileName == null) {
            System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.command));
            this.printedError = true;
        } else if (this.prev == null || this.input.isEmpty()) {
            System.out.print(Message.REQUIRES_INPUT.with_parameter(this.command));
            this.printedError = true;
        } else {
            while (!input.isEmpty()) {
                String line = input.poll();
                String processedLine = processLine(line);
                if (processedLine != null) {
                    output.add(processedLine);
                }
            }
        }
    }

    @Override
    protected String processLine(String line) {

        if (this.next != null && !this.printedError) {
            System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(this.command));
            this.printedError = true;
        }

        if (!this.printedError) {
            // Only continue if no error has been printed

            if (this.outputFileName != null) {
                File file = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + outputFileName);
                // Create a new file at the location the user specified

                try {
                    if (!file.exists() && !checked) {
                        // If the file doesn't already exist and I haven't checked if it exists
                        file.createNewFile();
                        this.checked = true;
                    } else if (file.exists() && !checked) {
                        // If the does exist and I haven't checked if it exists
                        file.delete();
                        file.createNewFile();
                        this.checked = true;
                    }

                    FileWriter fw = new FileWriter(file, true);
                    // Create the FileWriter and set it to append to the file instead of overwriting it
                    if (line != null && !line.equals(file.getName())) {
                        // Only write the line to the file if it is not null and if line is not the name of the file.
                        fw.write(line + "\n");
                        fw.flush();
                        fw.close();
                    }
                } catch (IOException e) {
                    return null;
                }
            } else if (!printedError){
                // If no file name was specified then print the corresponding error
                System.out.print(Message.REQUIRES_PARAMETER.with_parameter(">"));
                this.printedError = true;
            } else {
                return null;
            }
        }


        return null;
    }
}
