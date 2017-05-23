package com.xivvic.args.util.trie;

import static com.xivvic.args.util.trie.Constants.DIM;
import static com.xivvic.args.util.trie.Constants.VALID_CHARS;
import static com.xivvic.args.util.trie.Constants.indexOfCharacter;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * Setting a value for a key replaces any previous value for that key.
 * Values cannot be null. Setting a value to null removes any associated key.
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
		for (int c = 0; c < DIM; c++)
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

	public Set<V> valueSet()
	{
		Set<V> rv = new HashSet<>();
		Iterator<String> it = keys().iterator();

		while (it.hasNext())
		{
			String k = it.next();
			V v = get(k);
			rv.add(v);
		}

		return rv;
	}

	public Iterable<String> keysWithPrefix(String prefix)
	{
		StringBuilder sb = new StringBuilder(prefix);
		Queue<String> results = new ArrayDeque<String>();

		Node x = get(root, prefix, 0);
		collect(x, sb, results);
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

		for (int i = 0; i < DIM; i++)
		{
			char c = VALID_CHARS.charAt(i);
			prefix.append(c);
			collect(x.next[i], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
	}

}