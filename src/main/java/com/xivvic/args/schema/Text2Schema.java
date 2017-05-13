package com.xivvic.args.schema;

import static com.xivvic.args.error.ErrorCode.NO_SCHEMA;
import static com.xivvic.args.error.ErrorStrategy.FAIL_FAST;
import static com.xivvic.args.error.ErrorStrategy.WARN_AND_IGNORE;

import java.util.Collections;
import java.util.Map;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Text2Schema
{
	public final static String EXTENDED_FORMAT_SEPARATOR = "\n";

	private final ErrorStrategy es;
	public Text2Schema()
	{
		es = FAIL_FAST;
	}

	public Text2Schema(ErrorStrategy es)
	{
		this.es = es;
	}

	public static boolean isLongFormDefinition(String defs)
	{
		if (defs == null)
		{
			return false;
		}

		return defs.contains(EXTENDED_FORMAT_SEPARATOR);
	}

	public Schema createSchema(String defs) throws SchemaException
	{
		if (defs == null)
		{
			throw new SchemaException(NO_SCHEMA);
		}

		boolean lfd = isLongFormDefinition(defs);

		SchemaParser parser = lfd ? new ParserLongForm() : new ParserShortForm();

		Map<String, Map<String, String>> map = Collections.emptyMap();

		try
		{
			map = parser.parse(defs);
		}
		catch (SchemaException e)
		{
			ErrorStrategy override = parser.getStrategyOverride();
			ErrorStrategy strat = override == null ? es : override;

			if (strat == WARN_AND_IGNORE)
			{
				log.warn("Exception encountered processing schema definition: [{}]", e.getLocalizedMessage());
			}
			else
			{
				throw e;
			}
		}

		SchemaBuilder builder = new SchemaBuilder();
		for (Map<String, String> def : map.values())
		{
			builder.def(def);
		}

		return builder.build();
	}
}
