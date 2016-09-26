package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

import java.util.*;

public class SequentialCommandBuilder {

    private static Boolean containsRedirect = false;

    public static List<SequentialFilter> createFiltersFromCommand(String command) {
        // Boolean to keep track of if the last command is a Redirect or not.

        // integer of location of the first instance of >
        int positionOfRedirect = command.indexOf(">");

        // determines if the last symbol is a Redirect
        if (positionOfRedirect >= 0) {
            containsRedirect = true;
        } else {
            containsRedirect = false;
        }

        String[] arrayOfCommands = new String[0];

        // Determines if the command contains filters after a > appears. If so throw an error since this is invalid
        // Otherwise, Build an array containing each command

        // Splits the input string at each instance of | or > and stores the result in an array
        arrayOfCommands = command.split("(\\|)");

        for (int i = 0; i < arrayOfCommands.length; i++) {
            arrayOfCommands[i] = arrayOfCommands[i].trim();
        }


        List<SequentialFilter> commands = new LinkedList<>();

        for (String cmd : arrayOfCommands) {
            if (cmd.contains(">")) {
                int pos = cmd.indexOf(">");
                if (pos > 0) {
                    String first = cmd.substring(0, pos - 1);
                    String second = cmd.substring(pos, cmd.length());
                    commands.add(constructFilterFromSubCommand(first));
                    commands.add(constructFilterFromSubCommand(second));
                } else {
                    commands.add(constructFilterFromSubCommand(cmd));
                }
            } else {
                commands.add(constructFilterFromSubCommand(cmd));
            }
        }

        // link each filter to the next filter
        linkFilters(commands);

        // return the linked filters
        return commands;
    }

    private static SequentialFilter constructFilterFromSubCommand(String subCommand) {

        // for commands that take input like Cd or Head, since I am initially checking the substring for the first few
        // characters I then also ensure that the commands which need a parameter also contain a space so as to avoid
        // trying to process cdsrc as a command when it is not.

        if (subCommand.equals("")) {
            // If the user doesn't enter any command just return null
            return null;
        } else if (subCommand.charAt(0) == '>') {
            return new Redirect(subCommand);
        } else if (subCommand.substring(0, 2).equals("wc")) {

            if (isItValid(subCommand, 2)) {
                return new Wc(subCommand);
            } else {
                return null;
            }

        } else if (subCommand.substring(0, 2).equals("cd")) {

            if (isItValid(subCommand, 2)) {
                return new Cd(subCommand);
            } else {
                return null;
            }

        } else if (subCommand.equals("ls")) {
            return new Ls(subCommand);
        } else if (subCommand.equals("pwd")) {
            return new Pwd(subCommand);
        } else if (subCommand.substring(0, 4).equals("head")) {

            if (isItValid(subCommand, 4)) {
                return new Head(subCommand);
            } else {
                return null;
            }

        } else if (subCommand.substring(0, 4).equals("grep")) {

            if (isItValid(subCommand, 4)) {
                return new Grep(subCommand);
            } else {
                return null;
            }

        } else {
            // If the user entered something that wasn't a command, then print out the corresponding error message and
            // return null
            System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
            // return a Cd to . since that will not effect the state of anything
            return null;
        }
    }

    private static void linkFilters(List<SequentialFilter> filters) {

        // only link filters when there is more than one filter
        if (filters.size() > 1) {
            Iterator<SequentialFilter> itr = filters.iterator();

            SequentialFilter curr = itr.next();
            SequentialFilter prev = curr;

            int i = 1;
            while (itr.hasNext()) {

                if (i >= 2) {
                    prev.setNextFilter(curr);
                }

                prev = curr;
                curr = itr.next();
                if (curr != null) {
                    curr.setPrevFilter(prev);
                }
                i++;
            }

            if (curr != null) {
                prev.setNextFilter(curr);
            }
        }
    }

    public static void executeFilters(List<SequentialFilter> filters) {
        for (int i = 0; i < filters.size(); i++) {
            // Current SF object
            SequentialFilter filter = filters.get(i);

            if (filter != null) {
                filter.process();

                // If processing the final command in the List, then determine if the contents of the final commands
                // output queue should be printed to the console or not if the last command was a Redirect.
                if (i == filters.size() - 1 && !containsRedirect) {
                    while (!filter.output.isEmpty()) {
                        System.out.println(filter.output.remove());
                    }
                }
            }
        }
    }

    private static Boolean isItValid(String subCommand, int length) {
        if (subCommand.length() > length && !subCommand.contains(" ")) {
            System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
            return false;
        } else {
            return true;
        }
    }
}
