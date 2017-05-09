package com.xivvic.args.util;

import static com.xivvic.args.util.PropertyFileParser.COMMENT;
import static com.xivvic.args.util.PropertyFileParser.LEFT_CONTEXT;
import static com.xivvic.args.util.PropertyFileParser.RIGHT_CONTEXT;
/**
 * Enumeration of different types of lines with static methods
 * to decide classifications.
 *
 * Note, any line presented for classification that contains an embedded newline
 * character will be considered to be malformed.
 *
 * @author reid.dev
 *
 */
public enum LineClassification {
	Null,
	Blank,
	Comment,
	SectionHeader,
	PropertyDefinition,
	Malformed,
	Undefined,
	;

	public static LineClassification classify(String line) {
		if (line == null)
		{
			return Null;
		}

		if (line.contains("\n"))
		{
			return Malformed;
		}


		String trim = line.trim();

		if (trim.length() == 0)
		{
			return Blank;
		}

		if (isSectionHeader(trim))
		{
			return SectionHeader;
		}

		if (isComment(trim))
		{
			return Comment;
		}

		if (isPropertyDefinition(trim))
		{
			return PropertyDefinition;
		}

		return Undefined;
	}

	private static boolean isPropertyDefinition(String line) {
		int p1 = line.indexOf('=');
		if (p1 > 0)
		{
			return  true;
		}

		int p2 = line.indexOf(':');
		if (p2 > 0)
		{
			return true;
		}

		return false;
	}

	private static boolean isComment(String line) {
		return line.startsWith(COMMENT);
	}

	private static boolean isSectionHeader(String line) {
		line = line.trim();
		return line.startsWith(LEFT_CONTEXT) && line.endsWith(RIGHT_CONTEXT);
	}

	//	public boolean isNamedContext(String line, String context) {
	//		return context != null && line.trim().equalsIgnoreCase(LEFT_CONTEXT + context + RIGHT_CONTEXT);
	//	}
	//
}
