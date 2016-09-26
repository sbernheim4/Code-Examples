package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.io.File;
import java.util.LinkedList;

public class Ls extends SequentialFilter {
    private String command;

    public Ls(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();

        this.command = command;
    }

	@Override
	public void process() {
        // ERROR HANDLING ON THE INPUT
        if (!this.input.isEmpty()) {
            System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.command));
        } else if (!this.command.equals("ls")) {
            System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(this.command));
        } else {
            File curDir = new File(SequentialREPL.currentWorkingDirectory);
            // Create a file in the current directory

            for (String f : curDir.list()) {
                // Go through every file in the current directory adding their name to the output queue
                this.output.add(f);
            }
        }
	}

	@Override
	protected String processLine(String line) {
		return null;
	}
}
