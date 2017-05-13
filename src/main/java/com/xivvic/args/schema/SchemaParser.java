package com.xivvic.args.schema;

import com.xivvic.args.error.ArgsException;

public interface SchemaParser
{
	boolean parseLine(String line) throws ArgsException;
	Schema getSchema() throws ArgsException;
}
