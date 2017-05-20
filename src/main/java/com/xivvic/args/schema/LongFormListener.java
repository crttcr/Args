package com.xivvic.args.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.antlr.ArgsSpecBaseListener;
import com.xivvic.args.schema.antlr.ArgsSpecParser;
import com.xivvic.args.schema.item.Item;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongFormListener
extends ArgsSpecBaseListener
{
	private Map<String, Map<String, String>> result = new HashMap<>();

	private Map<String, String> current = null;
	private String itemName = null;
	private List<SchemaException> errors = new LinkedList<>();
	private final ErrorStrategy es;

	@Setter
	private boolean trace = false;

	public LongFormListener(ErrorStrategy es)
	{
		this.es = es;
	}

	@Override
	public void enterItem(ArgsSpecParser.ItemContext ctx)
	{
		if (trace)
		{
			log.info("Enter item: " + ctx.getText());
		}
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

		current.put(Item.NAME, itemName);
		result.put(itemName, current);

		current = null;
		itemName = null;

		if (trace)
		{
			log.info("Exited item: " + ctx.getText());
		}
	}

	@Override
	public void exitItem_header(ArgsSpecParser.Item_headerContext ctx)
	{
		itemName = ctx.name().getText();
		if (trace)
		{
			log.info("Exit item header: " + ctx.getText());
		}
	}

	@Override
	public void exitName_value(ArgsSpecParser.Name_valueContext ctx)
	{
		ParseTree keyTree = ctx.getChild(0);
		ParseTree valueTree = ctx.getChild(2);
		String key = keyTree.getText();
		String value = valueTree == null ? null : ctx.value().getText();

		if (value != null && value.startsWith("\"") && value.endsWith("\""))
		{
			value = value.substring(1, value.length() - 1);
		}

		current.put(key, value);
		if (trace)
		{
			log.info("Exit NameValue: " + ctx.getText());
		}

	}

	@Override
	public void exitName(ArgsSpecParser.NameContext ctx)
	{
		if (trace)
		{
			log.info("Exit name: " + ctx.getText());
		}
	}

	@Override
	public void enterName_value(ArgsSpecParser.Name_valueContext ctx)
	{
		if (trace)
		{
			log.info("Enter name value: " + ctx.getText());
		}
	}


	public Map<String, Map<String, String>> result()
	{
		return Collections.unmodifiableMap(result);
	}

	//	private void handleException(SchemaException e)
	//	{
	//		switch (es)
	//		{
	//		case FAIL_FAST:
	//			throw new RuntimeException(e);
	//		case FAIL_SLOW:
	//			errors = errors == null ? new ArrayList<>() : errors;
	//			errors.add(e);
	//		case WARN_AND_IGNORE:
	//			String msg = String.format("Ignoring schema definition error:  %s", e.getMessage());
	//			log.warn(msg);
	//			break;
	//		default:
	//			String err = String.format("Program error: ErrorStrategy (%s) not supported. Continuing.", es);
	//			log.warn(err);
	//			break;
	//		}
	//	}
}
