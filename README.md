# Args - Simple, overachieving option/argument processing

This is yet another library for processing program arguments. Inspired by Robert C. Martin's 
[Clean Code](http://www.amazon.com/Clean-Code-Handbook-Software-Craftmanship/dp/0132350882"),
chapter 14, this library keeps the simple **one-character \[+ optional modifier]** specification for cases
where advanced capabilities are overkill. Additionally, by providing a more detailed specification,
it's possible to provide descriptions, default values, environment variable lookup, and validation
to ensure programs are started in a consistent state.


## Getting Started

To use this code, simply add the source code or jar file to your project, describe the program 
options, and pass the program arguments to the Args constructor. If the constructor does not
throw an ArgsException error, then the instance can be used to access program arguments by
name. Here is the simple usage example that does not use advanced the features.

```
   public static void main(String[] args)
   {
      try
      {
         Args        arg = new Args("s*,p#,v", args);

         String   server = arg.getValue("s");
         Integer    port = arg.getValue("p");
         Boolean verbose = arg.getValue("v");

         run(path, port, logging);
      }
      catch (ArgsException e)
      {
         System.out.printf("Argument error: %s\n", e.errorMessage());
      }
   }

```

### Prerequisites

None, other than adding the source or jar to your project.

## Advanced Usage

This library supports a second form of specifying the options that are available to a
program, referred to as the 'long form.'  To use the long form, provide a string containing
extended option definition properties, generally from a file, to the Args constructor.

Advanced features include long name options prefixed with -- in addition to short options
prefixed with -, default values, validations, extracting values from environment
variables, and descriptions that can be used to provide help text. An example 'long form'
definition is shown below and described in more detail in 
the Schema [README](src/main/java/com/xivvic/args/schema/README.md)

```
[latitude]
name=latitude
description="Longitude coordinate"
type=DOUBLE
default=100.04
```

Note, that the schema parser currently requires values containing whitespace to be enclosed within quotes.


## Error Strategy
This libary currently supports 3 error handling strategies:

* FAIL_FAST: Fail as soon as an error is encountered
* FAIL_SLOW: Fail after all processing is done, reporting all errors
* WARN_AND_IGNORE: Provide a warning but let the program run despite the error

The default strategy for Schema definition is FAIL_SLOW, and the default for argument
processing is FAIL_FAST.

## Running the tests

The tests that come with the library source can be run via [JUnit](http://http://junit.org/junit4)

## Built With

* [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) 
* [Lombok](https://projectlombok.org/) - Taking some of the pain out of Java

## Contributing


## Authors

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Robert C. Martin for 
[Clean Code](http://www.amazon.com/Clean-Code-Handbook-Software-Craftmanship/dp/0132350882")
and chapter 14 in particular.
