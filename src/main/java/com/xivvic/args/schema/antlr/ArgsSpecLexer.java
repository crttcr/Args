// Generated from ArgsSpec.g4 by ANTLR 4.7

package com.xivvic.args.schema.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ArgsSpecLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NAME=1, STRING=2, LINE_COMMENT=3, LINE_ESCAPE=4, WS=5, NL=6, LBRACK=7, 
		RBRACK=8, COLON=9, EQUAL=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"NAME", "STRING", "LINE_COMMENT", "LINE_ESCAPE", "WS", "NL", "LBRACK", 
		"RBRACK", "COLON", "EQUAL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, "'['", "']'", "':'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NAME", "STRING", "LINE_COMMENT", "LINE_ESCAPE", "WS", "NL", "LBRACK", 
		"RBRACK", "COLON", "EQUAL"
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


	public ArgsSpecLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ArgsSpec.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\fY\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\6\2\31\n\2\r\2\16\2\32\3\3\3\3\3\3\3\3\3\3\3\3\7\3#\n\3\f\3\16"+
		"\3&\13\3\3\3\3\3\3\4\3\4\3\4\5\4-\n\4\3\4\7\4\60\n\4\f\4\16\4\63\13\4"+
		"\3\4\5\4\66\n\4\3\4\3\4\3\4\3\4\3\5\3\5\5\5>\n\5\3\5\3\5\3\5\3\5\3\6\6"+
		"\6E\n\6\r\6\16\6F\3\6\3\6\3\7\5\7L\n\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\61\2\f\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\3\2\5\b\2--/\60\62;C\\aac|\4\2$$^^\4\2\13\13\"\"\2b\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\30\3\2\2\2\5\34\3\2\2\2\7,\3"+
		"\2\2\2\t;\3\2\2\2\13D\3\2\2\2\rK\3\2\2\2\17Q\3\2\2\2\21S\3\2\2\2\23U\3"+
		"\2\2\2\25W\3\2\2\2\27\31\t\2\2\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2"+
		"\2\2\32\33\3\2\2\2\33\4\3\2\2\2\34$\7$\2\2\35\36\7^\2\2\36#\7$\2\2\37"+
		" \7^\2\2 #\7^\2\2!#\n\3\2\2\"\35\3\2\2\2\"\37\3\2\2\2\"!\3\2\2\2#&\3\2"+
		"\2\2$\"\3\2\2\2$%\3\2\2\2%\'\3\2\2\2&$\3\2\2\2\'(\7$\2\2(\6\3\2\2\2)-"+
		"\7%\2\2*+\7\61\2\2+-\7\61\2\2,)\3\2\2\2,*\3\2\2\2-\61\3\2\2\2.\60\13\2"+
		"\2\2/.\3\2\2\2\60\63\3\2\2\2\61\62\3\2\2\2\61/\3\2\2\2\62\65\3\2\2\2\63"+
		"\61\3\2\2\2\64\66\7\17\2\2\65\64\3\2\2\2\65\66\3\2\2\2\66\67\3\2\2\2\67"+
		"8\7\f\2\289\3\2\2\29:\b\4\2\2:\b\3\2\2\2;=\7^\2\2<>\7\17\2\2=<\3\2\2\2"+
		"=>\3\2\2\2>?\3\2\2\2?@\7\f\2\2@A\3\2\2\2AB\b\5\2\2B\n\3\2\2\2CE\t\4\2"+
		"\2DC\3\2\2\2EF\3\2\2\2FD\3\2\2\2FG\3\2\2\2GH\3\2\2\2HI\b\6\2\2I\f\3\2"+
		"\2\2JL\7\17\2\2KJ\3\2\2\2KL\3\2\2\2LM\3\2\2\2MN\7\f\2\2NO\3\2\2\2OP\b"+
		"\7\2\2P\16\3\2\2\2QR\7]\2\2R\20\3\2\2\2ST\7_\2\2T\22\3\2\2\2UV\7<\2\2"+
		"V\24\3\2\2\2WX\7?\2\2X\26\3\2\2\2\f\2\32\"$,\61\65=FK\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}