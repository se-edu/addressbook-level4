# Update Architecture
We enabled our application to be self-updatable, and downloads only the required components when doing so.

For more detailed information of the individual components, please see:
 - [Launcher](Launcher.md)
 - [Updater](Updater.md)

For more detailed information on how we make a release with this architecture, please see:
 - [Release](Release.md)

## Application Usage Flow - From downloading to updating
1. The user downloads the `installer` from the website, and saves it in a desired directory.
2. Upon execution of the `installer`, it unpacks the required files for the application into the same directory.
    - Launcher
    - Library files (including the main application)
    - Config file
3. `Installer` also starts the `launcher` before it closes.
4. `Launcher` launches the `main application` with its run-time dependencies, as well as any desired arguments.
5. The `main application` runs, and checks for updates in the background.
    - If there is an update, it will download any required libraries, produce a specification file with information about these files and extract an internal `updater` resource in preparation for the upgrade. Required libraries may also include a new version of the `launcher`.
6. Upon exit of the main application, the extracted `updater` resource is executed.
    - This JAR is a fat JAR and already contains required dependencies e.g. to read the specification file.
7. The `updater` resource will read the specification file, and perform the needed operations for version upgrade.
    - After updating, this resource will close and be left in the directory, and will probably be overwritten in the next update

## Constraints and Considerations

Here are some of the constraints we faced during the design of the update architecture:

- On Windows, dependencies used during program runtime cannot be modified
    - A separate executable has to be used to perform updates to the application.

        - Possible solution A: Create a launcher that checks for new versions and performs updates.
            - However, we would also like to make updates to this separate executable.
        - Possible solution B: Also have an update component in the main application, so the main application and the executable can update each other perpetually.
            - However, this will result in duplicate update codes, and the update status cannot be shown on the main application. (At least not in a straightforward way. I guess we could still write the update status to a file and make the main app poll for status changes.)

        - Current solution: Main application extracts an internal resource which is launched to perform the update.
            - Drawbacks: We cannot update only the updater itself, and the main application will be slightly bloated since it requires some similar dependencies as the updater.
            - Possible optimization: Change updater into a non-fat JAR. Its dependencies could be copied by the main application into the same directory as the extracted updater jar.
