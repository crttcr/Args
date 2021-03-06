package com.xivvic.args.schema.item;

import java.util.Objects;

public class ItemPredicateRequired<T>
implements ItemPredicate<T>
{

	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);
		Boolean r = item.getRequired();

		if (r == null)
		{
			return false;
		}

		return r;
	}


}
