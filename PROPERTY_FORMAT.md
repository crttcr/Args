# Option Specification Format

Options are specified in either the short format or the long format.
The short format is designed to be quick and simple, allowing programs
access to command line arguments with minimal ceremony. The long format 
requires more specification effort but in return opens up additional 
features for automatically handling arguments.

## Short Format
The short format is a single string consisting of one character option names
separated by commas. Option types are designated by modifiers following the
option name character.  Here is a short form specification for three options,
of type STRING, INTEGER, and BOOLEAN: `"s*,p#,v"`.

The option types and their modifiers available in the short form specification
are as follows:

```
  - BOOLEAN
* - STRING
# - INTEGER
## - DOUBLE
[*] - STRING_LIST
```

The short form specification must not contain a newline character or it will
be treated as a long form specification.

## Long Format

The long format is similar to the INI file format where properties are organized
into named sections.

###
The rules for the format

Each option specification starts with a property name in brackets. 
White space is not meaningful adjacent to the bracket characters, so the 
following are identical property name designators for a property "verbose". 

```
[verbose]
[verbose ]
[ verbose ] 
  [ verbose ] 
```


Option names as well as property names follow these rules:

* The cannot contain spaces
* They are not quoted
* They can consist of these characters `_.-+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`


After the option name, individual property definitions are a sequence of name value pairs following
the option name. Property names are separated from values by either `'='` or `':'`.
Whitespace around the separator is not significant and is ignored.

```
[port]
type=INTEGER
required = true
dv=8080
ev=MY_SERVICE_PORT
description="The port on which the server listens for incoming connections"
```

Property values with spaces need to be quoted.
White space at the beginning of the line is ignored as is any whitespace
appearing between property name and property value, so the following are equivalent,

```
required=true
required =true
required = true
required      :    true
```

Line comments start with `//` or `'#'` and are ignored.