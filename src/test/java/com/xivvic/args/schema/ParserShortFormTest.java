package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.item.Item;

public class ParserShortFormTest
{
	private ParserShortForm subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new ParserShortForm(ErrorStrategy.FAIL_FAST);
	}

	@Test
	public void testSchemaBuilderSingleBooleanTest() throws Exception
	{
		// Arrange
		//
		String opt = "b";

		// Act
		//
		Schema schema = subject.parse(opt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.BOOLEAN);
	}

	@Test
	public void testSchemaBuilderSingleStringTest() throws Exception
	{
		// Arrange
		//
		String opt = "s";
		String fmt = "s*";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.STRING);
	}

	@Test
	public void testSchemaBuilderOneDouble() throws Exception
	{
		// Arrange
		//
		String opt = "d";
		String fmt = "d##";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<Double> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.DOUBLE);
	}

	@Test
	public void testSchemaBuilderOneStringList() throws Exception
	{
		// Arrange
		//
		String opt = "l";
		String fmt = "l[*]";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<List<String>> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.STRING_LIST);
	}

	@Test
	public void testSchemaBuilderSingleIntegerTest() throws Exception
	{
		// Arrange
		//
		String opt = "i";
		String fmt = "i#";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<Boolean> item = schema.getItem(opt);

		// Assert
		//
		assertSimpleItemForm(item, opt, OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBSITest() throws Exception
	{
		// Arrange
		//
		String fmt = "b,s*,i#";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<Boolean> i1 = schema.getItem("b");
		Item<String>  i2 = schema.getItem("s");
		Item<Integer> i3 = schema.getItem("i");

		// Assert
		//
		assertSimpleItemForm(i1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(i2, "s", OptionType.STRING);
		assertSimpleItemForm(i3, "i", OptionType.INTEGER);
	}

	@Test
	public void testSchemaBuilderBIBWithSpaces() throws Exception
	{
		// Arrange
		//
		String fmt = "b, i# , x";

		// Act
		//
		Schema schema = subject.parse(fmt);
		Item<Boolean> i1 = schema.getItem("b");
		Item<Boolean> i2 = schema.getItem("i");
		Item<Boolean> i3 = schema.getItem("x");

		// Assert
		//
		assertSimpleItemForm(i1, "b", OptionType.BOOLEAN);
		assertSimpleItemForm(i2, "i", OptionType.INTEGER);
		assertSimpleItemForm(i3, "x", OptionType.BOOLEAN);
	}

	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private void assertSimpleItemForm(Item<?> item, String name, OptionType type)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertNull(item.getRequired());
		assertNull(item.getDv());
		assertEquals(type, item.getType());

		OptEvaluator<?> eval = item.getEval();
		assertNotNull(eval);
		assertEquals(0, eval.count());
		assertNull(eval.getValue());
	}
}
