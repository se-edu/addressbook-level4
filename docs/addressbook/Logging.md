# Logging

We are using `java.util.logging.Logger` as our logger, and `LoggerManager` is used to manage the logging levels of loggers and handlers (for output of log messages)

**Note to developers**
- The logging level can be controlled using the `currentLogLevel` setting in the configuration file (See [Configuration](../docs/Configuration.md)).
- The `Logger` for a class can be obtained using `LoggerManager.getLogger(Class)` which will log messages according to the specified logging level
- Currently log messages are output through: `Console` and `.log`

# Guidelines

## Logging Levels

- SEVERE
  - Critical use case affected, which may possibly cause the termination of the application

- WARNING
  - Can continue, but with caution

- INFO
  - Information important for the application's purpose
    - e.g. update to local model/request sent to cloud
  - Information that the layman user can understand

- CONFIG
  - Usually used to output information regarding the system's configuration

- FINE
  - Used for superficial debugging purposes to pinpoint components that the fault/bug is likely to arise from
  - Should include more detailed information as compared to `INFO` i.e. log useful information!
    - e.g. print the actual list instead of just its size

- FINER/FINEST
  - Almost everywhere, to allow more specific pinpointing of errors in methods

## General

- Possible guidelines to adhere to:
  - Include version of code being run in EVERY file
  - `Catch` blocks that re-throw the exception should not log the exception as this may lead to repeated logging of the same exception
  - Avoid `NullPointerException`s, which may occur if we try to log some return value of an object's method, when the object itself may be null
  - Ensure that the object being printed has `toString()` implemented, if not the printout will be uninformative such as `Object@31942`

## References and readings
- [Apache Commons Logging guide](http://commons.apache.org/proper/commons-logging/guide.html#Message_PrioritiesLevels)
- [10 Tips for Proper Application Logging](https://www.javacodegeeks.com/2011/01/10-tips-proper-application-logging.html)
- [Base 22 Java Logging Standards and Guidelines](https://wiki.base22.com/display/btg/Java+Logging+Standards+and+Guidelines)
