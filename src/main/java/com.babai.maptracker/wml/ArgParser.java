package wml;

import java.io.FileInputStream;
import java.io.File;

public class ArgParser {
	public boolean showLogs = false;
	public boolean showParseLogs = false;
	public FileInputStream in = null;
	
	public void parseArgs(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.startsWith("--")) {
				arg = arg.substring(2, arg.length());
			} else if (arg.startsWith("-") || arg.startsWith("/")) {
				arg = arg.substring(1, arg.length());
			}
			
			switch(arg) {
				case "log" -> { showLogs = true; showParseLogs = true; }
				case "log-token", "log-t" -> showLogs = true;
				case "log-parse", "log-p" -> showParseLogs = true;
				case "i", "input" -> {
					try {
						in = new FileInputStream(new File(args[++i]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
