package com.xivvic.args.schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.xivvic.args.error.SchemaException;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.item.Item;
import com.xivvic.args.schema.item.ItemPredicate;
import com.xivvic.args.schema.item.ItemPredicateHasEnvironmentVariable;
import com.xivvic.args.util.trie.Trie;

public class Schema
{
	private Trie<Item<?>> trie = new Trie<>();

	public Schema(Map<String, Item<?>> defs )
	{
		Objects.requireNonNull(defs);

		Set<Entry<String, Item<?>>> entries = defs.entrySet();

		for (Entry<String, Item<?>> e : entries)
		{
			String k = e.getKey();
			Item<?> v = e.getValue();
			trie.put(k, v);
		}
	}

	public boolean allRequiredOptionsHaveValuesOrDefaults() {

		Set<String> keys = trie.keySet();

		for (String key : keys)
		{
			Item<?> item = trie.get(key);
			Boolean required = item.getRequired();
			if (required == null || required == Boolean.FALSE)
			{
				continue;
			}

			OptEvaluator<?> eval = item.getEval();

			if (eval.getValue() != null)
			{
				continue;
			}

			if (eval.getDefault() == null)
			{
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> Item<T> getItem(String option)
	{
		Item<T> item = (Item<T>) trie.get(option);

		return item;
	}

	public List<String> getOptions()
	{
		List<String> rv = new ArrayList<>();
		Set<String> keys = trie.keySet();

		keys.forEach(rv::add);

		return rv;
	}

	public List<Item<?>> itemsWithEnvironmentVariables()
	{
		ItemPredicate<?> e = new ItemPredicateHasEnvironmentVariable<>();

		List<Item<?>> rv = find(e);

		return rv;
	}

	@Override
	public String toString()
	{
		Set<String> keys = trie.keySet();
		return "Schema [opts=" + keys + "]";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Item<?>> find(ItemPredicate<?> predicate) {

		List<Item<?>> rv = new ArrayList<>();
		Set<Item<?>> items = trie.valueSet();;

		for (Item item:  items)
		{
			if (predicate == null || predicate.test(item))
			{
				rv.add(item);
			}
		}

		return rv;
	}

	public List<Item<?>> itemsWithPrefix(String prefix)
	{
		Objects.requireNonNull(prefix);

		List<Item<?>> rv = new ArrayList<>();
		Iterator<String> it = trie.keysWithPrefix(prefix).iterator();

		while (it.hasNext())
		{
			String k = it.next();
			Item<?> item = trie.get(k);
			rv.add(item);
		}

		return rv;
	}

	/**
	 * Returns a schema similar to the existing schema but including the item specified
	 * by the parameter.
	 *
	 * If the target is null, then this method returns a reference to its object.
	 * If the target already contains an item with the same option name, it is replaced.
	 *
	 * @param the item that should be included in the returned schema
	 * @return a schema that contains the specified option
	 */
	public Schema with(Item<?> addition)
	{
		if (addition == null)
		{
			return this;
		}

		SchemaBuilder builder = new SchemaBuilder();

		List<String> options = getOptions();

		for (String name : options)
		{
			Item<?> found = getItem(name);
			//			Item<?> clone = found.duplicate();
			// FIXME:
			builder.item(found);
		}

		builder.item(addition);
		try
		{
			Schema rv = builder.build();
			return rv;
		}
		catch (SchemaException e)
		{
			// Should not happen.
			//
			return null;
		}
	}

	/**
	 * Returns a schema similar to the existing schema but without the option identified
	 * by the parameter.
	 *
	 * If the target is null or is not an option in this schema, then this method
	 * returns a reference to its object.
	 *
	 * @param target the name of the option that should not be in the returned schema
	 * @return a schema that does not contain the specified option
	 */
	public Schema without(String target)
	{
		if (target == null)
		{
			return this;
		}

		Item<?> found = getItem(target);
		if (found == null)
		{
			return this;
		}

		SchemaBuilder builder = new SchemaBuilder();

		List<String> options = getOptions();

		for (String name : options)
		{
			if (! name.equals(target))
			{
				Item<?> item = getItem(name);
				builder.item(item);
			}
		}

		try
		{
			Schema rv = builder.build();
			return rv;
		}
		catch (SchemaException e)
		{
			// Should never happen.
			//
			return null;
		}
	}

	public int optionCount()
	{
		return trie.size();
	}

}