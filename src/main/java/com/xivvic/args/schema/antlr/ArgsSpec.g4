grammar ArgsSpec;

@header {
package com.xivvic.args.schema.antlr;
} 

spec: (LINE_COMMENT | item | key_value)* EOF;
item: item_header (LINE_COMMENT | key_value)*;
item_header: OPEN_BRACKET item_name CLOSE_BRACKET;
item_name: text;
key_value: key (COLON | EQUALS) value?;
key: text;
value: text;
text: TEXT;

// Lexical Items
//
COLON	         : ':' ;
EQUALS	      : '=' ;
OPEN_BRACKET	: '[' ;
CLOSE_BRACKET	: ']' ;

TEXT: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '/' | '\\' | ':' | '*' | '.' | ',' | '@' | ' ')+;
WS  :   (('\r')? '\n' |  ' ' | '\t')+  -> skip;
LINE_COMMENT : '#' ~('\n'|'\r')*  ->  channel(HIDDEN);

/*
//TEXT : ( ~('='|'\n') )*;
*/

