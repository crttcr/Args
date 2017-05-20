package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.xivvic.args.TestUtil;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.item.Item;

public class SchemaBuilderLongFormTest
{
	public static String TEST_SPEC_LOCATION = "src/test/resources/definitions";

	private SchemaBuilder subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new SchemaBuilder();
	}

	@Test
	public void testSchemaBuilderDouble() throws Exception
	{
		// Arrange
		//
		String defs = TestUtil.readFromTestResourceFile(TEST_SPEC_LOCATION, "latitude.longitude.argspec");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Double> latitude  = schema.getItem("latitude");
		Item<Double> longitude = schema.getItem("longitude");

		// Assert
		//
		assertItemForm(latitude,  "latitude",  OptionType.DOUBLE, null, 100.04);
		assertItemForm(longitude, "longitude", OptionType.DOUBLE, true, 10.5);
	}

	@Test
	public void testSchemaBuilderBoolean() throws Exception
	{
		// Arrange
		//
		String defs = TestUtil.readFromTestResourceFile("verbose.silent.argspec");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> v = schema.getItem("verbose");
		Item<Boolean> q = schema.getItem("silent");

		// Assert
		//
		assertItemForm(v, "verbose", OptionType.BOOLEAN, null, true);
		assertItemForm(q, "silent",  OptionType.BOOLEAN, false, false);
	}

	@Test
	public void testSchemaBuilderBooleanNoRequiredNoDefault() throws Exception
	{
		// Arrange
		//
		String defs = "[x]\n";

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> item = schema.getItem("x");

		// Assert
		//
		assertItemForm(item, "x", OptionType.BOOLEAN, null, null);
	}

	@Ignore("Default values not implemented because of Java generic challenges")
	@Test
	public void testSchemaBuilderBooleanWithDefaultTrue() throws Exception
	{
		// Arrange
		//
		String defs = TestUtil.readFromTestResourceFile("boolean.default.argspec");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> item = schema.getItem("verbose");

		// Assert
		//
		assertItemForm(item, "verbose", OptionType.BOOLEAN, true, null);
	}




	///////////////////////////////
	// Helper Methods //
	///////////////////////////////

	private void assertItemForm(Item<?> item, String name, OptionType type, Boolean required, Object dv)
	{
		assertNotNull(item);
		OptEvaluator<?> eval = item.getEval();
		assertNotNull(eval);

		assertEquals(name, item.getName());
		assertEquals(required, item.getRequired());
		assertEquals(type, item.getType());
		assertEquals(dv, eval.getDefault());
	}

}
