package com.xivvic.args.display;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class TableDimensionCalculator
{
	/**
	 * This adjusts a TOTAL width to an INTERIOR width for an edged line.
	 */

	private final static int COL_MIN = 12;
	private static final IntUnaryOperator minimumWidthForColumns = numberOfColumns -> COL_MIN * numberOfColumns + 2 * numberOfColumns + 1;

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
		int  min = minimumWidthForColumns.applyAsInt(cols);
		if (total < min)
		{
			String msg = String.format("Total specified width [%d] not sufficient to display [%d] columns", total, cols);
			throw new IllegalArgumentException(msg);
		}

		int    max_len[] = greatestLengths();
		int          sum =  Arrays.stream(max_len).sum();
		int[]         rv = null;

      if (sum > min)
		{
      		final float factor = sum / (float) total;
      		IntStream is = Arrays.stream(max_len);
     		rv = is.map(l -> { return (int) (l * factor); })
      			.map(l -> {return l < COL_MIN ? COL_MIN : l; })
      			.toArray();
		}
      else
      {
   			IntStream is = Arrays.stream(max_len);
   			rv = is.map(l -> {return l < COL_MIN ? COL_MIN : l; })
      		.toArray();
     }

		int len = Arrays.stream(rv).sum();

		rv[rv.length - 1] += Math.max(0, total - len - 1);

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
