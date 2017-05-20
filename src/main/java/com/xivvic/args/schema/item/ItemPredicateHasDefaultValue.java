package com.xivvic.args.schema.item;

import java.util.Objects;

import com.xivvic.args.marshall.OptEvaluator;

public class ItemPredicateHasDefaultValue<T>
implements ItemPredicate<T>
{

	@Override
	public boolean test(Item<T> item)
	{
		Objects.requireNonNull(item);
		OptEvaluator<?> eval = item.getEval();

		return eval.getDefault() != null;
	}
}
