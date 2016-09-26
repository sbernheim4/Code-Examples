package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;
import java.util.LinkedList;

public class Wc extends SequentialFilter {
    private String command;
    long lines = 0;
    long words = 0;
    long chars = 0;

    public Wc(String command) {
        input = new LinkedList<>();
        output = new LinkedList<>();
        this.command = command;
    }

    @Override
    // Overriding process to handle errors better internally
    public void process(){
        int i = 0;
        int size = this.input.size();

        if (this.input.isEmpty() && this.prev == null) {
            // if the input queue is empty and there was no previous command
            System.out.print(Message.REQUIRES_INPUT.with_parameter(this.command));
        } else {
            while (!input.isEmpty()) {
                String line = input.poll();
                this.chars += line.length();
                this.lines++;
                String[] test = line.split(" ");
                this.words += test.length;
                i++;
            }

            // Make sure that only adding to the output queue if the previous command is one that has pipped output
            if (i == size && !(this.prev instanceof Cd) && !(this.prev instanceof Redirect)) {
                this.output.add(lines + " " + words + " " + chars);
            }
        }
    }

    @Override
    protected String processLine(String line) {
        return null;
    }

}