package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DOUBLE;
import static com.xivvic.args.error.ErrorCode.MISSING_DOUBLE;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class DoubleOptEvaluator extends OptEvaluatorBase<Double>
{
	@Getter
	private Double value = null;

	private Double dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String parameter = currentArgument.next();
			value = string2double(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_DOUBLE);
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
		return "DoubleEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public Double getDefault()
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
			this.dv = string2double(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DOUBLE, "", dv);
		}
	}


	private static Double string2double(String input) throws ArgsException
	{
		try
		{
			Double rv = Double.parseDouble(input);
			return rv;
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_DOUBLE);
		}
	}

}
