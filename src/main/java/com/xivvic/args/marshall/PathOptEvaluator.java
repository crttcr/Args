package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DATE;
import static com.xivvic.args.error.ErrorCode.INVALID_PATH;
import static com.xivvic.args.error.ErrorCode.MISSING_PATH;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class PathOptEvaluator extends OptEvaluatorBase<Path>
{
	@Getter
	private Path value = null;

	private Path dv = null;

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
	public Cardinality cardinality()
	{
		return ONE;
	}

	@Override
	public String toString()
	{
		return "PathOptEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public Path getDefault()
	{
		return dv;
	}

	@Override
	public void setDefaultValue(String dv) throws SchemaException
	{
		if (dv == null)
		{
			return;
		}

		try
		{
			this.dv = PathOptEvaluator.string2path(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DATE, "", dv);
		}
	}

	private static Path string2path(String input) throws ArgsException
	{
		try
		{
			Path rv = Paths.get(input);
			return rv;
		}
		catch (InvalidPathException e)
		{
			throw new ArgsException(INVALID_DATE);
		}
	}
}
