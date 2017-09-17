package com.xivvic.args.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.ibm.icu.text.MessagePattern;
import com.xivvic.args.StandardOptions;
import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.TestUtil;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.item.Item;

public class SchemaTest
{

	private Schema subject;

	@Before
	public void before() throws Exception
	{
		Map<String, Item<?>> args = new HashMap<>();
		String               name = "x";
		Item<?>              item = createItem(name, OptionType.STRING);
		args.put(name, item);

		subject = new Schema(args);
	}

	@Test
	public void onSort_withITEM_NAME_COMPARATOR_thenHelpIsLast()
	{
		// Arrange
		//
		List<String> names = Arrays.asList("port", "server", "help", "verbose", "candy");

		// Act
		//
		Collections.sort(names, Item.ITEM_NAME_COMPARATOR);

		// Assert
		//
		assertEquals("help", names.get(names.size() - 1).toString());
	}


	@Test
	public void onGetOptions_withOneItem_thenTheListContainsOneItem()
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
	public void onGetOptions_withHelpIncluded_thenHelpIsLastInList() throws Exception
	{
		// Arrange
		//
		Map<String, Item<?>> args = new HashMap<>();
		String        alpha = "alpha";
		String            h = StandardOptions.HELP.toString();
		String         zeta = "zeta";
		Item<Integer> first = createRequiredIntegerItem(alpha);
		Item<Integer>  last = createRequiredIntegerItem(zeta);
		Item<?>        help = createItem(h, OptionType.BOOLEAN);

		args.put(alpha, first);
		args.put(h, help);
		args.put(zeta, last);
		subject = new Schema(args);

		// Act
		//
		List<String> result = subject.getOptions();

		// Assert
		//
		assertNotNull(result);
		assertEquals(h, result.get(result.size() - 1).toString());
	}

	@Test
	public void onGetItem_beforeApplyingArguments_thenTheItemHasNoValue()
	{
		// Act
		//
		Item<String> item = subject.getItem("x");
		String      value = item.getEval().getValue();

		// Assert
		//
		assertNotNull(item);
		assertNull(value);
	}

	@Test
	public void onRequiredItemsCheck_withSingleRequiredIntegerBound_thenReturnTrue() throws Exception
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
	public void onRequiredItemsCheck_withSingleRequiredIntegerNotBound_thenReturnFalse() throws Exception
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
	public void onRequiredItemsCheck_withNothingRequired_thenReturnTrue()
	{
		// Act
		//
		boolean ok = subject.allRequiredOptionsHaveValuesOrDefaults();

		// Assert
		//
		assertTrue(ok);
	}

	@Test
	public void onCreate_withEnvironmentVariables_thenItemsHaveEnvironmentVariables() throws Exception
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

	@Test
	public void onWithout_withNullOptionName_thenReturnSameSchema() throws Exception
	{
		// Arrange
		//
		String defs = "[p] dv=/tmp type=PATH \n[s] ev=MY_SERVER type=STRING dv=localhost";
		Schema schema = new Text2Schema().createSchema(defs);

		// Act
		//
		Schema result = schema.without(null);

		// Assert
		//
		assertSame(schema, result);
	}

	@Test
	public void onWithout_withMissinOptionName_thenReturnSameSchema() throws Exception
	{
		// Arrange
		//
		String defs = "[p] dv=/tmp type=PATH \n[s] ev=MY_SERVER type=STRING dv=localhost";
		Schema schema = new Text2Schema().createSchema(defs);

		// Act
		//
		Schema result = schema.without("Flavor");

		// Assert
		//
		assertSame(schema, result);
	}

	@Test
	public void onWithout_withValidOptionName_thenReturnSchemaWithoutOption() throws Exception
	{
		// Arrange
		//
		String defs = "[p] dv=/tmp type=PATH \n[s] ev=MY_SERVER type=STRING dv=localhost";
		Schema schema = new Text2Schema().createSchema(defs);

		// Act
		//
		Schema        result = schema.without("s");
		Item<Path>      path = result.getItem("p");
		Item<String> missing = result.getItem("s");

		// Assert
		//
		assertNull(missing);
		assertNotNull(path);
		assertEquals(OptionType.PATH, path.getType());
		assertEquals(1, result.optionCount());
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
