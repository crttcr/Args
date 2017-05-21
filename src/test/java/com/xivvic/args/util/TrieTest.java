package com.xivvic.args.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TrieTest
{
	Trie<String> subject;

	@Before
	public void setUp() throws Exception
	{
		subject = new Trie<String>();
	}

	@Test
	public void testConstruction()
	{
		assertNotNull(subject);
		assertEquals(0, subject.size());
		assertTrue(subject.isEmpty());
	}

	@Test
	public void testGet()
	{
		// Arrange
		//
		String k = "key";
		String v = "value";
		subject.put(k, v);

		// Act
		//
		String s = subject.get(k);

		// Assert
		//
		assertNotNull(s);
		assertEquals(v, s);
	}

	@Test
	public void testContains()
	{
		// Arrange
		//
		String k = "key";
		String v = "value";
		subject.put(k, v);

		// Act
		//
		boolean expected = subject.contains(k);
		boolean notExpected = subject.contains(v);

		// Assert
		//
		assertTrue(expected);
		assertFalse(notExpected);
	}

	@Test
	public void testSize()
	{
		// Arrange
		//
		String k1 = "key";
		String k2 = "ke";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);

		// Act
		//
		int size = subject.size();


		// Assert
		//
		assertEquals(2, size);
	}

	@Test
	public void testIsEmpty()
	{
		// Arrange
		//
		String k1 = "key";
		String k2 = "ke";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);

		// Act
		//
		boolean b1 = subject.isEmpty();
		subject.delete(k1);
		boolean b2 = subject.isEmpty();
		subject.put(k2, null);
		boolean b3 = subject.isEmpty();


		// Assert
		//
		assertFalse(b1);
		assertFalse(b2);
		assertTrue(b3);
	}

	@Test
	public void testKeys()
	{
		// Arrange
		//
		String k1 = "___";
		String k2 = "__";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);
		Set<String> set = new HashSet<>();

		// Act
		//
		Iterator<String> it = subject.keys().iterator();
		it.forEachRemaining(set::add);

		// Assert
		//
		assertTrue(set.contains(k1));
		assertTrue(set.contains(k2));
		assertFalse(set.contains(v1));
	}

	@Test
	public void testNumericKeys()
	{
		// Arrange
		//
		String k1 = "0";
		String k2 = "01";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);
		Set<String> set = new HashSet<>();

		// Act
		//
		Iterator<String> it = subject.keys().iterator();
		it.forEachRemaining(set::add);

		// Assert
		//
		assertTrue(set.contains(k1));
		assertTrue(set.contains(k2));
		assertFalse(set.contains(v1));
	}

	@Test
	public void testUppercaseKeys()
	{
		// Arrange
		//
		String k1 = "C";
		String k2 = "VIXEN";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);
		Set<String> set = new HashSet<>();

		// Act
		//
		Iterator<String> it = subject.keys().iterator();
		it.forEachRemaining(set::add);

		// Assert
		//
		assertTrue(set.contains(k1));
		assertTrue(set.contains(k2));
		assertFalse(set.contains(v1));
	}

	@Test
	public void testLowercaseKeys()
	{
		// Arrange
		//
		String k1 = "a";
		String k2 = "ax";
		String v1 = "v1";
		String v2 = "v2";
		subject.put(k1, v1);
		subject.put(k2, v2);
		Set<String> set = new HashSet<>();

		// Act
		//
		Iterator<String> it = subject.keys().iterator();
		it.forEachRemaining(set::add);

		// Assert
		//
		assertTrue(set.contains(k1));
		assertTrue(set.contains(k2));
		assertFalse(set.contains(v1));
	}

	@Test
	public void testKeysWithPrefix()
	{
		// Arrange
		//
		String prefix = "..";
		String k1 = "..";
		String k2 = "..+";
		String k3 = "._.";
		String k4 = "...";
		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";
		subject.put(k1, v1);
		subject.put(k2, v2);
		subject.put(k3, v3);
		subject.put(k4, v4);
		Set<String> set = new HashSet<>();

		// Act
		//
		Iterator<String> it = subject.keysWithPrefix(prefix).iterator();
		it.forEachRemaining(set::add);

		// Assert
		//
		assertTrue(set.contains(k1));
		assertTrue(set.contains(k2));
		assertFalse(set.contains(k3));
		assertTrue(set.contains(k4));
	}

}
