package com.xivvic.args.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class PropertyFileParserTest
{
	PropertyFileParser subject;

	@Test(expected = NullPointerException.class)
	public void testNullListOfLinesThrowsException()
	{
		new PropertyFileParser(null);
	}

	@Test
	public void testEmptyLinesReturnEmptyList()
	{
		// Arrange
		//
		List<String> empty = Collections.emptyList();
		subject = new PropertyFileParser(empty);

		// Act
		//
		List<Map<String, String>> out = subject.parse();

		// Assert
		assertNotNull(out);
		assertTrue(out.isEmpty());
	}

}
