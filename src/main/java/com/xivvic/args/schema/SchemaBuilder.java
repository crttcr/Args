package com.xivvic.args.schema;

import static com.xivvic.args.error.ErrorCode.EMPTY_SCHEMA;
import static com.xivvic.args.error.ErrorCode.INVALID_SCHEMA_ELEMENT;
import static com.xivvic.args.error.ErrorCode.SCHEMA_MISSING_OPTION_NAME;
import static com.xivvic.args.error.ErrorCode.SCHEMA_MISSING_OPTION_TYPE;
import static com.xivvic.args.error.ErrorStrategy.FAIL_FAST;
import static com.xivvic.args.error.ErrorStrategy.WARN_AND_IGNORE;
import static com.xivvic.args.schema.item.Item.NAME;
import static com.xivvic.args.schema.item.Item.TYPE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.xivvic.args.error.CompositeSchemaException;
import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.item.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchemaBuilder
{
	private Map<String, Item<?>>				  opts	  = new ConcurrentHashMap<>();
	private ErrorStrategy						  es		  = FAIL_FAST;
	private Map<String, SchemaException>	  errors   = new HashMap<>();

	public SchemaBuilder()
	{
		this(FAIL_FAST);
	}

	public SchemaBuilder(ErrorStrategy es)
	{
		this.es = es;
	}

	public SchemaBuilder def(Map<String, String> def) throws SchemaException
	{
		SchemaException ex = buildDefinition(def);

		if (ex == null)
		{
			return this;
		}

		// At this point, the construction of the schema caused an error, so
		// handle it according to the error strategy.
		//
		if (es == FAIL_FAST)
		{
			throw ex;
		}

		String name = def == null || def.get(NAME) == null ? "UNDEFINED" : def.get(NAME);
		errors.put(name, ex);

		return this;
	}

	public void item(Item<?> item)
	{
		Objects.requireNonNull(item);

		String name = item.getName();
		opts.put(name, item);
	}

	private SchemaException buildDefinition(Map<String, String> def)
	{
		if (def == null)
		{
			return new SchemaException(EMPTY_SCHEMA);
		}

		String name = def.get(NAME);
		String type = def.get(TYPE);

		if (name == null)
		{
			return new SchemaException(SCHEMA_MISSING_OPTION_NAME);
		}

		if (type == null)
		{
			return new SchemaException(SCHEMA_MISSING_OPTION_TYPE, name);
		}


		OptionType optType = OptionType.valueOf(type.toUpperCase());
		if (optType == null)
		{
			String msg = String.format("Unable to convert [%s] to an option type", optType);
			return new SchemaException(INVALID_SCHEMA_ELEMENT, name, msg);
		}

		try
		{
			Item<?> item = Item.builder(def).build();
			opts.put(name, item);
		}
		catch (SchemaException e)
		{
			return e;
		}

		return null;
	}

	public Schema build() throws SchemaException
	{
		if (opts.isEmpty() && es != WARN_AND_IGNORE)
		{
			throw new SchemaException(ErrorCode.EMPTY_SCHEMA);
		}

		if (errors.isEmpty())
		{
			return new Schema(opts);
		}

		if (es == ErrorStrategy.WARN_AND_IGNORE)
		{
			for (Entry<String, SchemaException> entry : errors.entrySet())
			{
				String name = entry.getKey();
				String msg = String.format("Ignoring option [%s] becuase of exception [%s]", name, entry.getValue());
				log.warn(msg);
				opts.remove(name);
			}

			return new Schema(opts);
		}

		List<SchemaException> list = errors.entrySet()
		.stream()
		.sorted(Map.Entry.comparingByKey())
		.map(e -> { return e.getValue(); })
		.collect(Collectors.toList());

		SchemaException ex = new CompositeSchemaException(list);
		throw ex;
	}
}
