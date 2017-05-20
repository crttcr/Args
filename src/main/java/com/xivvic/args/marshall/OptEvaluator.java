package com.xivvic.args.marshall;

import java.util.Iterator;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.SchemaException;

public interface OptEvaluator<T>
{
	// Note, this method establishes a default value for an option if it is not set
	// via the command line. This method is called during option specification time,
	// not at argument processing time.
	//
	void setDefaultValue(String dv) throws SchemaException;

	// This method is called during argument processing to bind an argument value
	// within the OptEvaluator.
	//
	void set(Iterator<String> currentArgument) throws ArgsException;

	// The number of times a value is set for this OptEvaluator
	//
	int count();

	// The bound value
	//
	T getValue();

	// The default value, or null if there is none established
	//
	T getDefault();

	// What is the permitted / expected cardinality of this option
	// ZERO, ONE, MULTIPLE
	//
	Cardinality cardinality();
}
