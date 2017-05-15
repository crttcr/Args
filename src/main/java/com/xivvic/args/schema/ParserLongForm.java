package com.xivvic.args.schema;

import static com.xivvic.args.error.ErrorCode.NO_SCHEMA;
import static com.xivvic.args.error.ErrorStrategy.FAIL_FAST;
import static com.xivvic.args.error.ErrorStrategy.WARN_AND_IGNORE;

import java.util.Collections;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.antlr.ArgsSpecLexer;
import com.xivvic.args.schema.antlr.ArgsSpecParser;

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

		String trim = spec.trim();
		Map<String, Map<String, String>> defs = createDefinitions(trim);

		return defs;
	}


	private Map<String, Map<String, String>> createDefinitions(String spec)
	{
		CharStream input = CharStreams.fromString(spec);
		ArgsSpecLexer lexer = new ArgsSpecLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ArgsSpecParser parser = new ArgsSpecParser(tokens);
		LongFormListener listener = new LongFormListener(es);
		parser.addParseListener(listener);
		parser.spec();

		//		System.out.println("Context post spec() call: " + context);
		return listener.result();
	}
}
