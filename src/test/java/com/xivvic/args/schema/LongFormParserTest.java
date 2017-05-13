package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.schema.item.Item;

public class LongFormParserTest
{
	private LongFormParser subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new LongFormParser();
	}

	@Test
	public void testCommentLineReturnsFalse() throws Exception
	{
		// Arrange
		//
		String line = "#";

		// Act
		//
		boolean adds = subject.parseLine(line);

		// Assert
		//
		assertFalse(adds);
	}

	@Test(expected = ArgsException.class)
	public void testUnclosedPropertyNameCausesException() throws Exception
	{
		// Arrange
		//
		String line = "[x";

		// Act
		//
		subject.parseLine(line);
	}

	@Test
	public void testDefaultPropertyIsBoolean() throws Exception
	{
		// Arrange
		//
		String option = "verbose";
		String line = "[" + option + "]";

		// Act
		//
		boolean b = subject.parseLine(line);
		Schema  s = subject.getSchema();
		Item<?> item = s.getItem("verbose");

		// Assert
		//
		assertTrue(b);
		assertNotNull(s);
		assertNotNull(item);
		assertEquals(OptionType.BOOLEAN, item.getType());
	}
}
