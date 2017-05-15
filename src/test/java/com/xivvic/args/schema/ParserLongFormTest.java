package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.schema.item.Item;

public class ParserLongFormTest
{
	private ParserLongForm subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new ParserLongForm();
	}

	@Test
	public void testCommentLineReturnsEmptyMap() throws Exception
	{
		// Arrange
		//
		String spec = "# This is a comment line.";

		// Act
		//
		Map<String, Map<String, String>> map = subject.parse(spec);

		// Assert
		//
		assertNotNull(map);
		assertTrue(map.isEmpty());
	}

	@Test(expected = ArgsException.class)
	public void testUnclosedPropertyNameCausesException() throws Exception
	{
		// Arrange
		//
		String spec = "[x";

		// Act
		//
		subject.parse(spec);
	}

	@Test
	public void testDefaultPropertyIsBoolean() throws Exception
	{
		// Arrange
		//
		String option = "verbose";
		String spec = "[" + option + "]";

		// Act
		//
		Map<String, Map<String, String>> result = subject.parse(spec);
		Map<String, String> def = result.get(option);

		// Assert
		//
		assertNotNull(def);
		assertEquals(option, def.get(Item.NAME));
		assertEquals(OptionType.BOOLEAN.name(), def.get(Item.TYPE));
	}

	@Test
	public void testIntegerPropertyWithAllFieldsSet() throws Exception
	{
		// Arrange
		//
		String option = "port";
		String spec = "#Starting comment\n[" + option + "]\n";
		spec = spec + "## This is a comment\n";
		spec = spec + "type=INTEGER\n";
		spec = spec + "dv=80\n";
		spec = spec + "description=The server port to listen on\n";
		spec = spec + "ev=APPLICATION_SERVER_PORT\n";
		spec = spec + "required=true\n";

		// Act
		//
		Map<String, Map<String, String>> result = subject.parse(spec);
		Map<String, String> def = result.get(option);

		// Assert
		//
		assertNotNull(def);
		assertEquals(option, def.get(Item.NAME));
		assertEquals(OptionType.INTEGER.name(), def.get(Item.TYPE));
		assertEquals("80", def.get(Item.DEFAULT));
		assertEquals("The server port to listen on", def.get(Item.DESCRIPTION));
		assertEquals("APPLICATION_SERVER_PORT", def.get(Item.ENV_VAR));
		assertEquals("true", def.get(Item.REQUIRED));
	}

}
