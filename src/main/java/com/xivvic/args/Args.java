package com.xivvic.args;

import static com.xivvic.args.error.ErrorCode.NO_SCHEMA;
import static com.xivvic.args.error.ErrorCode.NULL_ARGUMENT_ARRAY;
import static com.xivvic.args.error.ErrorCode.UNEXPECTED_OPTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.Item;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.SchemaBuilder;


/**
 * Argument and options command line processor inspired by
 * Robert C. Martin's Clean Code, chapter 14.
 *
 * However, this class no longer is responsible for parsing
 * the definition of the program options and processing the command line.
 * Seems like two different things.
 *
 * Parsing and maintaining option definitions is the responsibility
 * of the Schema and SchemaBuilder objects.
 */
public class Args
{
	private Set<String> optionsFound = new HashSet<String>();
	private ListIterator<String> argumentIterator;
	private final List<String> arguments = new ArrayList<>();
	private final Schema schema;

	public Args(Schema schema, String[] args)
		throws ArgsException
	{
		if (schema == null)
		{
			throw new ArgsException(NO_SCHEMA);
		}
		this.schema = schema;
		initialize(args);
	}

	public Args(String defs, String[] args)
		throws ArgsException
	{
		if (defs == null)
		{
			throw new ArgsException(NO_SCHEMA);
		}

		this.schema = new SchemaBuilder("No_name").build(defs);
		initialize(args);
	}

	private void initialize(String[] args) throws ArgsException
	{
		parseCommandLine(args);
		addMissingEnvironmentVariables();
		validateCommandLine();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addMissingEnvironmentVariables() throws ArgsException
	{
		List<Item> items = schema.requiredWithEnvironments();

		for (Item i : items)
		{
			OptEvaluator eval =  i.getEval();
			Object o = eval.getValue();
			if (o != null)
			{
				continue;
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
			if (argString.startsWith("--"))
			{
				String rest = argString.substring(2);
				handleLongFormOption(rest, argumentIterator);
			}
			else if (argString.startsWith("-"))
			{
				String rest = argString.substring(1);
				handleShortFormOption(rest, argumentIterator);
			}
			else
			{
				arguments.add(argString);
			}
		}
	}

	private void handleLongFormOption(String option, ListIterator<String> args) throws ArgsException
	{
		if (option.length() < 1)
		{
			throw new ArgsException(ErrorCode.MISSING_OPTION_NAME);
		}

		Item<?> item = schema.getItem(option);

		if (item == null)
		{
			throw new ArgsException(UNEXPECTED_OPTION, option, null);
		}

		OptEvaluator<?> eval = item.getEval();
		optionsFound.add(option);

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


	private void handleShortFormOption(String stripped, ListIterator<String> args)
		throws ArgsException
	{
		if (stripped.length() < 1)
		{
			throw new ArgsException(ErrorCode.MISSING_OPTION_NAME);
		}

		String option = stripped.substring(0, 1);

		Item<?> item = schema.getItem(option);

		if (item == null)
		{
			throw new ArgsException(UNEXPECTED_OPTION, option, null);
		}

		OptEvaluator<?> eval = item.getEval();
		optionsFound.add(option);
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

	private void validateCommandLine()
	{
		// TODO Auto-generated method stub
	}

	public <T> T getValue(String option) {
		Item<T> item = schema.getItem(option);
		OptEvaluator<T> eval = item.getEval();
		T rv = eval.getValue();
		return rv;
	}

	public boolean has(String opt)
	{
		if (opt == null)
		{
			return false;
		}

		return optionsFound.contains(opt);
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
}