package com.xivvic.args.schema;

import static com.xivvic.args.error.ErrorCode.INVALID_ARGUMENT_FORMAT;
import static com.xivvic.args.error.ErrorCode.NO_SCHEMA;
import static com.xivvic.args.error.ErrorStrategy.FAIL_FAST;
import static com.xivvic.args.error.ErrorStrategy.WARN_AND_IGNORE;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.item.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserShortForm
extends SchemaParser
{
	public ParserShortForm()
	{
		super(FAIL_FAST);
	}

	public ParserShortForm(ErrorStrategy es)
	{
		super(es);
	}

	@Override
	public Map<String, Map<String, String>> parse(String spec) throws SchemaException
	{
		if (spec == null || spec.trim().length() == 0)
		{
			if (es == WARN_AND_IGNORE)
			{
				log.warn("Empty specification provided. Ignorning and returning empty result.");
				return Collections.emptyMap();
			}

			SchemaException ex = new SchemaException(NO_SCHEMA);
			handleException(ex);
		}

		Map<String, Map<String, String>> defs = createDefinitions(spec.trim());

		return defs;
	}

	private Map<String, Map<String, String>> createDefinitions(String spec) throws SchemaException
	{
		if (! isSimpleDefinition(spec))
		{
			String msg = "Supplied schema format is not a simple format definition";
			throw new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, "n/a", msg);
		}

		char firstChar = spec.charAt(0);
		declaredStrategy = ErrorStrategy.strategyForCode(firstChar);

		if (declaredStrategy != null)
		{
			spec = spec.substring(1);
		}

		Map<String, Map<String, String>> rv = new ConcurrentHashMap<>();
		String[] defs = spec.split(",");
		for (String s : defs)
		{
			String t = s.trim();
			if (t.length() == 0)
			{
				String msg = String.format("Schema definition [%s] contains an empty element.", spec);
				SchemaException ex = new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, "n/a", msg);
				handleException(ex);
				continue;
			}

			String opt = t.substring(0, 1);
			String modifier = t.substring(1);
			Map<String, String> def = processSimpleItem(opt, modifier);
			rv.put(opt, def);
		}

		return rv;
	}

	private boolean isSimpleDefinition(String spec)
	{
		return ! spec.contains(ParserLongForm.EXTENDED_FORMAT_SEPARATOR);
	}

	private Map<String, String> processSimpleItem(String opt, String modifier) throws SchemaException
	{
		Map<String, String> rv = new ConcurrentHashMap<>();


		if (modifier == null)
		{
			return rv;
		}

		rv.put(Item.NAME, opt);

		// TODO: Consider additional types of arguments for simple processing.
		//
		switch (modifier)
		{
		case    "": rv.put(Item.TYPE,      OptionType.BOOLEAN.name()); break;
		case   "*": rv.put(Item.TYPE,       OptionType.STRING.name()); break;
		case   "#": rv.put(Item.TYPE,      OptionType.INTEGER.name()); break;
		case  "##": rv.put(Item.TYPE,       OptionType.DOUBLE.name()); break;
		case "[*]": rv.put(Item.TYPE,  OptionType.STRING_LIST.name()); break;
		default:
			SchemaException ex = new SchemaException(INVALID_ARGUMENT_FORMAT, opt, null);
			handleException(ex);
		}

		return rv;
	}

}
