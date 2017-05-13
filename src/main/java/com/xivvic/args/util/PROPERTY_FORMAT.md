# Property File Format

Properties files read by the PropertyFileParsert follow a format
a format that is a combination of those recogniezed by the class java.util.Properties
and by Windows property files. Windows property files scoping to a property
definition, so that the property name does not need to appear in each 
property definition.

##
The rules for the format

Entries start with a property name in brackets. White space is not meaningful
adjacent to the bracket characters, so the following are identical property
name designators for a property "verbose". Any text after the first closing
bracket is ignored.

```
[verbose]
[verbose ]
[ verbose ] 
  [ verbose ] 
```

Once a property name is established, its field names and values are generally expected to be a 
single line of the form, one of the following:

```
propertyName=propertyValue
propertyName:propertyValue
```

White space at the beginning of the line is ignored as is any whitespace
appearing between property name and property value, so the following are equivalent,

```
name=Stephen
name = Stephen
  name :Stephen
```

Lines that start with the comment characters ! or # are ignored. 
Blank lines are also ignored.


The property value is generally terminated by the end of the line. 
White space following the property value is not ignored, and is treated as 
part of the property value.

Property values can span multiple lines if each line but the last is terminated by a 
backslash (‘\’) character. For example:

targetCities=\
        Atlanta,\
        Chicago,\
        Detroit

This is equivalent to targetCities=Atlanta,Chicago,Detroit,(white space at the beginning of lines is ignored).

The characters newline, carriage return, and tab can be inserted with characters \n, \r, and \t, respectively.

The backslash character must be escaped as a double backslash. For example:

path=c:\\docs\\doc1

UNICODE characters can be entered as they are in a Java program, using the \u prefix. For example, \u002c.
