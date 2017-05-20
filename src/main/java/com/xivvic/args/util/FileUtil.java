package com.xivvic.args.util;

import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtil
{
	public static String readFromResourceFile(String dir, String file)
	{
		Path path = Paths.get(dir, file);


		if (! Files.exists(path))
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

	public static String inputStreamToString(InputStream stream)
	{
		Objects.requireNonNull(stream);

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int length;
		try
		{
			while ((length = stream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
		}
		catch (IOException e)
		{
			return null;
		}

		try
		{
			return result.toString(StandardCharsets.UTF_8.name());
		}
		catch (UnsupportedEncodingException cannotHappen)
		{
			return null;
		}
	}

}
