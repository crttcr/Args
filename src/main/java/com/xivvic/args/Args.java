package com.xivvic.args;

import static com.xivvic.args.error.ErrorCode.AMBIGUOUS_OPTION_NAME;
import static com.xivvic.args.error.ErrorCode.NO_SCHEMA;
import static com.xivvic.args.error.ErrorCode.NULL_ARGUMENT_ARRAY;
import static com.xivvic.args.error.ErrorCode.UNEXPECTED_OPTION;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.error.ExceptionHandler;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.Text2Schema;
import com.xivvic.args.schema.item.Item;
import com.xivvic.args.util.FileUtil;


/**
 * Argument and options command line processor with some initial inspiration from
 * Robert C. Martin's Clean Code, chapter 14.
 *
 * However, this class no longer is responsible for parsing
 * the definition of the program options and processing the command line.
 * Seems like two different things.
 *
 * Parsing and maintaining option definitions is the responsibility
 * of the {@see Schema} and related resources in the schema package.
 */
public class Args
{
	public static final String DEFAULT_SPECIFICATION_FILE = "default.argspec";

	private Set<String> optionsFound = new HashSet<String>();
	private ListIterator<String> argumentIterator;
	private final List<String> arguments = new ArrayList<>();
	private final Schema schema;

	private Args(Schema schema, String[] args)
	throws ArgsException
	{
		if (schema == null)
		{
			throw new SchemaException(NO_SCHEMA);
		}
		this.schema = schema;

		parseCommandLine(args);
		addMissingEnvironmentVariables();
		validateCommandLine();
	}

	public static Args createDefaultInstance(String[] args)
	throws ArgsException
	{
		String defs = null;
		try (InputStream stream = Args.class.getClassLoader().getResourceAsStream(DEFAULT_SPECIFICATION_FILE))
		{
			defs = FileUtil.inputStreamToString(stream);
			if (defs == null)
			{
				throw new SchemaException(NO_SCHEMA);
			}
		}
		catch (IOException e)
		{
			String msg = String.format("Exception opening [%s] : %s", DEFAULT_SPECIFICATION_FILE, e);
			System.err.println(msg);
			throw new SchemaException(NO_SCHEMA);
		}

		Args rv = Args.processOrThrowException(defs, args);
		return rv;
	}

	public static Args processOrExit(Schema schema, String[] args)
	{
		if (schema == null)
		{
			SchemaException ex = new SchemaException(NO_SCHEMA);
			ExceptionHandler handler = ExceptionHandler.terminatingHandler(ex);
			handler.handle();
		}

		Args rv = null;
		try
		{
			rv = new Args(schema, args);
			provideHelpIfRequested(schema, rv);
		}
		catch (ArgsException e)
		{
			ExceptionHandler handler = ExceptionHandler.terminatingHandler(e);
			handler.handle();
		}

		return rv;
	}

	public static Args processOrExit(String defs, String[] args)
	throws ArgsException
	{
		if (defs == null)
		{
			throw new SchemaException(NO_SCHEMA);
		}

		Text2Schema t2s = new Text2Schema();
		Schema schema = t2s.createSchema(defs);

		return Args.processOrExit(schema, args);
	}


	public static Args processOrThrowException(Schema schema, String[] args)
	throws ArgsException
	{
		if (schema == null)
		{
			throw new SchemaException(NO_SCHEMA);
		}

		Args rv = null;
		try
		{
			rv = new Args(schema, args);
			provideHelpIfRequested(schema, rv);
		}
		catch (ArgsException e)
		{
			ExceptionHandler handler = ExceptionHandler.throwingHandler(e);
			handler.handle();
		}
		return rv;
	}

	public static Args processOrThrowException(String defs, String[] args)
	throws ArgsException
	{
		if (defs == null)
		{
			throw new SchemaException(NO_SCHEMA);
		}

		Text2Schema t2s = new Text2Schema();
		Schema schema = t2s.createSchema(defs);

		return Args.processOrThrowException(schema, args);
	}

	public boolean optionProvidedOnCommandLine(String opt)
	{
		if (opt == null)
		{
			return false;
		}

		return optionsFound.contains(opt);
	}

	public boolean optionHasValue(String opt)
	{
		if (opt == null)
		{
			return false;
		}

		return getValue(opt) == null;
	}

	public <T> T getValue(String option) {
		Item<T> item = schema.getItem(option);
		OptEvaluator<T> eval = item.getEval();
		T rv = eval.getValue();
		return rv;
	}

	public int argumentCount()
	{
		return argumentIterator.nextIndex();
	}

	public String getArgument(int i)
	{
		if (i < 0 || i >= arguments.size())
		{
			return null;
		}

		return arguments.get(i);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Args");
		sb.append("\n\t");
		int argCount = arguments.size();
		sb.append("Arguments provided: ");
		sb.append(argCount);
		sb.append("\n\t");
		sb.append(schema.toString());

		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addMissingEnvironmentVariables() throws ArgsException
	{
		List<Item<?>> items = schema.itemsWithEnvironmentVariables();

		for (Item<?> i : items)
		{
			OptEvaluator eval =  i.getEval();
			Object o = eval.getValue();
			if (o != null)
			{
				continue; // Already has a value, ignore
			}

			String opt = i.getName();
			String ev = i.getEv();

			String prop = System.getenv(ev);
			if (prop == null)
			{
				throw new ArgsException(ErrorCode.MISSING_ENVIRONMENT_VARIABLE, opt, ev);
			}

			Iterator<String> it = Arrays.asList(prop).iterator();
			eval.set(it);
		}
	}

	private void parseCommandLine(String[] args)
	throws ArgsException
	{
		if (args == null)
		{
			throw new ArgsException(NULL_ARGUMENT_ARRAY);
		}

		List<String> argList = Arrays.asList(args);

		for (argumentIterator = argList.listIterator(); argumentIterator.hasNext(); )
		{
			String argString = argumentIterator.next();
			if (argString.equals("--"))
			{
				while (argumentIterator.hasNext())
				{
					arguments.add(argString);
				}
			}
			else if (argString.equals("-"))
			{
				// Currently not handling - with a common unix meaning to read input from STDIN
				//
				throw new ArgsException(ErrorCode.MISSING_OPTION_NAME);
			}
			else if (argString.startsWith("--"))
			{
				String rest = argString.substring(2);
				handleOption(rest, argumentIterator);
			}
			else if (argString.startsWith("-"))
			{
				String rest = argString.substring(1, 2);
				handleOption(rest, argumentIterator);
			}
			else
			{
				arguments.add(argString);
			}
		}
	}

	private void handleOption(String option, ListIterator<String> args) throws ArgsException
	{
		if (option.length() < 1)
		{
			throw new ArgsException(ErrorCode.MISSING_OPTION_NAME);
		}

		// First look for the exact name.
		// If it's not found, treat it as a prefix and look for
		// a single option definition with that prefix.
		// Failing that, throw an error.
		//
		Item<?> item = schema.getItem(option);

		if (item == null)
		{
			List<Item<?>> itemList = schema.itemsWithPrefix(option);

			int count = itemList.size();
			switch (count)
			{
			case 1:
				item = itemList.get(0);
				break;
			case 0:
				throw new ArgsException(UNEXPECTED_OPTION, option, null);
			default:
				String msg = itemList.stream().map(i -> { return i.getName(); }).collect(Collectors.joining(", "));
				throw new ArgsException(AMBIGUOUS_OPTION_NAME, option, msg);
			}
		}

		OptEvaluator<?> eval = item.getEval();
		optionsFound.add(item.getName());

		try
		{
			eval.set(argumentIterator);
		}
		catch (ArgsException e)
		{
			e.setOption(option);
			throw e;
		}
	}

	private static void provideHelpIfRequested(Schema schema, Args args)
	{
		if (! args.optionProvidedOnCommandLine(StandardOptions.HELP))
		{
			return;
		}

		StatusReporter reporter = new StatusReporter(schema, args);

		String output = reporter.getCommandLineStatusReport();

		System.out.println(output);
		System.exit(0);
	}


	private void validateCommandLine()
	{
		// TODO Auto-generated method stub
	}
}
