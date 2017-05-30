package com.xivvic.args.schema.item;

import java.util.function.Predicate;

public interface ItemPredicate<T>
extends Predicate<Item<T>>
{
	@Override
	default ItemPredicate<T> negate()
	{
		return (t) -> ! test(t);
	}
}
