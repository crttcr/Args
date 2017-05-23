package com.xivvic.args.util;

import static com.xivvic.args.util.trie.Constants.DIM;
import static com.xivvic.args.util.trie.Constants.VALID_CHARS;
import static com.xivvic.args.util.trie.Constants.indexOfCharacter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ConstantsTest
{
	@Test
	public void testVALID_CHARS_hasChars()
	{
		assertNotNull(VALID_CHARS);
		assertTrue(VALID_CHARS.length() > 0);
	}

	@Test
	public void testVALID_CHARS_containsUniqueChars()
	{
		Set<Character> seen = new HashSet<>();

		for (int i = 0; i < VALID_CHARS.length(); i++)
		{
			Character c = VALID_CHARS.charAt(i);
			assertFalse(seen.contains(c));
			seen.add(c);
		}
	}

	@Test
	public void testR()
	{
		int count = VALID_CHARS.length();
		assertEquals(count, DIM);
	}

	@Test
	public void testIndexOfCharacterIsCorrect()
	{
		for (int i = 0; i < VALID_CHARS.length(); i++)
		{
			char c = VALID_CHARS.charAt(i);
			int index = indexOfCharacter(c);
			assertEquals(i, index);
		}
	}
}

