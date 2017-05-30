package com.xivvic.args.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableDimensionCalculator
{
	private final static int COL_MIN = 12;

	private final String[] headers;
	private final List<String[]> data;

	public TableDimensionCalculator(String[] headers, List<String[]> data)
	{
		this.headers = Objects.requireNonNull(headers);
		this.data = Objects.requireNonNull(data);
	}

	public int[] fitDimensions(int total)
	{
		int cols = headers.length;
		if (total < 8 * cols + 2)
		{
			String msg = String.format("Total specified width [%d] not sufficient to display [%d] columns", total, cols);
			throw new IllegalArgumentException(msg);
		}

		int max_len[] = greatestLengths();
		int sum =  Arrays.stream(max_len).sum();

		if (sum <= total - 2)
		{
			return Arrays.stream(max_len)
			.map(l -> {return l < COL_MIN ? COL_MIN : l; })
			.toArray();
		}

		final float factor = sum / (float) total;

		int[] rv = Arrays.stream(max_len)
		.map(l -> {return (int) (l * factor); })
		.map(l -> {return l < COL_MIN ? COL_MIN  : l; })
		.toArray();

		return rv;
	}

	private int[] greatestLengths()
	{
		int cols = headers.length;

		int[] rv = new int[cols];

		// Max of headers
		//
		for (int col = 0; col < cols; col++)
		{
			rv[col] = maxLength(rv[col], headers[col]);
		}

		// Max of data
		//
		for (int row = 0; row < data.size(); row++)
		{
			String[] values = data.get(row);

			for (int col = 0; col < values.length && col < cols; col++)
			{
				rv[col] = maxLength(rv[col], values[col]);
			}
		}

		return rv;
	}

	private int maxLength(int existing, String string)
	{
		int rv = existing;

		if (string == null)
		{
			return rv;
		}

		int strlen = string.length();

		return Math.max(rv, strlen);
	}
}
