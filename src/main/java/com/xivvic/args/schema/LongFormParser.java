package com.xivvic.args.schema;


import static com.xivvic.args.error.ErrorCode.INVALID_SCHEMA_ELEMENT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.schema.item.Item;

import lombok.extern.slf4j.Slf4j;

/**
 * This class implements a simple parsing strategy of building up the definition of a
 * Schema item each time parseLine() is called with a single item of data. The line is
 * added to the current option being defined. Each time a new option property is presented
 * by passing a line containing a property name, such as:
 *
 * [port]
 *
 * the current property is completed and a new one starts collection definition lines.
 *
 * When all definition lines have been provided, getSchema() will return the schema that
 * has been parsed.
 *
 * @author reid.dev
 */

@Slf4j
public class LongFormParser
{
	public static final String			 DEFAULT_NAME					= "[Name not defined]";
	public static final String			 EXTENDED_FORMAT_SEPARATOR	= "=";

	private Map<String, Item.Builder<?>>		 builders			= new ConcurrentHashMap<>();
	private Map<String, Map<String, String>> itemdata				= new ConcurrentHashMap<>();

	private Item.Builder<?> current;

	private String							 def;
	private final ErrorStrategy       es;
	private List<ArgsException>       errors;

	public LongFormParser()
	{
		this(ErrorStrategy.FAIL_FAST);
	}

	public LongFormParser(ErrorStrategy es)
	{
		this.es = es;
	}

	// Return true if the line adds to the definition of an option,
	// false if it does not (e.g. comment, empty line), and throw an
	// exception if it is malformed.
	//
	public boolean parseLine(String line) throws ArgsException
	{
		if (line == null)
		{
			return false;
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
	}



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


	private void buildItemsFromExtendedDefinition() throws ArgsException
	{
		String[] lines = def.split("\n");
		for (String line : lines)
		{
			line = line.trim();
			if (line.length() == 0 || line.startsWith("#"))
			{
				continue;
			}

			if (!line.contains(EXTENDED_FORMAT_SEPARATOR))
			{
				log.warn("Schema definition line {} missing separator {}.", line, EXTENDED_FORMAT_SEPARATOR);
				continue;
			}
			LongFormData form = LongFormData.processDefinitionLine(line);
			String key = form.getKey();

			Map<String, String> data = itemdata.get(key);
			if (data == null)
			{
				data = new ConcurrentHashMap<>();
				itemdata.put(key, data);
			}

			String field = form.getField();
			String value = form.getValue();
			data.put(field, value);
		}

		for (Entry<String, Map<String, String>> e : itemdata.entrySet())
		{
			String k = e.getKey();
			Map<String, String> v = e.getValue();
			//			applyBuilder(k, v);
		}
	}


	//	private void applyBuilder(String key, Map<String, String> data) throws ArgsException
	//	{
	//		String typename = data.get(Item.TYPE);
	//		if (typename == null)
	//		{
	//			String msg = "Cannot construct an option without specifying the type.";
	//			throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
	//		}
	//
	//		OptionType type = OptionType.valueOf(typename.toUpperCase());
	//		if (type == null)
	//		{
	//			String msg = String.format("The type name [%s] is not a valid type", typename);
	//			throw new ArgsException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
	//		}
	//
	//		Item.Builder<?> builder = Item.builder(data);
	//		Item<?> item = builder.build();
	//		opts.put(key, item);
	//	}

	private void handleException(ArgsException e) throws ArgsException
	{
		switch (es)
		{
		case FAIL_FAST:
			throw e;
		case FAIL_SLOW:
			errors = errors == null ? new ArrayList<>() : errors;
			errors.add(e);
		case WARN_AND_IGNORE:
			String msg = String.format("Ignoring schema definition error:  %s", e.getMessage());
			log.warn(msg);
			break;
		default:
			String err = String.format("Program error: ErrorStrategy (%s) not supported. Continuing.", es);
			log.warn(err);
			break;
		}
	}

	public Schema getSchema() throws ArgsException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
