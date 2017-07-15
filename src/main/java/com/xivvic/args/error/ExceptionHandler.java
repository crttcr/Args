package com.xivvic.args.error;

public class ExceptionHandler
{
	private enum TerminationStrategy
	{
		TERMINATE,
		THROW_EXCEPTION,
		IGNORE,
	}

	private final ArgsException ex;
	private final TerminationStrategy ts;
	private ExceptionHandler(ArgsException e, TerminationStrategy ts)
	{
		this.ex = e;
		this.ts = ts;
	}

	public static ExceptionHandler terminatingHandler(ArgsException e)
	{
		ExceptionHandler handler = new ExceptionHandler(e, TerminationStrategy.TERMINATE);

		return handler;
	}

	public static ExceptionHandler throwingHandler(ArgsException e)
	{
		ExceptionHandler handler = new ExceptionHandler(e, TerminationStrategy.THROW_EXCEPTION);

		return handler;
	}

	public static ExceptionHandler ignoringHandler(ArgsException e)
	{
		ExceptionHandler handler = new ExceptionHandler(e, TerminationStrategy.IGNORE);

		return handler;
	}

	public void exit()
	{
		System.out.println("Handler with exception: " + ex.errorMessage());
		System.out.println("Exiting ...");
		System.exit(0);
	}

	public void handle() throws ArgsException
	{
		switch (ts)
		{
		case TERMINATE:
			exit();
			break;
		case THROW_EXCEPTION:
			System.out.println("Throwing exception ...");
			throw ex;
		case IGNORE:
			System.out.println("Ignoring exception ...");
			break;
		}
	}

}
