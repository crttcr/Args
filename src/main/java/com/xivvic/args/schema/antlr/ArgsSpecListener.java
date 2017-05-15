// Generated from ArgsSpec.g4 by ANTLR 4.7

package com.xivvic.args.schema.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArgsSpecParser}.
 */
public interface ArgsSpecListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#spec}.
	 * @param ctx the parse tree
	 */
	void enterSpec(ArgsSpecParser.SpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#spec}.
	 * @param ctx the parse tree
	 */
	void exitSpec(ArgsSpecParser.SpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(ArgsSpecParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(ArgsSpecParser.ItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#item_header}.
	 * @param ctx the parse tree
	 */
	void enterItem_header(ArgsSpecParser.Item_headerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#item_header}.
	 * @param ctx the parse tree
	 */
	void exitItem_header(ArgsSpecParser.Item_headerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#item_name}.
	 * @param ctx the parse tree
	 */
	void enterItem_name(ArgsSpecParser.Item_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#item_name}.
	 * @param ctx the parse tree
	 */
	void exitItem_name(ArgsSpecParser.Item_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#key_value}.
	 * @param ctx the parse tree
	 */
	void enterKey_value(ArgsSpecParser.Key_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#key_value}.
	 * @param ctx the parse tree
	 */
	void exitKey_value(ArgsSpecParser.Key_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(ArgsSpecParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(ArgsSpecParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ArgsSpecParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ArgsSpecParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#text}.
	 * @param ctx the parse tree
	 */
	void enterText(ArgsSpecParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#text}.
	 * @param ctx the parse tree
	 */
	void exitText(ArgsSpecParser.TextContext ctx);
}