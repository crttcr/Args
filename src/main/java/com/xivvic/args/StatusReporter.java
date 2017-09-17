package com.xivvic.args;

import java.util.List;
import java.util.Objects;

import com.xivvic.args.schema.OptionType;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.item.Item;
import com.xivvic.args.schema.item.ItemPredicate;
import com.xivvic.args.schema.item.ItemPredicateRequired;

public class StatusReporter
{
	private final Schema schema;
	private final Args args;

	public StatusReporter(Schema schema, Args args)
	{
		this.schema = Objects.requireNonNull(schema);
		this.args = Objects.requireNonNull(args);
	}

	public String getCommandLineStatusReport()
	{
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("rawtypes")
		ItemPredicate<?> required = new ItemPredicateRequired();
		List<Item<?>> required_options = schema.find(required);

		if (! required_options.isEmpty())
		{
			sb.append("Required Options:\n");
			for (Item<?> item : required_options)
			{
				String item_status = getStatusForItem(item, args);
				sb.append(item_status).append("\n");
			}
			sb.append("\n");
		}
		else
		{
			sb.append("No required options.\n");
		}

		ItemPredicate<?> optional = required.negate();
		List<Item<?>> not_required = schema.find(optional);
		if (! not_required.isEmpty())
		{
			sb.append("Optional Options:\n");
			for (Item<?> item : not_required)
			{
				String item_status = getStatusForItem(item, args);
				sb.append(item_status).append("\n");
			}
		}
		else
		{
			sb.append("No optional options.\n");
		}

		return sb.toString();
	}

	private String getStatusForItem(Item<?> item, Args args)
	{
		StringBuilder sb = new StringBuilder();
		String name = item.getName();
		OptionType type = item.getType();
		boolean hasValue = args.optionHasValue(name);

		sb.append("Option   : ").append(name).append("\n");
		sb.append("Type     : ").append(type).append("\n");
		sb.append("IsSet    : ").append(hasValue ? "YES" : "NO").append("\n");

		return sb.toString();
	}
}
