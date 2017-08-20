package com.xivvic.args.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextFormatterTest
{
	@Test
	public void onEdgedLine_withPositiveInteriorLength_thenReturnLineWithCorrectWidth()
	{
		// Arrange
		//
		int  totalWidth = 12;
		int insideWidth = TextFormatter.edgedTotalWidth2InteriorWidth.applyAsInt(totalWidth);

		// Act
		//
		String result = TextFormatter.edgedLine('#', '-', insideWidth);

		// Assert
		//
		assertEquals(totalWidth, result.length());
	}


}
