package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.util.LinkedList;

public class Pwd extends SequentialFilter {

    private String command;

    public Pwd(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();

        this.command = command;
    }

    public void process() {
        if (!this.input.isEmpty()) {
            System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(this.command));
        } else if (this.command.contains(" ")) {
            System.out.print(Message.INVALID_PARAMETER.with_parameter(this.command));
        } else {
            this.output.add(SequentialREPL.currentWorkingDirectory);
        }
    }

    @Override
    protected String processLine(String line) {
        return null;
    }
}
