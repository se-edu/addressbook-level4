# Configuration

Certain properties of the application can be customized through the configuration file (default: `config.json`):

- Logging level
- Update interval
- Number/Type of browser(s) used
- Local Data file
- Cloud data file
- Name of addressbook
- Application title

Most of the variable names are rather straightforward. However, the logging section is slightly more complex and will be elaborated on.

Behaviours to note:
- The application creates a config file with default values if no existing config file can be found.
- Configuration is reset to default if file cannot be read (including if the file contains unexpected or has missing parameters)

# Logging
There are 2 configuration variables meant for logging:
- `currentLogLevel`
  - Specifies the application-wide logging level
- `specialLogLevel`
  - Adding `className` : `loggingLevel` pairs to this variable will impose a special logging level for that class (priority over the application-wide logging level)

Here is a typical example of the logging parameters in `config.json`:
```
...
  "currentLogLevel" : "DEBUG",
  "specialLogLevels" : {
      "ModelManager" : "TRACE",
      "SyncManager" : "TRACE"
  },
...
```
Such a configuration cause the loggers to log messages at the `DEBUG` level throughout the application, except `ModelManager`'s and `SyncManager`'s loggers which will log messages at the `TRACE` level.

**Note that these settings do not apply to most of `Config`, and also `StorageManager`'s code to read the configuration file since they are not yet in effect. Therefore the logging will usually be at the default level for `Config` and for the affected parts of `StorageManager`. This default logging level is specified in `resources/log4j2.json`.**

# Introducing new configuration variables
(For developers)
- Ensure that a default value is assigned to the desired variable.
- Add getters and setters for the new variable
- Get the config object from respective component managers (see `MainApp::initComponents`)
  - Add `Config` to the constructor parameters if they do not already accept a `Config` object
  - DO NOT obtain the configuration object directly from `StorageManager` as such practices tend to hide application state information during testing.
