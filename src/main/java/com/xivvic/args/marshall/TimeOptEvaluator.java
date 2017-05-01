package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_TIME;
import static com.xivvic.args.error.ErrorCode.MISSING_TIME;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.Getter;

public class TimeOptEvaluator extends OptEvaluatorBase<LocalTime>
{
	@Getter
	private LocalTime value = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		String parameter = null;

		try
		{
			parameter = currentArgument.next();
			value = LocalTime.parse(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_TIME);
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_TIME);
		}
	}

	@Override
	public String toString()
	{
		return "TimeOptEval[called = " + count() + ", value = " + value + "]";
	}
}
