# Updater
A separate updater jar which contains key classes to perform data migration from one version to another. It is typically named as `updater.jar`.

## How does the update work
The main application has the updater jar as an internal resource.

1. After the main application finishes the downloading of updates, it will extract the updater jar in preparation for doing the update migration.
2. At exit of the main application, the updater jar is executed. It will attempt to perform updates for several times, expecting the main application to hog the resources to be replaced for a short while.
    - Updater jar reads from the specification file created by the main application to know which files have to be moved
    