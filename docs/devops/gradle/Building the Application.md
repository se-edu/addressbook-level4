# Building the Application

## Background

To compile the application into a JAR, we have used [shadow jar](https://github.com/johnrengelman/shadow) so that the dependencies can be packed into a single JAR file.

See [Shadow user guide](http://imperceptiblethoughts.com/shadow/) for more information.

## Compiling

To compile the jar file, simply run the `shadowJar` gradle task. The resulting `addressbook.jar` will be placed in `build/jar`.