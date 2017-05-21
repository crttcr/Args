package com.xivvic.args.util.trie;

import static com.xivvic.args.util.trie.Constants.R;
import static com.xivvic.args.util.trie.Constants.VALID_CHARS;
import static com.xivvic.args.util.trie.Constants.indexOfCharacter;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * String -> V
 *
 * Setting a value for a key replaces any previous value for that key.
 * Values cannot be null. Setting a value to null removes any associated key.
 *
 */
public class Trie<V>
{
	private int n;       // The number of keys stored.
	private Node root;

	public int size()
	{
		return n;
	}

	public boolean isEmpty()
	{
		return n == 0;
	}

	public boolean contains(String key)
	{
		Objects.requireNonNull(key);
		return get(key) != null;
	}

	public void put(String key, V val) {
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

	private Node put(Node x, String key, V v, int d)
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

	@SuppressWarnings("unchecked")
	public V get(String key)
	{
		Objects.requireNonNull(key);

		Node x = get(root, key, 0);
		if (x == null)
		{
			return null;
		}

		return (V) x.val;
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
	
		if (x.val != null)
		{
			return x;
		}
	
		// remove subtrie rooted at x if it is completely empty
		//
		for (int c = 0; c < R; c++)
		{
			if (x.next[c] != null)
			{
				return x;
			}
		}
	
		return null;
	}

	public Iterable<String> keys()
	{
		return keysWithPrefix("");
	}

	public Set<String> keySet()
	{
		Set<String> set = new HashSet<>();
		Iterator<String> it = keys().iterator();
		it.forEachRemaining(set::add);

		return set;
	}

	public Iterable<String> keysWithPrefix(String prefix)
	{
		StringBuilder sb = new StringBuilder(prefix);
		Queue<String> results = new ArrayDeque<String>();

		Node x = get(root, prefix, 0);
		collect(x, sb, results);
		return results;
	}

	/**
	 * Returns keys that match {@code pattern},
	 * where '.' is treated as a single wildcard character.
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
}