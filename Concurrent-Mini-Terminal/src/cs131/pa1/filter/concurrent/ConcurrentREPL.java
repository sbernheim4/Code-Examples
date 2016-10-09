package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;

import java.util.*;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	static Map <BackgroundProcess, String> background = new HashMap<>();

	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;

		while(true) {

			System.out.print(Message.NEWCOMMAND);
			//obtaining the command from the user
			command = s.nextLine();

			if(command.equals("exit")) {
				break;
			} else if(!command.trim().equals("")) {

				if (command.equals("repl_jobs")) {
					// if the user enters repl_jobs

					Iterator<Map.Entry<BackgroundProcess, String>> iter = background.entrySet().iterator();
					// iterator to go through the hashmap that is storing all background processes
					int counter = 1;
					Map.Entry<BackgroundProcess, String> prev = null;

					// iterate through the hashmap
					while(iter.hasNext()) {

						Map.Entry<BackgroundProcess, String> entry = iter.next();

						int size = entry.getKey().getFilters().size();
						int curr = 0;
						for (ConcurrentFilter filter : entry.getKey().getFilters()) {
							if (filter.isDone()) {
								// check to see if all the filters in the command are done executing and if so add one
								// to curr
								curr++;
							}
						}

						if (curr == size)  {
							//if curr equals size, that means every filter in the command has finished executing and
							// so the command is no longer running and should be removed from the map
							iter.remove();
						} else if (!entry.getKey().getPrinted()) {
							// otherwise the command is still running in the background and so it should be printed.
							System.out.println("\t" + counter + ". " + entry.getValue());
							counter++;
							entry.getKey().setTrue();
							// set the boolean value of the background process to be true since it just got printed
						}
					}
				} else {
					//building the filters list from the command
					List<ConcurrentFilter> filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);

					//checking to see if construction was successful. If not, prompt user for another command
					if(filterlist != null) {
						//run each filter process manually.
						Iterator<ConcurrentFilter> iter = filterlist.iterator();

						// if the command is NOT a  background process

						while(iter.hasNext()){
							// Instead of calling process() on iter.next() I make a new thread based on the what the
							// current ConcurrentFilter is and then call thread.start(). I made it so that each
							// ConcurrentFilter's run method simply calls process(). This way process still gets called
							// but each command will run in its own thread.
							Thread thread = new Thread(iter.next());
							thread.start();

							if (command.charAt(command.length()-1) != '&') {
								while (true) {
									// This waits for the current, running thread to first finish before proceeding to
									// the next one
									try {
										thread.join();
										break;
									} catch (InterruptedException e) {

									}
								}
							} else {
								BackgroundProcess temp = new BackgroundProcess(filterlist, false);
								if (!background.containsKey(temp) && !background.containsValue(command)) {
									background.put(temp, command);
								}
							}
						}
					}
				}
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
