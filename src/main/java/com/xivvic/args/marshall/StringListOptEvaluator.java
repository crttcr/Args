package com.xivvic.args.marshall;

import static com.xivvic.args.error.ErrorCode.MISSING_STRING_LIST;
import static com.xivvic.args.marshall.Cardinality.MULTIPLE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class StringListOptEvaluator extends OptEvaluatorBase<List<String>>
{
	private List<String> value = null;
	private List<String> dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		try
		{
			String s = currentArgument.next();
			if (value == null)
			{
				value = new ArrayList<String>();
			}

			value.add(s);
		}
		catch (NoSuchElementException e)
		{
			throw new ArgsException(MISSING_STRING_LIST);
		}
	}

	@Override
	public Cardinality cardinality()
	{
		return MULTIPLE;
	}

	@Override
	public String toString()
	{
		return "StringListEval[called = " + count() + ", value = " + value + "]";
	}

	@Override
	public List<String> getValue()
	{
		if (value == null)
		{
			return null;
		}

		return Collections.unmodifiableList(value);
	}

	@Override
	public List<String> getDefault()
	{
		if (dv == null)
		{
			return null;
		}

		return Collections.unmodifiableList(dv);
	}

	@Override
	public void setDefaultValue(String dv) throws SchemaException
	{
		if (dv == null)
		{
			return;
		}

		this.dv = StringListOptEvaluator.string2list(dv);
	}

	private static List<String> string2list(String input)
	{
		if (input == null)
		{
			return null;
		}
		try
		{
			List<String> rv = new ArrayList<>();

			String[] parts = input.split(" ");
			for (String s : parts)
			{
				rv.add(s);
			}

			return rv;
		}
		catch (PatternSyntaxException cannotHappen)
		{
			return null;
		}
	}
}
