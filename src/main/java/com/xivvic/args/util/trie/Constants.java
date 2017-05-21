package com.xivvic.args.util.trie;

// NOTE: Based on the way indices are calculated, indexOfCharacter() will fail
// if the NUMBERS, UPPER, and LOWER character sets are not comprehensive.
//
// You can change the SYMBOLS without compromising the approach,
// but the not the other constituents of VALID_CHARS.
//
public abstract class Constants
{
	public static final String VALID_CHAR_SYMBOLS = "_.-+";
	public static final String VALID_CHAR_NUMBERS = "0123456789";
	public static final String   VALID_CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String   VALID_CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";

	public static final String VALID_CHARS =
	VALID_CHAR_SYMBOLS +
	VALID_CHAR_NUMBERS +
	VALID_CHAR_UPPER +
	VALID_CHAR_LOWER;

	public static final int R = VALID_CHARS.length();

	public static int indexOfCharacter(char c)
	{
		switch (c)
		{
		case '_': return 0;
		case '.': return 1;
		case '-': return 2;
		case '+': return 3;
		}


		if (c <= '9')
		{
			return c - '0' + VALID_CHAR_SYMBOLS.length();
		}

		if (c <= 'Z')
		{
			int pos = c - 'A' + VALID_CHAR_SYMBOLS.length() + VALID_CHAR_NUMBERS.length();
			return pos;
		}

		int pos = c - 'a' + VALID_CHAR_SYMBOLS.length() + VALID_CHAR_NUMBERS.length() + VALID_CHAR_UPPER.length();
		return pos;
	}

}
