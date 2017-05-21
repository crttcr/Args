package com.xivvic.args.util;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * String -> T
 *
 * Setting a value for a key replaces any previous value for that key.
 * Values cannot be null. Setting a value to null removes any associated key.
 *
 */
public class Trie<Value>
{

	// NOTE: Based on the way indices are calculated, this will fail if the NUMBERS, UPPER, and LOWER
	// character sets are not comprehensive. You can change the SYMBOLS without compromising the approach,
	// but the not the other constituents of VALID_CHARS.
	//
	private static final String VALID_CHAR_SYMBOLS = "_.-+";
	private static final String VALID_CHAR_NUMBERS = "0123456789";
	private static final String   VALID_CHAR_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String   VALID_CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String        VALID_CHARS = VALID_CHAR_SYMBOLS + VALID_CHAR_NUMBERS + VALID_CHAR_UPPER + VALID_CHAR_LOWER;

	private static final int R = VALID_CHARS.length();

	private Node root;
	private int n;          // number of keys in trie

	private static class Node
	{
		private Object val;
		private Node[] next = new Node[R];

		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder(256);
			sb.append("Node -> ");
			sb.append(val);
			sb.append("\n");
			for (int i = 0; i < R; i++)
			{
				Node child = next[i];
				if (child == null)
				{
					continue;
				}

				char c = VALID_CHARS.charAt(i);
				sb.append("\t");
				sb.append(i);
				sb.append(":");
				sb.append(c);
				sb.append(" -> ");
				sb.append(child);
				sb.append("\n");
			}

			return sb.toString();
		}
	}

	public Trie() {}

	@SuppressWarnings("unchecked")
	public Value get(String key)
	{
		Objects.requireNonNull(key);

		Node x = get(root, key, 0);
		if (x == null)
		{
			return null;
		}

		return (Value) x.val;
	}

	public boolean contains(String key)
	{
		Objects.requireNonNull(key);
		return get(key) != null;
	}

	private Node get(Node x, String key, int d)
	{
		if (x == null)
		{
			return null;
		}

		if (d == key.length())
		{
			return x;
		}

		char c = key.charAt(d);
		int index = indexOfCharacter(c);
		return get(x.next[index], key, d + 1);
	}

	public void put(String key, Value val) {
		Objects.requireNonNull(key);

		if (val == null)
		{
			delete(key);
		}
		else
		{
			root = put(root, key, val, 0);
		}
	}

	private Node put(Node x, String key, Value v, int d)
	{
		if (x == null)
		{
			x = new Node();
		}

		if (d == key.length())
		{
			if (x.val == null)
			{
				n++;
			}
			x.val = v;
			return x;
		}

		char c = key.charAt(d);
		int index = indexOfCharacter(c);
		x.next[index] = put(x.next[index], key, v, d + 1);
		return x;
	}

	private static int indexOfCharacter(char c)
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

	public int size()
	{
		return n;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}

	public Iterable<String> keysWithPrefix(String prefix)
	{
		Queue<String> results = new ArrayDeque<String>();
		Node x = get(root, prefix, 0);
		StringBuilder sb = new StringBuilder(prefix);
		collect(x, sb, results);
		return results;
	}

	/**
	 * Returns all of the keys in the symbol table that match {@code pattern},
	 * where . symbol is treated as a wildcard character.
	 * @param pattern the pattern
	 * @return all of the keys in the symbol table that match {@code pattern},
	 *     as an iterable, where . is treated as a wildcard character.
	 */
	public Iterable<String> keysThatMatch(String pattern)
	{
		Queue<String> results = new ArrayDeque<String>();
		collect(root, new StringBuilder(), pattern, results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, Queue<String> results)
	{
		if (x == null)
		{
			return;
		}
		if (x.val != null)
		{
			String s = prefix.toString();
			results.add(s);
		}

		for (int i = 0; i < R; i++)
		{
			char c = VALID_CHARS.charAt(i);
			prefix.append(c);
			collect(x.next[i], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results)
	{
		if (x == null)
		{
			return;
		}

		int d = prefix.length();
		if (d == pattern.length() && x.val != null)
		{
			String s = prefix.toString();
			results.add(s);
		}

		if (d == pattern.length())
		{
			return;
		}

		char c = pattern.charAt(d);
		if (c == '.')
		{
			for (int i = 0; i < R; i++)
			{
				char ch = VALID_CHARS.charAt(i);
				prefix.append(ch);
				collect(x.next[i], prefix, pattern, results);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
		else
		{
			prefix.append(c);
			collect(x.next[c], prefix, pattern, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

	public String longestPrefixOf(String query)
	{
		Objects.requireNonNull(query);

		int length = longestPrefixOf(root, query, 0, -1);
		if (length == -1)
		{
			return null;
		}
		else
		{
			return query.substring(0, length);
		}
	}

	// returns the length of the longest string key in the subtrie
	// rooted at x that is a prefix of the query string,
	// assuming the first d character match and we have already
	// found a prefix match of given length (-1 if no such match)
	private int longestPrefixOf(Node x, String query, int d, int length)
	{
		if (x == null)
		{
			return length;
		}
		if (x.val != null)
		{
			length = d;
		}
		if (d == query.length())
		{
			return length;
		}
		char c = query.charAt(d);
		return longestPrefixOf(x.next[c], query, d + 1, length);
	}

	public void delete(String key)
	{
		Objects.requireNonNull(key);

		root = delete(root, key, 0);
	}

	private Node delete(Node x, String key, int d)
	{
		if (x == null)
		{
			return null;
		}

		if (d == key.length())
		{
			if (x.val != null)
			{
				n--;
			}
			x.val = null;
		}
		else
		{
			char c = key.charAt(d);
			int index = indexOfCharacter(c);
			x.next[index] = delete(x.next[index], key, d + 1);
		}

		// remove subtrie rooted at x if it is completely empty
		//
		if (x.val != null)
		{
			return x;
		}

		for (int c = 0; c < R; c++)
		{
			if (x.next[c] != null)
			{
				return x;
			}
		}

		return null;
	}
}