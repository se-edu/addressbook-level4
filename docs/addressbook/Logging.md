# Logging

We are using [log4j2](http://logging.apache.org/log4j/2.x/) as our logger, and `AppLogger` is used as a wraparound of the logger to control the logging levels programmatically according to our requirements.

**Note to developers**
- The logging level can be controlled using the `loggingLevel` setting in the configuration file (See [Configuration](../docs/Configuration.md)).
- The `AppLogger` for a class can be obtained using `LoggerManager.getLogger(Class)` which will log messages according to `src/main/resources/log4j2.json`'s specified patterns
- Currently log messages are output through 3 methods: `Console`, `.log` and `.csv`. Since the CSV file is using `;` as its delimiter, developers should avoid using `;` which may lead to incorrect interpretation of log data.


**log4j2 information**
- Supposedly optimized version of `LogBack`
- Can also act as an API for other logger implementations such as `slf4j`
  - `log4j2` has its internal implementation, but can be substituted as needed
  - Our application uses the default internal implementation

# Guidelines

## Logging Levels

- FATAL
  - Critical use case affected, which may possibly cause the termination of the application

- ERROR **(NOT USED)**

- WARN
  - Can continue, but with caution

- INFO
  - Information important for the application's purpose
    - e.g. update to local model/request sent to cloud
  - Information that the layman user can understand

- DEBUG
  - Used for superficial debugging purposes to pinpoint components that the fault/bug is likely to arise from
  - Should include more detailed information as compared to `INFO` i.e. log useful information!
    - e.g. print the actual list instead of just its size

- TRACE
  - Everywhere, e.g. entry and exit of ALL methods, including the parameters being given/returned
  - Will not be using log4j2 for this, will explore AOP at a future date

## General

- Possible guidelines to adhere to:
  - Include version of code being run in EVERY file
  - Don't manually concatenate strings, which may worsen performance
    - Instead, use log4j2's `{}` to log parameters
  - `Catch` blocks that re-throw the exception should not log the exception as this may lead to repeated logging of the same exception
  - Avoid `NullPointerException`s, which may occur if we try to log some return value of an object's method, when the object itself may be null
  - Ensure that the object being printed has `toString()` implemented, if not the printout will be uninformative such as `Object@31942`

## References and readings
- [Apache Commons Logging guide](http://commons.apache.org/proper/commons-logging/guide.html#Message_PrioritiesLevels)
- [10 Tips for Proper Application Logging](https://www.javacodegeeks.com/2011/01/10-tips-proper-application-logging.html)
- [Base 22 Java Logging Standards and Guidelines](https://wiki.base22.com/display/btg/Java+Logging+Standards+and+Guidelines)
