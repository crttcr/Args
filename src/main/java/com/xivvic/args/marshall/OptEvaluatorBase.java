package com.xivvic.args.marshall;

import java.util.Iterator;
import java.util.Objects;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.OptionType;

public abstract class OptEvaluatorBase<T>
implements OptEvaluator<T>
{
	private int count = 0;

	protected abstract void doSet(Iterator<String> currentArgument) throws ArgsException;

	@Override
	public abstract void setDefaultValue(String dv) throws SchemaException;

	@Override
	public final void set(Iterator<String> currentArgument) throws ArgsException
	{
		count++;
		doSet(currentArgument);
	}


	@Override
	public int count() { return count; }

	@SuppressWarnings("unchecked")
	public static <T> OptEvaluator<T> getEvaluatorForType(OptionType type)
	{
		Objects.requireNonNull(type);

		switch (type)
		{
		case BOOLEAN:      return (OptEvaluator<T>) new BooleanOptEvaluator();
		case STRING:       return (OptEvaluator<T>) new StringOptEvaluator();
		case STRING_LIST:  return (OptEvaluator<T>) new StringListOptEvaluator();
		case INTEGER:      return (OptEvaluator<T>) new IntegerOptEvaluator();
		case DOUBLE:       return (OptEvaluator<T>) new DoubleOptEvaluator();
		case DATE:         return (OptEvaluator<T>) new DateOptEvaluator();
		case TIME:         return (OptEvaluator<T>) new TimeOptEvaluator();
		case PATH:         return (OptEvaluator<T>) new PathOptEvaluator();
		case FILE:         return (OptEvaluator<T>) new FileOptEvaluator();

		default: throw new IllegalArgumentException("Not implemented: " + type);
		}
	}

}
