package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.StringJoiner;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.TestUtil;
import com.xivvic.args.schema.item.Item;

public class SchemaBuilderLongFormTest
{
	// FIXME.  SBLF no longer creates a schema, so the MAP<MAP> needs to be tested instead.

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
		String defs = TestUtil.readFromTestResourceFile("latitude.longitude.txt");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Double> latitude  = schema.getItem("latitude");
		Item<Double> longitude = schema.getItem("longitude");

		// Assert
		//
		assertItemForm(latitude,  "latitude",  OptionType.DOUBLE, null, null);
		assertItemForm(longitude, "longitude", OptionType.DOUBLE, true, 10.5);
	}

	@Test
	public void testSchemaBuilderBoolean() throws Exception
	{
		// Arrange
		//
		String defs = TestUtil.readFromTestResourceFile("verbose.quiet.txt");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> v = schema.getItem("verbose");
		Item<Boolean> q = schema.getItem("quiet");

		// Assert
		//
		assertItemForm(v, "verbose", OptionType.BOOLEAN, null, null);
		assertItemForm(q, "quiet",   OptionType.BOOLEAN, false, false);
	}

	@Test
	public void testSchemaBuilderBooleanNoRequiredNoDefault() throws Exception
	{
		// Arrange
		//
		String name = "x";
		String defs = createDefs(name, OptionType.BOOLEAN, null, null);

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> item = schema.getItem(name);

		// Assert
		//
		assertItemForm(item, name, OptionType.BOOLEAN, null, null);
	}

	@Test
	public void testSchemaBuilderBooleanWithDefaultTrue() throws Exception
	{
		// Arrange
		//
		String defs = TestUtil.readFromTestResourceFile("b.rt");

		// Act
		//
		Schema schema = new Text2Schema().createSchema(defs);
		Item<Boolean> item = schema.getItem("b");

		// Assert
		//
		assertItemForm(item, "b", OptionType.BOOLEAN, true, null);
	}




	///////////////////////////////
	// Helper Methods //
	///////////////////////////////

	private void assertItemForm(Item<?> item, String name, OptionType type, Boolean required, Object dv)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(required, item.getRequired());
		assertNull(item.getDv());
		assertEquals(type, item.getType());
		assertNotNull(item.getEval());
	}

	private String createDefs(String name, OptionType type, Boolean required, String dv)
	{
		StringJoiner sj = new StringJoiner("\n");

		sj.add(name + ".name=" + name);
		sj.add(name + ".type=" + type);

		if (required != null)
		{
			sj.add(name + ".required=" + required);
		}

		return sj.toString();
	}

}
