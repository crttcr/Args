package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.schema.item.Item;

public class ParserShortFormTest
{
	private ParserShortForm subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new ParserShortForm(ErrorStrategy.FAIL_FAST);
	}

	@Test
	public void testSchemaBuilderSingleBooleanTest() throws Exception
	{
		// Arrange
		//
		String opt = "b";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(opt);
		Map<String, String> def = parseResult.get(opt);

		// Assert
		//
		assertSimpleItemForm(def, opt, OptionType.BOOLEAN);
	}

	@Test
	public void testSchemaBuilderSingleStringTest() throws Exception
	{
		// Arrange
		//
		String opt = "s";
		String fmt = "s*";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def = parseResult.get(opt);

		// Assert
		//
		assertSimpleItemForm(def, opt, OptionType.STRING);
	}

	@Test
	public void testSchemaBuilderOneDouble() throws Exception
	{
		// Arrange
		//
		String opt = "d";
		String fmt = "d##";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def = parseResult.get(opt);

		// Assert
		//
		assertSimpleItemForm(def, opt, OptionType.DOUBLE);
	}

	@Test
	public void testSchemaBuilderOneStringList() throws Exception
	{
		// Arrange
		//
		String opt = "l";
		String fmt = "l[*]";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def = parseResult.get(opt);

		// Assert
		//
		assertSimpleItemForm(def, opt, OptionType.STRING_LIST);
	}

	@Test
	public void testSchemaBuilderSingleIntegerTest() throws Exception
	{
		// Arrange
		//
		String opt = "i";
		String fmt = "i#";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def = parseResult.get(opt);

		// Assert
		//
		assertSimpleItemForm(def, opt, OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBSITest() throws Exception
	{
		// Arrange
		//
		String fmt = "b,s*,i#";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def1 = parseResult.get("b");
		Map<String, String> def2 = parseResult.get("s");
		Map<String, String> def3 = parseResult.get("i");

		// Assert
		//
		assertSimpleItemForm(def1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(def2, "s", OptionType.STRING);
		assertSimpleItemForm(def3, "i", OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBIBWithSpaces() throws Exception
	{
		// Arrange
		//
		String fmt = "b, i# , x";

		// Act
		//
		Map<String, Map<String, String>> parseResult = subject.parse(fmt);
		Map<String, String> def1 = parseResult.get("b");
		Map<String, String> def2 = parseResult.get("i");
		Map<String, String> def3 = parseResult.get("x");

		// Assert
		//
		assertSimpleItemForm(def1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(def2, "i", OptionType.INTEGER);
		assertSimpleItemForm(def3, "x", OptionType.BOOLEAN);
	}

	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private void assertSimpleItemForm(Map<String, String> def, String name, OptionType type)
	{
		assertNotNull(def);
		assertEquals(name, def.get(Item.NAME));
		assertEquals(type.name(), def.get(Item.TYPE));

		assertNull(def.get(Item.REQUIRED));
		assertNull(def.get(Item.DESCRIPTION));
		assertNull(def.get(Item.DEFAULT));
		assertNull(def.get(Item.ENV_VAR));
	}
}
