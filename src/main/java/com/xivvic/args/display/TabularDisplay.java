package com.xivvic.args.display;

import java.util.Objects;
import java.util.StringJoiner;

import com.xivvic.args.util.TextFormatter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TabularDisplay
{
	private final TableData table;

	public TabularDisplay(TableData table)
	{
		this.table = Objects.requireNonNull(table);
	}

	public String format(int width)
	{
		assertWidthIsAdequate(width);

		int[]  cw = calculateColumnWidths(width);
		String rv = generateTableText(width, cw);
		return rv;
	}

	private String generateTableText(int width, int[] cw)
	{
		StringBuilder sb = new StringBuilder();

		String t = generateTopLine(width);
		String h = generateHeader(width, cw);
		String s = generateSeparatorLine(cw, '=');
		String d = generateDataRows(cw);
		String b = generateBottomLine(width);

		sb.append(t).append(h).append(s);
		sb.append(d).append(b);

		String rv = sb.toString();
		return rv;
	}

	private String generateDataRows(int[] cw)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < table.data().size(); i++)
		{
			String rowString = generateRowString(i, cw);
			sb.append(rowString);
		}

		String rv = sb.toString();
		return rv;
	}

	private int[] calculateColumnWidths(int width)
	{
		TableDimensionCalculator tdc = new TableDimensionCalculator(table.headers(), table.data());
		int[] cw = tdc.fitDimensions(width);
		return cw;
	}

	private String generateHeader(int width, int[] cw)
	{
		StringJoiner  sj = new StringJoiner("| ");
		for (int i = 0; i < cw.length; i++)
		{
			String       h = table.headers()[i];
			String content = TextFormatter.boundedElipsisSubstring(h, cw[i] - 2, '.');
			String padded = TextFormatter.pad(content, cw[i] - 2, ' ');
			sj.add(padded);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("| ").append(sj.toString()).append("|\n");
		return sb.toString();
	}

	private String generateRowString(int row, int[] cw)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		for (int i = 0; i < cw.length; i++)
		{
			int l = cw[i] - 2;
			String datum = table.data().get(row)[i];
			String content = TextFormatter.boundedElipsisSubstring(datum, l, '.');
			String padded = TextFormatter.pad(content, l, ' ');
			sb.append(" ");
			sb.append(padded);
			sb.append("|");
		}
		sb.append("\n");
		return sb.toString();
	}

	// This produces a line like
	// +=========================+
	//
	// of exactly the specified width
	//
	private String generateTopLine(int width)
	{
		StringBuilder sb = new StringBuilder();
		int           iw = TextFormatter.edgedTotalWidth2InteriorWidth.applyAsInt(width);
		String   segment = TextFormatter.edgedLine('+', '=', iw);
		sb.append(segment);
		sb.append("\n");
		return sb.toString();
	}

	// This produces a line like
	// +-------------------------+
	//
	// of exactly the specified width
	//
	private String generateBottomLine(int width)
	{
		StringBuilder sb = new StringBuilder();
		String   segment = TextFormatter.edgedLine('+', '-', width - 2);
		sb.append(segment);
		sb.append("\n");
		return sb.toString();
	}

	// This produces a line like
	// +=======+======+=====+====+
	//
	// Where the interior '+' characters are on the line boundaries
	// of exactly the specified width
	//
	private String generateSeparatorLine(int[] cw, char c)
	{
		StringBuilder sb = new StringBuilder();
		char edge = '+';

		for (int i = 0; i < cw.length; i++)
		{
			int current = cw[i];
			sb.append(edge);
			String segment = TextFormatter.pad("", current - 2, c);
			sb.append(segment);
			sb.append(c);
		}
		sb.append("+\n");
		return sb.toString();
	}

	private void assertWidthIsAdequate(int width)
	{
		String suggested = "Minimum width [40], suggested width at least [72].";

		if (width < 1)
		{
			String msg = String.format("Cannot format TabularDisplay with provided width: [%d]i. %s\n", width, suggested);
			throw new IllegalArgumentException(msg);
		}
		if (width < 40)
		{
			String msg = String.format("Larger width required for output formatting. Provided [%d]. %s\n", width, suggested);
			throw new IllegalArgumentException(msg);
		}
		if (width < 60)
		{
			String msg = String.format("Squeezing TabularDisplay into [%d] spaces. %s\n", width, suggested);
			log.warn(msg);
		}
	}

}
