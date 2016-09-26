package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.util.LinkedList;

public class Grep extends SequentialFilter {

    private String command;

    public Grep(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();
        this.command = command;
    }

    @Override
    public void process() {
        if (this.input.size() == 0) {
            System.out.print(Message.REQUIRES_INPUT.with_parameter(this.command));
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


        if (line != null) {
            int space = command.indexOf(" ");

            if (space != -1) {
                // Check to see if the user specified a pattern as a parameter

                String pattern = this.command.substring(space + 1);
                // If so the pattern is going to be everything after the first space

                if (line.contains(pattern)) {
                    // If the line contains the pattern return that line
                    return line;
                } else {
                    // Otherwise return null
                    return null;
                }
            } else {
                // If the user didn't specify a pattern print an error message
                System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.command));
                return null;
            }
        } else {
            System.out.print(Message.REQUIRES_PARAMETER.with_parameter(this.command));
            return null;
        }
    }
}
