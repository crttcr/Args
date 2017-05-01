package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_FILE;
import static com.xivvic.args.error.ErrorCode.MISSING_FILE;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class FileOptEvaluator extends OptEvaluatorBase<File>
{
	@Getter
	private File value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			if (parameter == null || parameter.trim().length() == 0)
			{
				throw new ArgsException(INVALID_FILE);
			}
			value = new File(parameter);
		}
		catch (NoSuchElementException | NullPointerException e)
		{
			throw new ArgsException(MISSING_FILE);
		}
	}

	@Override
	public String toString()
	{
		return "FileOptEval[called = " + count() + ", value = " + value + "]";
	}
}
