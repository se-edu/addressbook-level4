# Configuration

Certain properties of the application can be customized through the configuration file (default: `config.json`):

- Application title
- Logging level
- Local Data file
- Preferences file
- Name of addressbook

Variable names in this application are meant to be straightforward, to allow students to understand its idea more easily.

**Note that setting of custom the logging level is done only after reading of the configuration file, thus any logging done before it will be at the default `INFO` level**

# Introducing new configuration variables
(For developers)
- Ensure that a default value is assigned to the desired variable.
- Add getters and setters for the new variable
- Get the config object from respective component managers (see `MainApp::initComponents`)
  - Add `Config` to the constructor parameters if they do not already accept a `Config` object
  - DO NOT obtain the configuration object directly from `StorageManager` as such practices tend to hide application state information during testing.
