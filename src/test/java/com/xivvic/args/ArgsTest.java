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
	private Args subject;

	@Test
	public void onCreate_withDefaultConfig_thenHelpAndSilentAreSetNotVerbose() throws Exception
	{
		// Arrange
		//
		String[] args = {"--silent", "-help"};
		Args arg = Args.createDefaultInstance(args);

		// Act
		//
		Boolean help = arg.getValue("help");
		Boolean silent = arg.getValue("silent");
		Boolean verbose = arg.getValue("verbose");

		// Assert
		//
		assertTrue(help);
		assertTrue(silent);
		assertNull(verbose);
	}

	@Test(expected=ArgsException.class)
	public void onCreateOrThrow_withNullString_thenThrowException() throws Exception
	{
		String[] args = {"-x", "radio"};
		String defs = null;
		Args.processOrThrowException(defs, args);
	}

	@Test(expected=ArgsException.class)
	public void onCreateOrThrow_withNullSchema_thenThrowException() throws Exception
	{
		String[] args = {"-x", "radio"};
		Schema schema = null;
		Args.processOrThrowException(schema, args);
	}

	@Test(expected=ArgsException.class)
	public void onCreateOrThrow_withEmptySchema_thenThrowException() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("");
		String[] args = {"-x", "radio"};
		Args.processOrThrowException(schema, args);
	}

	@Test(expected=ArgsException.class)
	public void onCreateOrThrow_withNullArgList_thenThrowException() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("x");
		Args.processOrThrowException(schema, null);
	}

	@Test
	public void onCreateOrThrow_withEmptyArgList_thenReturnValidArgs() throws Exception
	{
		// Arrange & Act
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {};
		Args      arg = Args.processOrThrowException(schema, args);

		// Assert
		//
		assertNotNull(arg);
	}

	@Test(expected=ArgsException.class)
	public void onCreateOrThrow_withNakedDash_thenThrowException() throws Exception
	{
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"-", "radio"};
		Args.processOrThrowException(schema, args);
	}

	@Test
	public void onGetValue_withProvidedBooleanOption_thenReturnTrue() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"-x", "radio"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue("x");

		// Assert
		//
		assertTrue(isSet);
	}

	@Test
	public void onGetValue_withBooleanOptionNotProvided_thenNull() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		Boolean isSet = arg.getValue("x");

		// Assert
		//
		assertNull(isSet);
	}

	@Test
	public void onGetValue_withProvidedStringOption_thenReturnTheOptionFollower() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*");
		String[] args = {"-x", "radio"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		String s = arg.getValue("x");

		// Assert
		//
		assertNotNull(s);
		assertEquals("radio", s);
	}

	@Test
	public void onGetValue_withProvidedIntegerOption_thenReturnTheIntegerValue() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"-y", "239"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		Integer i = arg.getValue("y");

		// Assert
		//
		assertNotNull(i);
		assertEquals(239, i.intValue());
	}

	@Test
	public void onOptionHasValue_withNoValueAvailable_thenReturnFalse() throws Exception
	{
		// Arrange
		//
		String name   = "x";
		Schema schema = new Text2Schema().createSchema("x*");
		String[] args = {"apple", "239"};
		subject       = Args.processOrThrowException(schema, args);

		// Act
		//
		boolean result = subject.optionHasValue(name);

		// Assert
		//
		assertFalse(result);
	}

	@Test
	public void onOptionHasValue_withValueFromArguments_thenReturnTrue() throws Exception
	{
		// Arrange
		//
		String name   = "x";
		Schema schema = new Text2Schema().createSchema("x*");
		String[] args = {"-x", "xray"};
		subject       = Args.processOrThrowException(schema, args);

		// Act
		//
		boolean result = subject.optionHasValue(name);

		// Assert
		//
		assertTrue(result);
	}

	@Test
	public void onOptionHasValue_withValueFromDefault_thenReturnTrue() throws Exception
	{
		// Arrange
		//
		String defs = "[v] \n [verb] dv=RUN type=STRING";
		Schema schema = new Text2Schema().createSchema(defs);
		String[] args = {};
		String name   = "verb";
		subject       = Args.processOrThrowException(schema, args);

		// Act
		//
		boolean result = subject.optionHasValue(name);

		// Assert
		//
		assertTrue(result);
	}

	@Test
	public void onOptionProvidedTest_withOptionNotProvided_thenReturnFalse() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		boolean isSet = arg.optionProvidedOnCommandLine("x");

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void onOptionProvidedTest_withNullParameter_thenReturnFalse() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x");
		String[] args = {"radio"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		boolean isSet = arg.optionProvidedOnCommandLine(null);

		// Assert
		//
		assertFalse(isSet);
	}

	@Test
	public void onArgumentCount_withEmptyArguments_thenReturnZero() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {};
		Args arg = Args.processOrThrowException(schema, args);

		// Act
		//
		int count = arg.argumentCount();

		// Assert
		//
		assertEquals(0, count);
	}

	@Test
	public void onArgumentCount_withNoOptionsOneArg_thenReturnOne() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"file.a"};
		Args      arg = Args.processOrThrowException(schema, args);

		// Act
		//
		int count = arg.argumentCount();

		// Assert
		//
		assertEquals(1, count);
	}

	@Test
	public void onGetArgument_withNoProvidedArguments_thenReturnNull() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {};
		Args      arg = Args.processOrThrowException(schema, args);

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
	public void onGetArgument_withNoProvidedArguments_thenReturnTheArgument() throws Exception
	{
		// Arrange
		//
		Schema schema = new Text2Schema().createSchema("x*,y#");
		String[] args = {"file.a"};
		Args arg = Args.processOrThrowException(schema, args);

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

	@Test(expected = ArgsException.class)
	public void onCreateOrThrow_withAmbiguousOption_thenThrowException() throws Exception
	{
		String   defs = "[verbose] \n [verb] dv=RUN type=STRING";
		Schema schema = new Text2Schema().createSchema(defs);
		String[] args = {"-v"};
		Args.processOrThrowException(schema, args);
	}

	@Test
	public void onGetValue_withExactOptionThatIsAlsoAPrefix_thenReturnTheOption() throws Exception
	{
		// Arrange
		//
		String   defs = "[v] \n [verb] dv=RUN type=STRING";
		Schema schema = new Text2Schema().createSchema(defs);
		String[] args = {"-v"};

		// Act
		//
		Args  arg = Args.processOrThrowException(schema, args);
		Boolean v = arg.getValue("v");

		// Assert
		//
		assertNotNull(v);
		assertTrue(v);
	}

	@Test
	public void onGetValue_withUniquePrefix_thenReturnTheAssociatedOption() throws Exception
	{
		// Arrange
		//
		String   defs = "[v] \n [verb] dv=RUN type=STRING";
		Schema schema = new Text2Schema().createSchema(defs);
		String[] args = {"--ve", "JUMP"};

		// Act
		//
		Args    arg = Args.processOrThrowException(schema, args);
		Boolean   v = arg.getValue("v");
		String verb = arg.getValue("verb");

		// Assert
		//
		assertNotNull(verb);
		assertNull(v);
		assertEquals("JUMP", verb);
	}

}
