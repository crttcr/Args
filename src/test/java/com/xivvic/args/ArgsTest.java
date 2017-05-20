package com.xivvic.args;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.Text2Schema;

public class ArgsTest
{
	@Test(expected=ArgsException.class)
	public void testDefaultFacotryMethod() throws Exception
	{
		//
		String[] args = {"--silent", "-help"};
		Args arg = Args.createDefaultInstance(args);

		// Act
		//
		Boolean verbose = arg.getValue("verbose");
		Boolean silent = arg.getValue("silent");
		Boolean help = arg.getValue("help");

		// Assert
		//
		assertFalse(verbose);
		assertTrue(silent);
		assertFalse(help);

	}

	@Test(expected=ArgsException.class)
	public void testConstructWithNullStringDefs() throws Exception
	{
		String[] args = {"-x", "radio"};
		String defs = null;
		@SuppressWarnings("unused")

		Args arg = new Args(defs, args);
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithNullSchema() throws Exception
	{
		String[] args = {"-x", "radio"};
		Schema schema = null;
		@SuppressWarnings("unused")

		Args arg = new Args(schema, args);
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithEmptySchema() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("");
		String[] args = {"-x", "radio"};
		@SuppressWarnings("unused")
		Args arg = new Args(schema, args);
	}

	@Test(expected=ArgsException.class)
	public void testConstructWithNullArgList() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("x");
		@SuppressWarnings("unused")
		Args arg = new Args(schema, null);
	}

	@Test
	public void testConstructionWhenArgumentListIsEmpty() throws Exception
	{
		// Arrange & Act
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {};
		Args arg = new Args(schema, args);

		// Assert
		//
		assertNotNull(arg);
	}

	@Test(expected=ArgsException.class)
	public void testNakedDashThrows() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"-", "radio"};
		new Args(schema, args);
	}

	@Test
	public void testGetBooleanSuccessWhenSet() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"-x", "radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue("x");

		// Assert
		//
		assertTrue(isSet);
	}

	@Test
	public void testGetBooleanSuccessWhenNotSet() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue("x");

		// Assert
		//
		assertNull(isSet);
	}

	@Test
	public void testGetStringSuccess() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*");
		String[] args = {"-x", "radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		String s = arg.getValue("x");

		// Assert
		//
		assertNotNull(s);
		assertEquals("radio", s);
	}

	@Test
	public void testGetIntegerSuccess() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"-y", "239"};
		Args arg = new Args(schema, args);

		// Act
		//
		Integer i = arg.getValue("y");

		// Assert
		//
		assertNotNull(i);
		assertEquals(239, i.intValue());
	}

	@Test
	public void testHasWhenSingleBooleanNotProvided() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		boolean isSet = arg.has("x");

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void testHasNull() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args arg = new Args(schema, args);

		// Act
		//
		boolean isSet = arg.has(null);

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void testArgCountWhenNoneProvided() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {};
		Args arg = new Args(schema, args);

		// Act
		//
		int count = arg.argumentCount();

		// Assert
		//
		assertEquals(0, count);
	}

	@Test
	public void testArgCountWithOneArgumentNoOptions() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"file.a"};
		Args arg = new Args(schema, args);

		// Act
		//
		int count = arg.argumentCount();

		// Assert
		//
		assertEquals(1, count);
	}

	@Test
	public void testGetArgumentWhenNoneProvided() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {};
		Args arg = new Args(schema, args);

		// Act
		//
		String s = arg.getArgument(0);
		String t = arg.getArgument(-1);

		// Assert
		//
		assertNull(s);
		assertNull(t);
	}

	@Test
	public void testGetArgumentWithOneArgumentNoOptions() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"file.a"};
		Args arg = new Args(schema, args);

		// Act
		//
		String s = arg.getArgument(0);
		String t = arg.getArgument(-1);
		String u = arg.getArgument(1);

		// Assert
		//
		assertEquals(args[0], s);
		assertNull(t);
		assertNull(u);
	}


}
