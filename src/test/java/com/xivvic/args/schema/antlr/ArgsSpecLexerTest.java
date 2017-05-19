package com.xivvic.args.schema.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.junit.Before;
import org.junit.Test;

public class ArgsSpecLexerTest
{
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testVocabulary()
	{
		// Arrange
		//
		CharStream input = CharStreams.fromString("[dog]\na=b //okay");
		ArgsSpecLexer lexer = new ArgsSpecLexer(input);
		Vocabulary v = lexer.getVocabulary();
		int max = v.getMaxTokenType();
		Set<String> symbols = new HashSet<>();

		// Act
		for (int i = 0; i <= max; i++)
		{
			String s = v.getSymbolicName(i);
			symbols.add(s);
		}

		// Assert
		//
		assertTrue(symbols.contains("NAME"));
		assertTrue(symbols.contains("STRING"));
		assertTrue(symbols.contains("EQUAL"));
	}

	@Test
	public void testGetRuleNames()
	{
		// Arrange
		//
		CharStream input = CharStreams.fromString("[dog]\na=b //okay");
		ArgsSpecLexer lexer = new ArgsSpecLexer(input);

		// Act
		//
		String[] rules = lexer.getRuleNames();

		// Assert
		//
		assertNotNull(rules);
	}

	@Test
	public void testSingleItemTokens()
	{
		// Arrange
		//
		String input = "[dog]";
		CharStream cs = CharStreams.fromString(input);
		ArgsSpecLexer lexer = new ArgsSpecLexer(cs);
		CommonTokenStream cts = new CommonTokenStream(lexer);
		cts.fill();
		List<Token> tokens = cts.getTokens();

		// Act
		//
		for (Token token : tokens)
		{
			System.out.println(token);
		}

		// Assert
		//
		assertEquals(4,       tokens.size());
		assertEquals("[",     tokens.get(0).getText());
		assertEquals("dog",   tokens.get(1).getText());
		assertEquals("]",     tokens.get(2).getText());
		assertEquals("<EOF>", tokens.get(3).getText());
	}


}
