package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.INVALID_DOUBLE;
import static com.xivvic.args.error.ErrorCode.MISSING_INTEGER;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.Getter;

public class IntegerOptEvaluator extends OptEvaluatorBase<Integer>
{
	@Getter
	private Integer value = null;
	private Integer dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String parameter = currentArgument.next();
			value = IntegerOptEvaluator.string2integer(parameter);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_INTEGER);
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
		return "IntegerEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public Integer getDefault()
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
			this.dv = string2integer(dv);
		}
		catch (ArgsException ae)
		{
			throw new SchemaException(INVALID_DOUBLE, "", dv);
		}
	}


	private static Integer string2integer(String input) throws ArgsException
	{
		try
		{
			Integer rv = Integer.parseInt(input);
			return rv;
		}
		catch (NumberFormatException e)
		{
			throw new ArgsException(INVALID_DOUBLE);
		}
	}
}
