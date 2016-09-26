package cs131.pa1.filter.sequential;

// Java files for the PA
import cs131.pa1.filter.Message;

// Java Classes
import java.util.Scanner;
import java.util.List;


public class SequentialREPL {

	static String currentWorkingDirectory;

	public static void main(String[] args) {
		currentWorkingDirectory = System.getProperty("user.dir");
		beginREPL();
	}

	private static void beginREPL(){
		// Scanner for user input
		Scanner input = new Scanner(System.in);

		// String to store the commands the user enters
		String command;

		System.out.print(Message.WELCOME);

		do {
			System.out.print(Message.NEWCOMMAND);

			command = input.nextLine();

			// if statement is here in case the first thing the user enters is exit;
			if (!command.equals("exit")) {
				List<SequentialFilter> filters = SequentialCommandBuilder.createFiltersFromCommand(command);

				SequentialCommandBuilder.executeFilters(filters);
			}

		} while (!command.equals("exit"));

		System.out.print(Message.GOODBYE);
	}

}
