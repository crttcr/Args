package com.xivvic.args.schema.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.xivvic.args.error.ArgsException;
import com.xivvic.args.schema.OptionType;

public class ItemBuilderTest
{
	@Test(expected=ArgsException.class)
	public void testBuildWithNoValuesEstablished() throws ArgsException
	{
		Item.builder().build();
	}

	@Test(expected=ArgsException.class)
	public void testBuildWithoutName() throws ArgsException
	{
		// Arrange
		//
		String type = OptionType.STRING.toString();
		String dv = "default value";
		Item.Builder<String> builder = Item.builder();
		builder.type(type).dv(dv);

		// Act
		//
		builder.build();
	}

	@Test(expected=ArgsException.class)
	public void testBuildWithoutType() throws ArgsException
	{
		// Arrange
		//
		String name = "message";
		String dv = "default value";
		Item.Builder<String> builder = Item.builder();
		builder.name(name).dv(dv);

		// Act
		//
		builder.build();
	}

	@Test
	public void testBuildWithMinimalInformation() throws ArgsException
	{
		// Arrange
		//
		String name = "message";
		String type = OptionType.STRING.toString();
		Item.Builder<String> builder = Item.builder();
		builder.name(name).type(type);

		// Act
		//
		Item<String> item = builder.build();

		// Assert
		//
		assertComplexItem(item, name, type, null);
	}



	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////

	private <T> void assertComplexItem(Item<T> item, String name, String type, String dv)
	{
		assertNotNull(item);
		assertEquals(name, item.getName());
		assertEquals(type, item.getType().name());

		T defaultValue = item.getEval().getDefault();

		if (dv == null)
		{
			assertNull(defaultValue);
		}
		else
		{
			assertNotNull(defaultValue);
		}
	}

}
