package com.xivvic.args;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.schema.OptionType;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.Text2Schema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil
{
	public static ListIterator<String> getListIterator(String... strings)
	{
		List<String> list = Arrays.asList(strings);
		ListIterator<String> it = list.listIterator();
		return it;
	}

	public static String readFromTestResourceFile(String file)
	{
		Path path = Paths.get("src/test/resources/defs", file);

		System.out.println(path.toAbsolutePath().toString());

		if (!Files.exists(path))
		{
			fail("Missing test resource file: " + path);
		}

		byte[] bytes = {};
		try
		{
			bytes = Files.readAllBytes(path);
		}
		catch (IOException e)
		{
			fail("Error reading test resource file: " + path + ". Error: " + e.getLocalizedMessage());
		}
		String content = new String(bytes);

		return content;
	}

	public static Schema getTestSchema(OptionType t)
	{
		Objects.requireNonNull(t);

		String s = null;

		switch (t)
		{
		case BOOLEAN:
			s = readFromTestResourceFile("boolean.long.txt");
			break;
		case STRING:
			s = readFromTestResourceFile("string.long.txt");
			break;
		case STRING_LIST:
			s = readFromTestResourceFile("string.list.long.txt");
			break;
		case INTEGER:
			s = readFromTestResourceFile("integer.long.txt");
			break;
		case DOUBLE:
			s = readFromTestResourceFile("double.long.txt");
			break;
		case DATE:
			s = readFromTestResourceFile("date.long.txt");
			break;
		case TIME:
			s = readFromTestResourceFile("time.long.txt");
			break;
		case PATH:
			s = readFromTestResourceFile("path.long.txt");
			break;
		case FILE:
			s = readFromTestResourceFile("file.long.txt");
			break;

		default:
		}

		if (s == null)
		{
			return null;
		}

		try
		{
			Text2Schema t2s = new Text2Schema();
			Schema schema = t2s.createSchema(s);
			return schema;
		}
		catch (ArgsException e)
		{
			log.warn("Exception creating test schema for {}", t);
			return null;
		}
	}

}
