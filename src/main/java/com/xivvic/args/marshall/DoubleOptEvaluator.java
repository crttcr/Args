package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DOUBLE;
import static com.xivvic.args.error.ErrorCode.MISSING_DOUBLE;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class DoubleOptEvaluator extends OptEvaluatorBase<Double>
{
	@Getter
	private Double value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = Double.parseDouble(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_DOUBLE);
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_DOUBLE);
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
		return "DoubleEval[called = " + count() + ", value = " + value + "]";
	}

}
