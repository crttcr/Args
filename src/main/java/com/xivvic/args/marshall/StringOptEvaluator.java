package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.MISSING_STRING;
import static com.xivvic.args.marshall.Cardinality.ONE;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.xivvic.args.error.ArgsException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper=false)
public class StringOptEvaluator extends OptEvaluatorBase<String>
{
	@Getter
	private String value = null;

	private String dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			value = currentArgument.next();
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_STRING);
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
		return "String[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public String getDefault()
	{
		return dv;
	}

	@Override
	public void setDefaultValue(String dv)
	{
		this.dv = dv;
	}
}
