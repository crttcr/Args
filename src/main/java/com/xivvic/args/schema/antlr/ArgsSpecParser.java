// Generated from ArgsSpec.g4 by ANTLR 4.7

package com.xivvic.args.schema.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ArgsSpecParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COLON=1, EQUALS=2, OPEN_BRACKET=3, CLOSE_BRACKET=4, TEXT=5, WS=6, LINE_COMMENT=7;
	public static final int
		RULE_spec = 0, RULE_item = 1, RULE_item_header = 2, RULE_item_name = 3, 
		RULE_key_value = 4, RULE_key = 5, RULE_value = 6, RULE_text = 7;
	public static final String[] ruleNames = {
		"spec", "item", "item_header", "item_name", "key_value", "key", "value", 
		"text"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "':'", "'='", "'['", "']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "COLON", "EQUALS", "OPEN_BRACKET", "CLOSE_BRACKET", "TEXT", "WS", 
		"LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ArgsSpec.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ArgsSpecParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SpecContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ArgsSpecParser.EOF, 0); }
		public List<TerminalNode> LINE_COMMENT() { return getTokens(ArgsSpecParser.LINE_COMMENT); }
		public TerminalNode LINE_COMMENT(int i) {
			return getToken(ArgsSpecParser.LINE_COMMENT, i);
		}
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public SpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitSpec(this);
		}
	}

	public final SpecContext spec() throws RecognitionException {
		SpecContext _localctx = new SpecContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_spec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OPEN_BRACKET || _la==LINE_COMMENT) {
				{
				setState(18);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LINE_COMMENT:
					{
					setState(16);
					match(LINE_COMMENT);
					}
					break;
				case OPEN_BRACKET:
					{
					setState(17);
					item();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ItemContext extends ParserRuleContext {
		public Item_headerContext item_header() {
			return getRuleContext(Item_headerContext.class,0);
		}
		public List<TerminalNode> LINE_COMMENT() { return getTokens(ArgsSpecParser.LINE_COMMENT); }
		public TerminalNode LINE_COMMENT(int i) {
			return getToken(ArgsSpecParser.LINE_COMMENT, i);
		}
		public List<Key_valueContext> key_value() {
			return getRuleContexts(Key_valueContext.class);
		}
		public Key_valueContext key_value(int i) {
			return getRuleContext(Key_valueContext.class,i);
		}
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitItem(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_item);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			item_header();
			setState(30);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(28);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case LINE_COMMENT:
						{
						setState(26);
						match(LINE_COMMENT);
						}
						break;
					case TEXT:
						{
						setState(27);
						key_value();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(32);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Item_headerContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKET() { return getToken(ArgsSpecParser.OPEN_BRACKET, 0); }
		public Item_nameContext item_name() {
			return getRuleContext(Item_nameContext.class,0);
		}
		public TerminalNode CLOSE_BRACKET() { return getToken(ArgsSpecParser.CLOSE_BRACKET, 0); }
		public Item_headerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterItem_header(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitItem_header(this);
		}
	}

	public final Item_headerContext item_header() throws RecognitionException {
		Item_headerContext _localctx = new Item_headerContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_item_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(OPEN_BRACKET);
			setState(34);
			item_name();
			setState(35);
			match(CLOSE_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Item_nameContext extends ParserRuleContext {
		public TextContext text() {
			return getRuleContext(TextContext.class,0);
		}
		public Item_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterItem_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitItem_name(this);
		}
	}

	public final Item_nameContext item_name() throws RecognitionException {
		Item_nameContext _localctx = new Item_nameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_item_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			text();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Key_valueContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public TerminalNode COLON() { return getToken(ArgsSpecParser.COLON, 0); }
		public TerminalNode EQUALS() { return getToken(ArgsSpecParser.EQUALS, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public Key_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterKey_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitKey_value(this);
		}
	}

	public final Key_valueContext key_value() throws RecognitionException {
		Key_valueContext _localctx = new Key_valueContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_key_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			key();
			setState(40);
			_la = _input.LA(1);
			if ( !(_la==COLON || _la==EQUALS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(42);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(41);
				value();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyContext extends ParserRuleContext {
		public TextContext text() {
			return getRuleContext(TextContext.class,0);
		}
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitKey(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			text();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TextContext text() {
			return getRuleContext(TextContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			text();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TextContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(ArgsSpecParser.TEXT, 0); }
		public TextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).enterText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ArgsSpecListener ) ((ArgsSpecListener)listener).exitText(this);
		}
	}

	public final TextContext text() throws RecognitionException {
		TextContext _localctx = new TextContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\t\65\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\7\2\25\n"+
		"\2\f\2\16\2\30\13\2\3\2\3\2\3\3\3\3\3\3\7\3\37\n\3\f\3\16\3\"\13\3\3\4"+
		"\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\5\6-\n\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t"+
		"\2\2\n\2\4\6\b\n\f\16\20\2\3\3\2\3\4\2\61\2\26\3\2\2\2\4\33\3\2\2\2\6"+
		"#\3\2\2\2\b\'\3\2\2\2\n)\3\2\2\2\f.\3\2\2\2\16\60\3\2\2\2\20\62\3\2\2"+
		"\2\22\25\7\t\2\2\23\25\5\4\3\2\24\22\3\2\2\2\24\23\3\2\2\2\25\30\3\2\2"+
		"\2\26\24\3\2\2\2\26\27\3\2\2\2\27\31\3\2\2\2\30\26\3\2\2\2\31\32\7\2\2"+
		"\3\32\3\3\2\2\2\33 \5\6\4\2\34\37\7\t\2\2\35\37\5\n\6\2\36\34\3\2\2\2"+
		"\36\35\3\2\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\5\3\2\2\2\" \3\2\2"+
		"\2#$\7\5\2\2$%\5\b\5\2%&\7\6\2\2&\7\3\2\2\2\'(\5\20\t\2(\t\3\2\2\2)*\5"+
		"\f\7\2*,\t\2\2\2+-\5\16\b\2,+\3\2\2\2,-\3\2\2\2-\13\3\2\2\2./\5\20\t\2"+
		"/\r\3\2\2\2\60\61\5\20\t\2\61\17\3\2\2\2\62\63\7\7\2\2\63\21\3\2\2\2\7"+
		"\24\26\36 ,";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}