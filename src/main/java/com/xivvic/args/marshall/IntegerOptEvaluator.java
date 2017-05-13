package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_INTEGER;
import static com.xivvic.args.error.ErrorCode.MISSING_INTEGER;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class IntegerOptEvaluator extends OptEvaluatorBase<Integer>
{
	@Getter
	private Integer value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = Integer.parseInt(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_INTEGER);
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_INTEGER);
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
		return "Integer[called = " + count() + ", value = " + value + "]";
	}

}
