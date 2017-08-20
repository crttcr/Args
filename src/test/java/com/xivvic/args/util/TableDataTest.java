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
	public void onCreate_withHeaders_thenReturnValidTable()
	{
		String[] headers = { "Option", "Type", "Description", "Provided" };
		subject = new TableData(headers);
		assertNotNull(subject);
	}

}
