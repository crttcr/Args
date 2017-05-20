package com.xivvic.args.util;

public class BooleanUtil
{
	/**
	 * Parses a non-null string as a boolean, being permissive by allowing
	 * "true", "t", "yes", and "y" all to return Boolean.TRUE. Similar values
	 * meaning false result in Boolean.FALSE. For all other inputs null is returned.
	 *
	 * @param input the input value to parse
	 * @return Boolean value or null, based on the input
	 */
	public static Boolean parseBoolean(String input)
	{
		if (input == null)
		{
			return null;
		}

		input = input.toLowerCase();

		switch (input)
		{
		case "true":
		case "yes":
		case "t":
		case "y":
			return Boolean.TRUE;
		case "false":
		case "no":
		case "f":
		case "n":
			return Boolean.FALSE;
		default:
			return null;
		}
	}

}
