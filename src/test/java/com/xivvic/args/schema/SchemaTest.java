package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.TestUtil;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.item.Item;

public class SchemaTest
{

	private Schema subject;

	@Before
	public void setUp() throws Exception
	{
		Map<String, Item<?>> args = new HashMap<>();
		String name = "x";
		Item<?> item = createItem(name, OptionType.STRING);
		args.put(name, item);
		subject = new Schema(args);
	}

	@Test
	public void testGetOptionsBaseCase()
	{
		// Act
		//
		List<String> opts = subject.getOptions();

		// Assert
		//
		assertNotNull(opts);
		assertEquals(1, opts.size());
		assertEquals("x", opts.get(0));
	}

	@Test
	public void testGetItemBaseCaseNotSet()
	{
		// Act
		//
		Item<String> item = subject.getItem("x");
		String value = item.getEval().getValue();

		// Assert
		//
		assertNotNull(item);
		assertNull(value);
	}

	@Test
	public void testAllRequiredArgsHaveValuesSingleIntegerSuccess() throws Exception
	{
		// Arrange
		//
		Map<String, Item<?>> args = new HashMap<>();
		String name = "port";
		Item<Integer> theItem = createRequiredIntegerItem(name);
		args.put(name, theItem);
		ListIterator<String> it = TestUtil.getListIterator("49");
		theItem.getEval().set(it);
		subject = new Schema(args);


		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValuesOrDefaults();

		// Assert
		//
		assertTrue(ok);
	}


	@Test
	public void testAllRequiredArgsHaveValuesSingleIntegerFailure() throws Exception
	{
		// Arrange
		//
		Map<String, Item<?>> args = new HashMap<>();
		String name = "port";
		Item<Integer> theItem = createRequiredIntegerItem(name);
		args.put(name, theItem);
		subject = new Schema(args);

		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValuesOrDefaults();

		// Assert
		//
		assertFalse(ok);
	}

	@Test
	public void testAllRequiredOptionsHaveValuesOneOptionFalse()
	{
		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValuesOrDefaults();

		// Assert
		//
		assertTrue(ok);
	}

	@Test
	public void testGetItemsWithEnvironmentVar() throws Exception
	{
		// Arrange
		//
		// Note the '\n' in the definition string forces long form option parsing
		//
		String defs = "[p] dv=/tmp type=PATH \n[s] ev=MY_SERVER type=STRING dv=localhost";
		Schema schema = new Text2Schema().createSchema(defs);

		// Act
		//
		List<Item<?>> items = schema.itemsWithEnvironmentVariables();

		// Assert
		//
		assertNotNull(items);
		assertEquals(1, items.size());
	}

	///////////////////////////////
	// Helper Methods            //
	///////////////////////////////


	private Item<?> createItem(String name, OptionType type)
	{
		try
		{
			Item.Builder<?> builder = Item.builder();
			builder.name(name).type(type.name());
			return builder.build();
		}
		catch (SchemaException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private Item<Integer> createRequiredIntegerItem(String name)
	{
		try
		{
			Item.Builder<Integer> builder = Item.builder();
			builder.name(name).type(OptionType.INTEGER.name()).required("true");
			return builder.build();
		}
		catch (SchemaException e)
		{
			e.printStackTrace();
		}
		return null;
	}


}
