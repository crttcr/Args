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
public class ParserLongForm
extends SchemaParser
{

	public ParserLongForm()
	{
		super(FAIL_FAST);
	}

	public ParserLongForm(ErrorStrategy es)
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


	private Map<String, Map<String, String>> createDefinitions(String trim)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private String getPropertyListener()
	{
		return "FIXME";

	}
}
