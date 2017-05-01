package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_PATH;
import static com.xivvic.args.error.ErrorCode.MISSING_PATH;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class PathOptEvaluator extends OptEvaluatorBase<Path>
{
	@Getter
	private Path value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = Paths.get(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_PATH);
		}
		catch (InvalidPathException e)
		{
			throw new ArgsException(INVALID_PATH);
		}
	}

	@Override
	public String toString()
	{
		return "PathOptEval[called = " + count() + ", value = " + value + "]";
	}
}
