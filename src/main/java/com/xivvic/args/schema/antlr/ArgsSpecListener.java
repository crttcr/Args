// Generated from ArgsSpec.g4 by ANTLR 4.7

package com.xivvic.args.schema.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArgsSpecParser}.
 */
public interface ArgsSpecListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ArgsSpecParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ArgsSpecParser.StartContext ctx);
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
	 * Enter a parse tree produced by {@link ArgsSpecParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(ArgsSpecParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(ArgsSpecParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArgsSpecParser#name_value}.
	 * @param ctx the parse tree
	 */
	void enterName_value(ArgsSpecParser.Name_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArgsSpecParser#name_value}.
	 * @param ctx the parse tree
	 */
	void exitName_value(ArgsSpecParser.Name_valueContext ctx);
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
}