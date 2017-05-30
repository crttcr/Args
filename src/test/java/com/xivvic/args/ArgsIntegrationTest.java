package com.xivvic.args;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.xivvic.args.schema.OptionType;
import com.xivvic.args.schema.Schema;

/*
 * 	// Basic types
	//
	BOOLEAN,
	STRING,
	STRING_LIST,
	INTEGER,
	DOUBLE,

	// Domain types
	//
	DATE,
	TIME,
	DIRECTORY,
	FILE,

	;

 */
@Category(IntegrationTest.class)
public class ArgsIntegrationTest
{
	@Test
	public void testBoolean() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.BOOLEAN);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "true"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		Boolean b = arg.getValue(name);

		// Assert
		//
		assertNotNull(b);
		assertTrue(b);
	}

	@Test
	public void testString() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.STRING);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "dixie"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		String s = arg.getValue(name);

		// Assert
		//
		assertNotNull(s);
		assertEquals("dixie", s);
	}

	@Test
	public void testStringList() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.STRING_LIST);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "Deputy", "--" + name, "Dawg"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		List<String> list = arg.getValue(name);

		// Assert
		//
		assertNotNull(list);
		assertEquals(2, list.size());
		assertTrue(list.contains("Deputy"));
		assertTrue(list.contains("Dawg"));
	}

	@Test
	public void testIntegerArgument() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.INTEGER);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "10"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		Integer i = arg.getValue(name);

		// Assert
		//
		assertNotNull(i);
		assertEquals(10, i.intValue());
	}

	@Test
	public void testDoubleArgument() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.DOUBLE);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "12500.85"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		Double d = arg.getValue(name);

		// Assert
		//
		assertNotNull(d);
		assertEquals(12500.85, d, .001);
	}

	@Test
	public void testDate() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.DATE);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "2017-01-01"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		LocalDate d = arg.getValue(name);

		// Assert
		//
		assertNotNull(d);
		assertEquals(LocalDate.of(2017, 01, 01), d);
	}

	@Test
	public void testTime() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.TIME);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "10:22:59"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		LocalTime t = arg.getValue(name);

		// Assert
		//
		assertNotNull(t);
		assertEquals(LocalTime.of(10, 22, 59), t);
	}

	@Test
	public void testPath() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.PATH);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "/User/home"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		Path p = arg.getValue(name);

		// Assert
		//
		assertNotNull(p);
		assertEquals("/User/home", p.toAbsolutePath().toString());
	}

	@Test
	public void testFile() throws Exception
	{
		// Arrange
		//
		Schema schema = TestUtil.getTestSchema(OptionType.FILE);
		String   name = schema.getOptions().get(0);
		String[] args = {"--" + name, "names.txt"};

		// Act
		//
		Args arg = Args.processOrThrowException(schema, args);
		File f = arg.getValue(name);

		// Assert
		//
		assertNotNull(f);
		assertEquals("names.txt", f.getName());
	}

}
