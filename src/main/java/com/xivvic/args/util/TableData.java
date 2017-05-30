package com.xivvic.args.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public String format(int width)
	{
		if (width < 40)
		{
			String msg = String.format("Cannot format TableData without a reasonalble width for output formatting. Provided [%d]", width);
			throw new IllegalArgumentException(msg);
		}
		if (width < 60)
		{
			String msg = "Cannot format TableData without a reasonalble width for output formatting. Provided [{}]";
			log.warn(msg, width);
		}

		TableDimensionCalculator tdc = new TableDimensionCalculator(headers, data);
		int[] cw = tdc.fitDimensions(width);
		width = 0;
		for (int i: cw)
		{
			width += i;
		}
		width += 2;

		StringBuilder sb = new StringBuilder();

		String headerString = generateHeader(width, cw);
		sb.append(generateTopLine(width));
		sb.append(headerString);
		sb.append(generateSeparatorLine(width, cw, '='));

		for (int i = 0; i < data.size(); i++)
		{
			String rowString = generateRowString(i, cw);
			sb.append(rowString);
		}

		sb.append(generateBottomLine(width));
		return sb.toString();
	}

	private String generateHeader(int width, int[] cw)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		for (int i = 0; i < cw.length; i++)
		{
			int l = cw[i] - 2;
			String h = headers[i];
			String content = TextFormatter.boundedElipsisSubstring(h, l, '.');
			String padded = TextFormatter.pad(content, l, ' ');
			sb.append(" ");
			sb.append(padded);
			sb.append("|");
		}
		sb.append("\n");
		return sb.toString();
	}

	private String generateRowString(int row, int[] cw)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		for (int i = 0; i < cw.length; i++)
		{
			int l = cw[i] - 2;
			String datum = data.get(row)[i];
			String content = TextFormatter.boundedElipsisSubstring(datum, l, '.');
			String padded = TextFormatter.pad(content, l, ' ');
			sb.append(" ");
			sb.append(padded);
			sb.append("|");
		}
		sb.append("\n");
		return sb.toString();
	}

	private String generateTopLine(int width)
	{
		StringBuilder sb = new StringBuilder();
		String   segment = TextFormatter.edgedLine('+', '=', width - 2);
		sb.append(segment);
		sb.append("\n");
		return sb.toString();
	}

	private String generateBottomLine(int width)
	{
		StringBuilder sb = new StringBuilder();
		String   segment = TextFormatter.edgedLine('+', '-', width - 2);
		sb.append(segment);
		sb.append("\n");
		return sb.toString();
	}

	private Object generateSeparatorLine(int width, int[] cw, char c)
	{
		StringBuilder sb = new StringBuilder();
		char edge = '+';
		char line = c;

		for (int i = 0; i < cw.length; i++)
		{
			int current = cw[i];
			int len = current - 2;
			String segment = TextFormatter.edgedLine(edge, line, len);
			sb.append(segment);
		}
		sb.append("\n");
		return sb.toString();
	}


}
