package com.xivvic.args.schema;

import static com.xivvic.args.error.ErrorStrategy.FAIL_FAST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class SchemaParser
{
	public static final String EXTENDED_FORMAT_SEPARATOR = "\n";

	protected final ErrorStrategy         es;
	protected ErrorStrategy declaredStrategy;
	private List<SchemaException>   errors;

	protected SchemaParser()
	{
		es = FAIL_FAST;
	}

	public SchemaParser(ErrorStrategy es)
	{
		this.es = es;
	}

	public abstract Map<String, Map<String, String>> parse(String defs) throws SchemaException;

	public final ErrorStrategy getStrategyOverride()
	{
		return declaredStrategy;
	}

	protected void handleException(SchemaException e) throws SchemaException
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

}
