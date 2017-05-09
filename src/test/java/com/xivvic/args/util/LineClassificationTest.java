package com.xivvic.args.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class LineClassificationTest
{
	@Test
	public void testClassifyNull()
	{
		// Arrange
		//
		String line = null;

		// Act
		//
		LineClassification lc = LineClassification.classify(line);

		// Assert
		//
		assertEquals(LineClassification.Null, lc);
	}

	@Test
	public void testClassifyBlank()
	{
		// Arrange
		//
		String l1 = "";
		String l2 = "  ";
		String l3 = "\t";
		String l4 = "\n";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);

		// Assert
		//
		assertEquals(LineClassification.Blank, lc1);
		assertEquals(LineClassification.Blank, lc2);
		assertEquals(LineClassification.Blank, lc3);
		assertEquals(LineClassification.Malformed, lc4);
	}

	@Test
	public void testIsSectionHeaderSuccess()
	{
		// Arrange
		//
		String l1 = "[]";
		String l2 = "[ant]";
		String l3 = " [ant]";
		String l4 = " [ant] ";
		String l5 = " [ant pant] ";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);
		LineClassification lc5 = LineClassification.classify(l5);

		// Assert
		//
		assertEquals(LineClassification.SectionHeader, lc1);
		assertEquals(LineClassification.SectionHeader, lc2);
		assertEquals(LineClassification.SectionHeader, lc3);
		assertEquals(LineClassification.SectionHeader, lc4);
		assertEquals(LineClassification.SectionHeader, lc5);
	}

	@Test
	public void testIsSectionHeaderFailure()
	{
		// Arrange
		//
		String l1 = "[";
		String l2 = "]";
		String l3 = "]ant]";
		String l4 = "[ant[";
		String l5 = " [gorilla";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);
		LineClassification lc5 = LineClassification.classify(l5);

		// Assert
		//
		assertFalse(LineClassification.SectionHeader == lc1);
		assertFalse(LineClassification.SectionHeader == lc2);
		assertFalse(LineClassification.SectionHeader == lc3);
		assertFalse(LineClassification.SectionHeader == lc4);
		assertFalse(LineClassification.SectionHeader == lc5);
	}

	@Test
	public void testClassifyComment()
	{
		// Arrange
		//
		String l1 = "#";
		String l2 = "## This is a comment";
		String l3 = "\t ## Comment with tab character";
		String l4 = "## Comment followed by newline. \n This is two lines.";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);

		// Assert
		//
		assertEquals(LineClassification.Comment, lc1);
		assertEquals(LineClassification.Comment, lc2);
		assertEquals(LineClassification.Comment, lc3);
		assertEquals(LineClassification.Malformed, lc4);
	}

	@Test
	public void testClassifyPropertyDefinitionSuccess()
	{
		// Arrange
		//
		String l1 = "a=b";
		String l2 = "a:b";
		String l3 = "a=";
		String l4 = "a:";
		String l5 = " apple=";
		String l6 = " apple:";
		String l7 = " apple = bravo";
		String l8 = " apple : bravo";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);
		LineClassification lc5 = LineClassification.classify(l5);
		LineClassification lc6 = LineClassification.classify(l6);
		LineClassification lc7 = LineClassification.classify(l7);
		LineClassification lc8 = LineClassification.classify(l8);

		// Assert
		//
		assertEquals(LineClassification.PropertyDefinition, lc1);
		assertEquals(LineClassification.PropertyDefinition, lc2);
		assertEquals(LineClassification.PropertyDefinition, lc3);
		assertEquals(LineClassification.PropertyDefinition, lc4);
		assertEquals(LineClassification.PropertyDefinition, lc5);
		assertEquals(LineClassification.PropertyDefinition, lc6);
		assertEquals(LineClassification.PropertyDefinition, lc7);
		assertEquals(LineClassification.PropertyDefinition, lc8);
	}

	@Test
	public void testClassifyPropertyDefinitionFailure()
	{
		// Arrange
		//
		String l1 = "=b";
		String l2 = ":b";
		String l3 = "  =";
		String l4 = "  :";
		String l5 = " =  ";
		String l6 = " : ";
		String l7 = "\napple=  ";
		String l8 = "\napple: ";

		// Act
		//
		LineClassification lc1 = LineClassification.classify(l1);
		LineClassification lc2 = LineClassification.classify(l2);
		LineClassification lc3 = LineClassification.classify(l3);
		LineClassification lc4 = LineClassification.classify(l4);
		LineClassification lc5 = LineClassification.classify(l5);
		LineClassification lc6 = LineClassification.classify(l6);
		LineClassification lc7 = LineClassification.classify(l7);
		LineClassification lc8 = LineClassification.classify(l8);

		// Assert
		//
		assertFalse(LineClassification.PropertyDefinition == lc1);
		assertFalse(LineClassification.PropertyDefinition == lc2);
		assertFalse(LineClassification.PropertyDefinition == lc3);
		assertFalse(LineClassification.PropertyDefinition == lc4);
		assertFalse(LineClassification.PropertyDefinition == lc5);
		assertFalse(LineClassification.PropertyDefinition == lc6);
		assertFalse(LineClassification.PropertyDefinition == lc7);
		assertFalse(LineClassification.PropertyDefinition == lc8);
	}

}
