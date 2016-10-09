package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RedirectFilter extends ConcurrentFilter {
	private FileWriter fw;
	
	public RedirectFilter(String line) throws Exception {
		super();
		String[] param = line.split(">");
		if(param.length > 1) {
			if(param[1].trim().equals("")) {
				System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
				throw new Exception();
			}
			try {
				String paramOne = param[1].trim();
				if (paramOne.charAt(paramOne.length()-1) == '&') {
					paramOne = paramOne.substring(0, paramOne.length()-1);
				}
				// I added the above 4 lines so that if the last command is a redirect and a background process the file
				// name will not include the & symbol
				fw = new FileWriter(new File(ConcurrentREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + paramOne.trim()));
			} catch (IOException e) {
				System.out.printf(Message.FILE_NOT_FOUND.toString(), line);	//shouldn't really happen but just in case
				throw new Exception();
			}
		} else {
			System.out.printf(Message.REQUIRES_INPUT.toString(), line);
			throw new Exception();
		}
	}
	
	public void process() {
		while(!isDone()) {
			processLine(input.poll());
		}
	}
	
	public String processLine(String line) {
		try {
			fw.append(line + "\n");
			if(isDone()) {
				fw.flush();
				fw.close();
			}
		} catch (IOException e) {
			System.out.printf(Message.FILE_NOT_FOUND.toString(), line);
		}
		return null;
	}

	@Override
	public void run() {
		process();
	}
}
