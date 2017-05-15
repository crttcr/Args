package com.xivvic.args.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.antlr.ArgsSpecBaseListener;
import com.xivvic.args.schema.antlr.ArgsSpecParser;
import com.xivvic.args.schema.item.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongFormListener
extends ArgsSpecBaseListener
{
	private Map<String, Map<String, String>> result = new HashMap<>();
	private Map<String, String> current = null;
	private String name = null;
	private List<SchemaException> errors = new LinkedList<>();
	private final ErrorStrategy es;

	public LongFormListener(ErrorStrategy es)
	{
		this.es = es;
	}

	@Override
	public void enterItem(ArgsSpecParser.ItemContext ctx)
	{
		System.out.println("Entered item: " + ctx.getText());
		current = new HashMap<String, String>();
	}

	@Override
	public void exitItem(ArgsSpecParser.ItemContext ctx)
	{
		String type = current.get(Item.TYPE);
		if (type == null)
		{
			current.put(Item.TYPE, OptionType.BOOLEAN.name());
		}

		current.put(Item.NAME, name);
		result.put(name, current);

		current = null;
		name = null;

		System.out.println("Exited item: " + ctx.getText());
	}

	@Override
	public void exitItem_header(ArgsSpecParser.Item_headerContext ctx)
	{
		name = ctx.item_name().getText();
	}

	@Override
	public void exitKey_value(ArgsSpecParser.Key_valueContext ctx)
	{
		String key = ctx.key().getText();
		String value = ctx.value() == null ? null : ctx.value().getText();

		switch (key)
		{
		case Item.DEFAULT:
		case Item.DESCRIPTION:
		case Item.ENV_VAR:
		case Item.REQUIRED:
		case Item.TYPE:
			current.put(key, value);
			break;
		default:
			String msg = "Unexpected configuration value [" + key + "] in specification";
			SchemaException ex = new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			handleException(ex);
			break;
		}
	}

	public Map<String, Map<String, String>> result()
	{
		return Collections.unmodifiableMap(result);
	}

	private void handleException(SchemaException e)
	{
		switch (es)
		{
		case FAIL_FAST:
			throw new RuntimeException(e);
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
