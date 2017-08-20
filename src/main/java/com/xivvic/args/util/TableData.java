package com.xivvic.args.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

}
