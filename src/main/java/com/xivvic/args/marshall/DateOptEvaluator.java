package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DATE;
import static com.xivvic.args.error.ErrorCode.MISSING_DATE;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class DateOptEvaluator extends OptEvaluatorBase<LocalDate>
{
	@Getter
	private LocalDate value = null;

	private LocalDate dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String parameter = currentArgument.next();
			value  = DateOptEvaluator.string2date(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_DATE);
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
		return "DateOptEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public LocalDate getDefault()
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
			this.dv = DateOptEvaluator.string2date(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DATE, "", dv);
		}
	}


	private static LocalDate string2date(String input) throws ArgsException
	{
		try
		{
			LocalDate rv = LocalDate.parse(input);
			return rv;
		}
		catch (DateTimeParseException e)
		{
			throw new ArgsException(INVALID_DATE);
		}
	}

}
