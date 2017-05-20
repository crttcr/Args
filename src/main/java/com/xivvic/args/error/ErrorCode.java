package com.xivvic.args.error;

// NOTE: When adding a new ENUM, add an appropriate case to the switch // statement in the @{link errorText()} method.
//
public enum ErrorCode
{
	// Common Codes
	//
	OK("Error code says 'OK' but errorMessage called."),
	COMPOSITE_ERROR("Composite errors"),

	// Schema Codes
	//
	NO_SCHEMA("Argument processing requires a schema, but none was provided."),
	EMPTY_SCHEMA("Argument processing requires a schema, however an empty one was provided."),
	INVALID_SCHEMA("Schema definition not valid"),
	INVALID_SCHEMA_ELEMENT("Schema element [%s] not valid: [%s]."),
	SCHEMA_MISSING_OPTION_NAME("Options require a name. One was not provided."),
	SCHEMA_MISSING_OPTION_TYPE("Options require a type. One was not provided for %s"),
	INVALID_DEFAULT_VALUE("Default value [%s] not valid for option"),


	// Argument Processing Codes
	//
	NULL_ARGUMENT_ARRAY("Argument processing failed because a null value was provided as the argument list instead of an empty array."),
	UNEXPECTED_OPTION("Argument -%s unexpected. Not in schema definition."),
	MISSING_STRING("Could not find string parameter for option %s."),
	MISSING_STRING_LIST("Could not find string parameters for option %s."),
	MISSING_INTEGER("Could not find integer parameter for option %s"),
	MISSING_DOUBLE("Could not find double parameter for option %s"),
	MISSING_DATE("Could not find date parameter for option %s"),
	MISSING_TIME("Could not find time parameter for option %s"),
	MISSING_PATH("Could not find path parameter for option %s"),
	MISSING_FILE("Could not find file parameter for option %s"),
	MISSING_ENVIRONMENT_VARIABLE("Environment variable [%s] does not have an established value to define option [%s]."),
	MISSING_OPTION_NAME("Options require a name. So staring with - or -- must be followed by a character, not blank"),

	INVALID_ARGUMENT_FORMAT("'%s' is not a valid argument format."),
	INVALID_ARGUMENT_NAME("'%s' is not a valid argument name."),
	INVALID_INTEGER("Argument -%s expects an integer, but was '%s'."),
	INVALID_DOUBLE("Argument -%s expects a double, but was '%s'."),
	INVALID_DATE("Argument -%s expects a date, but '%s' failed to parse."),
	INVALID_TIME("Argument -%s expects a time, but '%s' failed to parse."),
	INVALID_PATH("Argument -%s expects a path, but '%s' is not valid."),
	INVALID_FILE("Argument -%s expects a file, but '%s' was empty."),
	;


	private String fmt;
	ErrorCode(String fmt) {
		this.fmt = fmt;
	}

	public String errorText(String option, String param)
	{
		switch(this)
		{
		case OK:
		case COMPOSITE_ERROR:
		case NULL_ARGUMENT_ARRAY:
		case MISSING_OPTION_NAME:
		case NO_SCHEMA:
		case EMPTY_SCHEMA:
		case INVALID_SCHEMA:
		case SCHEMA_MISSING_OPTION_NAME:
			return format();
		case SCHEMA_MISSING_OPTION_TYPE:
		case MISSING_STRING:
		case MISSING_STRING_LIST:
		case MISSING_INTEGER:
		case MISSING_DOUBLE:
		case MISSING_DATE:
		case MISSING_TIME:
		case MISSING_PATH:
		case MISSING_FILE:
		case INVALID_ARGUMENT_NAME:
		case UNEXPECTED_OPTION:
			return format(option);
		case INVALID_INTEGER:
		case INVALID_DOUBLE:
		case INVALID_DATE:
		case INVALID_TIME:
		case INVALID_PATH:
		case INVALID_FILE:
		case INVALID_SCHEMA_ELEMENT:
			return format(option, param);
		case INVALID_ARGUMENT_FORMAT:
		case INVALID_DEFAULT_VALUE:
			return format(param);
		case MISSING_ENVIRONMENT_VARIABLE:
			return format(param, option);
		}

		String fmt = "Programmer error. The format routine does not handle error code %s";
		String msg = String.format(fmt, this);
		return msg;
	}

	private String format()
	{
		return fmt;
	}

	private String format(String s)
	{
		return String.format(fmt, s);
	}

	private String format(String option, String param)
	{
		return String.format(fmt, option, param);
	}
}
