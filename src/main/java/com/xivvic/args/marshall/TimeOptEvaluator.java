package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DATE;
import static com.xivvic.args.error.ErrorCode.INVALID_TIME;
import static com.xivvic.args.error.ErrorCode.MISSING_TIME;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class TimeOptEvaluator extends OptEvaluatorBase<LocalTime>
{
	@Getter
	private LocalTime value = null;

	private LocalTime dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String parameter = currentArgument.next();
			value = TimeOptEvaluator.string2time(parameter);
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
	public Cardinality cardinality()
	{
		return ONE;
	}

	@Override
	public String toString()
	{
		return "TimeOptEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public LocalTime getDefault()
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
			this.dv = TimeOptEvaluator.string2time(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DATE, "", dv);
		}
	}

	private static LocalTime string2time(String input) throws ArgsException
	{
		try
		{
			LocalTime rv = LocalTime.parse(input);
			return rv;
		}
		catch (DateTimeParseException e)
		{
			throw new ArgsException(INVALID_DATE);
		}
	}
}