package com.xivvic.args.marshall;

import java.util.Iterator;

import com.xivvic.args.error.ArgsException;

public interface OptEvaluator<T>
{
	void set(Iterator<String> currentArgument) throws ArgsException;

	// The number of times a value is set for this OptEvaluator
	//
	int count();

	// The bound value
	//
	T getValue();

	Cardinality cardinality();
}
