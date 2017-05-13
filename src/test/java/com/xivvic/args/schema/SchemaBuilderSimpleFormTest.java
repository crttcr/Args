package com.xivvic.args.schema;

import static com.xivvic.args.schema.OptionType.BOOLEAN;
import static com.xivvic.args.schema.OptionType.INTEGER;
import static com.xivvic.args.schema.OptionType.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.schema.OptionType;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.SchemaBuilderRevised;
import com.xivvic.args.schema.item.Item;

public class SchemaBuilderSimpleFormTest
{
	private SchemaBuilderRevised subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new SchemaBuilderRevised();
	}

	@Test
	public void testSchemaBuilderWorksForSingleItemOfEachType() throws Exception
	{
		// Arrange
		//
		List<InputTuple> inputs = allInputTuples();

		// Act + Assert
		//
		for (InputTuple it : inputs)
		{
			Map<String, String> defs = getSimpleDefs(it.option, it.type);
			subject = new SchemaBuilderRevised(ErrorStrategy.FAIL_FAST);
			Schema schema = subject.def(defs).build();
			Item<?> item = schema.getItem(it.option);

			assertSimpleItemForm(item, it.option, it.type);
		}
	}

	@Test
	public void testSchemaBuilderBSITest() throws Exception
	{
		// Arrange
		//
		Map<String, String> d1 = getSimpleDefs("b", BOOLEAN);
		Map<String, String> d2 = getSimpleDefs("s", STRING);
		Map<String, String> d3 = getSimpleDefs("i", INTEGER);

		// Act
		//
		Schema schema = subject.def(d1).def(d2).def(d3).build();
		Item<Boolean> i1 = schema.getItem("b");
		Item<String>  i2 = schema.getItem("s");
		Item<Integer> i3 = schema.getItem("i");

		// Assert
		//
		assertSimpleItemForm(i1, "b", BOOLEAN);
		assertSimpleItemForm(i2, "s", STRING);
		assertSimpleItemForm(i3, "i", INTEGER);
	}

	@Test
	public void testSchemaBuilderBIB() throws Exception
	{
		// Arrange
		//
		Map<String, String> d1 = getSimpleDefs("b", BOOLEAN);
		Map<String, String> d2 = getSimpleDefs("i", INTEGER);
		Map<String, String> d3 = getSimpleDefs("v", BOOLEAN);

		// Act
		//
		Schema schema = subject.def(d1).def(d2).def(d3).build();
		Item<Boolean> i1 = schema.getItem("b");
		Item<Integer> i2 = schema.getItem("i");
		Item<Boolean> i3 = schema.getItem("v");

		// Assert
		//
		assertSimpleItemForm(i1, "b", BOOLEAN);
		assertSimpleItemForm(i2, "i", INTEGER);
		assertSimpleItemForm(i3, "v", BOOLEAN);
	}

	///////////////////////////////
	// Helper Methods & classes //
	///////////////////////////////

	private static class InputTuple
	{
		public final String		option;
		public final OptionType	type;

		public InputTuple(String option, OptionType type)
		{
			this.option = option;
			this.type = type;
		}
	}

	private List<InputTuple> allInputTuples()
	{
		List<InputTuple> list = new LinkedList<>();

		for (OptionType t : OptionType.values())
		{
			InputTuple it = new InputTuple(t.name().substring(0, 1), t);
			list.add(it);
		};

		return list;
	}

	private Map<String, String> getSimpleDefs(String opt, OptionType type)
	{
		Map<String, String> rv = new HashMap<>();

		rv.put(Item.NAME, opt);
		rv.put(Item.TYPE, type.name());

		return rv;
	}

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
