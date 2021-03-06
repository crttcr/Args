package com.xivvic.args.schema;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xivvic.args.error.ErrorStrategy;
import com.xivvic.args.error.SchemaException;
import com.xivvic.args.schema.Schema;
import com.xivvic.args.schema.SchemaBuilder;

public class SchemaBuilderEmptyTest
{
	private SchemaBuilder subject;

	@Test(expected=SchemaException.class)
	public void testBuildEmptyThrowsWhenFailFast() throws Exception
	{
		subject = new SchemaBuilder(ErrorStrategy.FAIL_FAST);
		subject.build();
	}

	@Test(expected=SchemaException.class)
	public void testBuildEmptyThrowsWhenFailSlow() throws Exception
	{
		subject = new SchemaBuilder(ErrorStrategy.FAIL_SLOW);
		subject.build();
	}

	@Test
	public void testBuildEmptyDoesNotThrowWhenWarnAndIgnore() throws Exception
	{
		// Arrange
		//
		subject = new SchemaBuilder(ErrorStrategy.WARN_AND_IGNORE);

		// Act
		//
		Schema schema = subject.build();

		// Assert
		//
		assertNotNull(schema);
	}


}
