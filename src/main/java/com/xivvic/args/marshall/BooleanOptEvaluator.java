package com.xivvic.args.marshall;

import static com.xivvic.args.marshall.Cardinality.ZERO;

import java.util.Iterator;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.util.BooleanUtil;

import lombok.Getter;

public class BooleanOptEvaluator extends OptEvaluatorBase<Boolean>
{
	@Getter
	private Boolean value = null;

	private Boolean dv = null;

	@Override
	protected void doSet(Iterator<String> currentArgument) throws ArgsException
	{
		// If this option is present in the arguments, then it will be set
		// to true.
		//
		value = true;
	}

	@Override
	public String toString()
	{
		return "BooleanOptEval[value=" + value + ", dv= " + dv + ", count="  + count() + "]";
	}

	@Override
	public Cardinality cardinality()
	{
		return ZERO;
	}

	@Override
	public void setDefaultValue(final String dv) throws SchemaException
	{
		this.dv = BooleanUtil.parseBoolean(dv);
	}

	@Override
	public final Boolean getDefault()
	{
		return dv;
	}
}
