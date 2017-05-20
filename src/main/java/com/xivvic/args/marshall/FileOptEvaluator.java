package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DOUBLE;
import static com.xivvic.args.error.ErrorCode.INVALID_FILE;
import static com.xivvic.args.error.ErrorCode.MISSING_FILE;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class FileOptEvaluator extends OptEvaluatorBase<File>
{
	@Getter
	private File value = null;
	private File dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String parameter = currentArgument.next();
			value = FileOptEvaluator.string2file(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_FILE);
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
		return "FileOptEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public File getDefault()
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
			this.dv = string2file(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DOUBLE, "", dv);
		}
	}


	private static File string2file(String input) throws ArgsException
	{
		try
		{
			if (input == null || input.trim().length() == 0)
			{
				throw new ArgsException(INVALID_FILE);
			}

			File rv = new File(input);
			return rv;
		}
		catch (NullPointerException e)
		{
			throw new ArgsException(INVALID_DOUBLE);
		}
	}
}
