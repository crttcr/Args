package com.xivvic.args.schema.item;

import java.util.Comparator;
import java.util.Map;

import com.xivvic.args.StandardOptions;
import com.xivvic.args.error.ErrorCode;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.marshall.OptEvaluator;
import com.xivvic.args.marshall.OptEvaluatorBase;
import com.xivvic.args.schema.OptionType;
import com.xivvic.args.util.BooleanUtil;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item<T>
{
	public static final Comparator<String> ITEM_NAME_COMPARATOR = getItemNameComparator();


	public final static String	NAME			= "name";
	public final static String	TYPE			= "type";
	public final static String	DESCRIPTION	= "description";
	public final static String	DEFAULT		= "dv";
	public final static String	REQUIRED		= "required";
	public final static String	ENV_VAR		= "ev";

	private String          name;
	private OptionType      type;
	private OptEvaluator<T> eval;
	private Boolean     required;
	private String   description;
	private String            ev;

	private Item()
	{
	}

	public Boolean getRequired()
	{
		if (required == null)
		{
			return null;
		}

		return required;

	}

	public static <U> Builder<U> builder()
	{
		Builder<U> rv = new Builder<>();
		return rv;
	}

	public static <U> Builder<U> builder(Map<String, String> args) throws SchemaException
	{
		Builder<U> rv = new Builder<>();

		if (args == null)
		{
			return rv;
		}

		String name = args.get(NAME);
		String type = args.get(TYPE);
		String desc = args.get(DESCRIPTION);
		String reqd = args.get(REQUIRED);
		String   ev = args.get(ENV_VAR);
		String   dv = args.get(DEFAULT);

		if (name != null) { rv.name(name);        }
		if (type != null) { rv.type(type);        }
		if (desc != null) { rv.description(desc); }
		if (reqd != null) { rv.required(reqd);    }
		if (ev   != null) { rv.ev(ev);            }
		if (dv   != null) { rv.dv(dv);            }   // Occurs after type() is set.

		return rv;
	}

	public static class Builder<T>
	{
		// If you were to make this field final, you can't reuse the builder to create a different object.
		//
		// Reuse would permit you to potentially leak changes to the object if the caller holds on
		// to your builder, so each builder can only build a single instance.
		//
		private final Item<T> instance = new Item<>();

		private String dv;

		public Builder<T> name(String name)
		{
			this.instance.name = name;
			return this;
		}

		public Builder<T> type(String type) throws SchemaException
		{
			if (type == null)
			{
				this.instance.type = null;
				this.instance.eval = null;
				return this;
			}

			try
			{
				OptionType ot = OptionType.valueOf(type);
				this.instance.type = ot;
				OptEvaluator<T> oe = OptEvaluatorBase.getEvaluatorForType(ot);
				this.instance.eval = oe;
			}
			catch (Exception e)
			{
				throw new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, instance.name, type);
			}

			return this;
		}

		public Builder<T> description(String description)
		{
			this.instance.description = description;
			return this;
		}

		public Builder<T> ev(String ev)
		{
			this.instance.ev = ev;
			return this;
		}

		public Builder<T> dv(String dv)
		{
			this.dv = dv;
			return this;
		}

		public Builder<T> required(String required)
		{
			this.instance.required = BooleanUtil.parseBoolean(required);
			return this;
		}

		@SuppressWarnings("unchecked")
		public Item<T> build() throws SchemaException
		{
			assertValid();

			if (dv != null)
			{
				instance.eval.setDefaultValue(dv);
			}

			Item<T> result = instance;
			return result;
		}

		private void assertValid() throws SchemaException
		{
			if (instance.name == null)
			{
				String msg = String.format("Items require a valid name: [%s]", instance);
				throw new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
			if (instance.type == null)
			{
				String msg = String.format("Items require a valid type: [%s]", instance);
				throw new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
			if (instance.eval == null)
			{
				String msg = String.format("Items require a valid evaluator: [%s]", instance);
				throw new SchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT, msg);
			}
		}

	}

	/**
	 * Returns a comparator that always sorts "help" to the end
	 *
	 */
	private static Comparator<String> getItemNameComparator()
	{
		Comparator<String> c = new Comparator<String>() {
			public int compare(String a, String b)
			{
				String help = StandardOptions.HELP.toString();
				if (a.equalsIgnoreCase(help))
				{
					return b.equalsIgnoreCase(help) ? 0 : 1;
				}
				else if (b.equalsIgnoreCase(help)) // Only b is help, so a < b
				{
					return -1;
				}
				else // neither are help
				{
					return a.compareToIgnoreCase(b);
				}
			}
		};

		return c;
	}
}