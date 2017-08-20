package com.xivvic.args.display;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.util.TableData;

public class TabularDisplayTest
{
	private TabularDisplay subject;

	@Before
	public void before()
	{
		TableData table = getExampleTable();

		subject = new TabularDisplay(table);
	}

	@Test(expected=IllegalArgumentException.class)
	public void onFormat_withNegativeWidth_thenThrowException()
	{
		subject.format(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void onFormat_withTooSmallWidth_thenThrowException()
	{
		subject.format(30);
	}

	@Test
	public void onFormat_withSufficientWidth_thenAllLinesOfResultingTableAreOfTheCorrectWidth()
	{
		// Arrange
		//
		int width = 120;

		// Act
		//
		String   table = subject.format(width);
		String[] lines = table.split("\n");

		// Assert
		//
		for (String line: lines)
		{
			System.out.println(line);
			assertEquals(width, line.length());
		}
	}

	/////////////////////////
	// Helpers             //
	/////////////////////////

	private TableData getExampleTable()
	{
		String[] headers = { "Name",   "Type",    "Required", "Provided", "Description" };
		String[]      d1 = { "help",   "BOOLEAN", "NO",       "NO",       "Prints help text" };
		String[]      d2 = { "host",   "HOST",    "YES",      "YES",      "The server DNS name or IP address" };
		String[]      d3 = { "port",   "INTEGER", "YES",      "YES",      "The port on the server for incoming connections" };

		TableData rv = new TableData(headers);
		rv.row(d1);
		rv.row(d2);
		rv.row(d3);

		return rv;
	}
}
