package com.xivvic.args;

import java.io.File;
import java.nio.file.Path;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.util.FileUtil;

/**
 * Example application to run the Args command line processor
 * initially inspired by Robert C. Martin's Clean Code, chapter 14.
 */
public class ApplicationLongForm
{
	public static void main(String[] args)
	{
		try
		{
			String[] ersatzArgs = {"-h", "--path", "/tmp", "--file" , "out.txt", "--server", "localhost", "--port", "8080"};
			args = args.length == 0 ? ersatzArgs : args;
			String defs = getOptionDefinitions();
			Args arg = Args.processOrExit(defs, args);

			Path path = arg.getValue("path");
			File file = arg.getValue("file");
			Integer port = arg.getValue("port");
			String server = arg.getValue("server");

			run(path, file, server, port);
		}
		catch (ArgsException e)
		{
			System.out.printf("Argument error: %s\n", e.errorMessage());
		}
	}

	// Reads resource file containing option definitions
	//
	private static String getOptionDefinitions()
	{
		String path = "src/main/resources/argsdefs";
		String file = "Option.definitions.example.argsdef";
		String defs = FileUtil.readFromResourceFile(path, file);

		return defs;
	}

	private static void run(Path path, File file, String server, int port)
	{
		String fmt = "ApplicationLongForm running with File+Path=%s/%s, Server+Port=%s:%d\n";
		String msg = String.format(fmt, path, file, server, port);
		System.out.printf(msg);
	}

}
