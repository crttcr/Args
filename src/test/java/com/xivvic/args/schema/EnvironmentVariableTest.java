package com.xivvic.args.schema;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xivvic.args.Args;
import com.xivvic.args.TestUtil;
import com.xivvic.args.error.ArgsException;


public class EnvironmentVariableTest
{
	@Test
	public void testHomeValueFromEnvironment() throws ArgsException
	{
		// Arrange
		//
		String[] arguments = {"a", "b"};
		String defs = TestUtil.readFromTestResourceFile("string.envvar.argspec");
		Schema schema = new Text2Schema().createSchema(defs);

		// Act
		//
		Args args = new Args(schema, arguments);
		String home = args.getValue("home");

		// Assert
		//
		assertNotNull(home);
	}
}
