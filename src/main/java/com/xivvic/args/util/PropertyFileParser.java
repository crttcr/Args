package com.xivvic.args.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.xivvic.args.error.ArgsException;

import lombok.extern.slf4j.Slf4j;

/**
 * This class implements a simple parsing strategy for property files, organized like a windows INI.
 *
 *
 *
 * @author reid.dev
 */

@Slf4j
public class PropertyFileParser
{
	public final static String DEFAULT_CONTEXT = "Default";
	public final static String COMMENT = "#";
	public final static String EQUALS = "=";
	public final static String COLON = ":";
	public final static String LEFT_CONTEXT = "[";
	public final static String RIGHT_CONTEXT = "]";
	public final static String LEFT_SUBSTITUTION = "${";
	public final static String RIGHT_SUBSTITUTION = "}";



	public static final String			DEFAULT_NAME					= "__GLOBAL__";
	public static final String[]		SEPARATORS	= {"=", ":"};

	private List<Map<String, String>> content = new ArrayList<>();
	private Map<String, String>       current = null;

	private final List<String> lines;

	private String							 def;
	private List<ArgsException>       errors;

	public PropertyFileParser(List<String> lines)
	{
		this.lines = Objects.requireNonNull(Collections.unmodifiableList(lines));
	}


	public List<String> errors()
	{
		return null;
	}

	public List<Map<String, String>> parse()
	{
		if (lines == null)
		{
			return Collections.emptyList();
		}
		/*
		for (String line : lines) {

		}

		final String def = line.trim();

		if (def.length() == 0)
		{
			return false;
		}

		if (def.startsWith("#"))
		{
			return false;
		}

		if (def.startsWith("["))
		{
			if (! def.endsWith("]"))
			{
				throw new ArgsException(INVALID_SCHEMA_ELEMENT, def);
			}

			startNewPropertyDefinition(def);
			return true;
		}

		if (def.contains(EXTENDED_FORMAT_SEPARATOR))
		{
			addToCurrentDefinition(def);
			return true;
		}

		if (es == ErrorStrategy.FAIL_FAST)
		{
			throw new ArgsException(INVALID_SCHEMA_ELEMENT, def);
		}

		return false;
		 */

		return Collections.unmodifiableList(content);
	}

	/*

	private void startNewPropertyDefinition(String line)
	{
		String name = line.substring(1, line.length() - 1).trim();

		current = Item.builder();
		builders.put(name, current);
		System.out.println(name);
	}

	private void addToCurrentDefinition(String line) throws ArgsException
	{
		LongFormData form = LongFormData.processDefinitionLine(line);
		String key = form.getKey();

		Map<String, String> data = itemdata.get(key);
		if (data == null)
		{
			data = new ConcurrentHashMap<>();
			itemdata.put(key, data);
		}
	}
	 */

	private boolean isSubstitution(String value) {
		return value.startsWith(LEFT_SUBSTITUTION) && value.endsWith(RIGHT_SUBSTITUTION);
	}

	private String getSubstitution(String value) {
		return value.substring(2, value.length() - 1);
	}
}
