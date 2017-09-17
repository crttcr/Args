package com.xivvic.args.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.xivvic.args.Args;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.item.Item;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class TableData
{
	private final String[] headers;
	private final List<String[]> data = new ArrayList<>();

	public TableData(String[] headers)
	{
		Objects.requireNonNull(headers);
		this.headers = headers.clone();
	}

	public void row(String[] values)
	{
		if (values == null)
		{
			return;
		}

		int n = Math.min(values.length, headers.length);

		String[] current = new String[n];

		for (int i = 0; i < n; i++)
		{
			current[i] = values[i];
		}

		data.add(current);
	}

	public static TableData from(Args args, Schema schema) {
		String[]         h = { "Name", "Type", "Set?", "Required", "Value", "Description"};
		List<String> names = schema.getOptions();
		TableData       rv = new TableData(h);

		names.forEach(n -> {
			String[]   sa = new String[h.length];
			Item<?>  item = schema.getItem(n);
			Boolean     r = item.getRequired();

			sa[0] = n;
			sa[1] = item.getType().name();
			sa[2] = args.optionHasValue(n) ? "Yes" : "No";
			sa[3] = r == null ? "" : r.toString();
			sa[4] = args.getValue(n).toString();
			sa[5] = item.getDescription();

			rv.row(sa);
		});

		return rv;
	}
}
