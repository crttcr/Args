package com.xivvic.args.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TableDataTest
{
	private TableData subject;

	@Test(expected=NullPointerException.class)
	public void testConstructWithNullHeaders()
	{
		new TableData(null);
	}

	@Test
	public void testSuccessfulConstruction()
	{
		String[] headers = { "Option", "Type", "Description", "Provided" };
		subject = new TableData(headers);
		assertNotNull(subject);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructWithNegativeWidth()
	{
		String[] headers = { "Option", "Type", "Description", "Provided" };
		new TableData(headers).format(-1);
	}

	@Test
	public void testFormatSuccess()
	{
		// Arrange
		//
		String[] headers = { "Option", "Type", "Provided", "Description" };
		String[]      d1 = { "help", "BOOLEAN", "NO", "Prints help text" };
		String[]      d2 = { "host", "HOST", "YES", "The server DNS name or IP address" };
		String[]      d3 = { "port", "STRING", "YES", "The port on the server for incoming connections" };

		TableData td = new TableData(headers);
		td.row(d1);
		td.row(d2);
		td.row(d3);

		// Act
		//
		String fmt = td.format(4 * 30);


		System.out.println(fmt);

		// Assert
		//
		assertNotNull(fmt);
	}

}
