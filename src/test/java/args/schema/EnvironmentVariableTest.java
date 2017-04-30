package args.schema;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xivvic.args.Args;
import com.xivvic.args.TestUtil;

import args.error.ArgsException;

public class EnvironmentVariableTest
{
	@Test
	public void testHomeValueFromEnvironment() throws ArgsException
	{
		// Arrange
		//
		String[] arguments = {"a", "b"};
		String defs = TestUtil.readFromTestResourceFile("s.envvar.txt");
		Schema schema = new SchemaBuilder("schema.name").build(defs);

		// Act
		//
		Args args = new Args(schema, arguments);
		String home = args.getValue("home");

		// Assert
		//
		assertNotNull(home);
	}
}
