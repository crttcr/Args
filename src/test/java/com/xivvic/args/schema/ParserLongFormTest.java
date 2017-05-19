package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.TestUtil;
import com.xivvic.args.schema.item.Item;

public class ParserLongFormTest
{
	public static String TEST_FILE_LOCATION = "src/test/resources/definitions";

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

	@Test
	public void testUnclosedPropertyNameGetsErrorRecovery() throws Exception
	{
		// Arrange
		//
		String spec = "[x";

		// Act
		//
		Map<String, Map<String, String>> result = subject.parse(spec);
		Map<String, String> def = result.get("x");

		// Assert
		//
		assertNotNull(def);
		assertEquals("x", def.get(Item.NAME));
		assertEquals(OptionType.BOOLEAN.name(), def.get(Item.TYPE));
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
		String spec = TestUtil.readFromTestResourceFile(TEST_FILE_LOCATION, "integer.all.argspec");

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
		assertEquals("The port on which the server accepts connections", def.get(Item.DESCRIPTION));
		assertEquals("MYAPP_SERVER_PORT", def.get(Item.ENV_VAR));
		assertEquals("true", def.get(Item.REQUIRED));
	}

	@Test
	public void testMultipleProperties() throws Exception
	{
		// Arrange
		//
		String spec = TestUtil.readFromTestResourceFile(TEST_FILE_LOCATION, "multiple.argspec");

		// Act
		//
		Map<String, Map<String, String>> result = subject.parse(spec);
		Map<String, String> verbose = result.get("verbose");
		Map<String, String> silent = result.get("silent");
		Map<String, String> brands = result.get("brands");
		Map<String, String> host = result.get("host");
		Map<String, String> port = result.get("port");

		// Assert
		//
		assertNotNull(verbose);
		assertNotNull(silent);
		assertNotNull(brands);
		assertNotNull(host);
		assertNotNull(port);
	}


}
