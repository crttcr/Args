package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DATE;
import static com.xivvic.args.error.ErrorCode.MISSING_DATE;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class DateOptEvaluator extends OptEvaluatorBase<LocalDate>
{
	@Getter
	private LocalDate value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = LocalDate.parse(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_DATE);
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_DATE);
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
		return "DateOptEval[called = " + count() + ", value = " + value + "]";
	}
}
