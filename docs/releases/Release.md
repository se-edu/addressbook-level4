# Release

## Background Information
### Versioning
An `addressbook` version has the format of `V<MAJOR>.<MINOR>.<PATCH>` with suffix of `ea` if it is an early access, e.g.
`V1.0.0ea` for early access version and `V1.0.0` for stable version.

- `MAJOR` version is bumped up when a release introduces major changes to the application.
- `MINOR` version is bumped up on every release when `MAJOR` version does not change.
- `PATCH` version is bumped up when there is a hotfix needed for the current release version.

This versioning system is loosely based on [Semantic Versioning](http://semver.org/).

### Release cycle
`addressbook` has 3 branches for release, namely `master`, `early-access` and `stable`:
- `master` : development will occur in this branch.
- `early-access`: when we have a release candidate from master, the latest commit in `master` will be merged to this
branch and an early access release will be made for people to try out the new features. This release candidate will be
polished in this branch, i.e. any bug fixes to early access version will go to this branch, increasing the `PATCH` version number
(e.g. from `V1.1.-0-ea` to `V1.1.-1-ea`). This change is then merged back to master.

  If there are more features to be published for people to try out, a new early access release will be created with a bump
in `MINOR` version number, resetting the `PATCH` version number to 0.

  This release channel is not meant for production use. Those who are interested to try out the latest (possibly unstable)
features of Addressbook should use this early access version.
- `stable` : Once `early-access` version has enough polished features to be included in the new version for
production use, it will be released as a stable version with the same version number as the `early-access` version,
excluding the `ea` mark of `early-access` version. If there is any hotfix to stable version, it will be done in this branch,
bumping up the `PATCH` version number (e.g. from `V1.1.-1-` to `V1.1.-2-`). This change is then merged to `early-access`
branch with a bump in `early-access` `PATCH` version number from whatever it is (e.g. from `V1.2.-0-ea` to `V1.2.-1-ea`)
and this will then be merged to `master`.

To illustrate, look at the diagram below.

<img src="../images/Release Cycle.jpg" width="600">

Development in master does not stop, even after creating an early access release. Version does not matter as well in master.

On creating an early access release, merge the `master` branch to `early-access` branch and create a release (instruction below)
with the `PATCH` version of 0, like `V1.0.0ea` and `V1.1.0ea` in the diagram. If the release has a bug, it will be fixed in
`early-access` branch, and the bug fix needs to be merged back `master` branch as shown in `V1.0.1ea`.

When `early-access` version has a set of new features that is well polished, we will create a stable release by merging
`early-access` branch to `stable` branch and create a release. The version of the release uses the latest `early-access`
version without early access flag (from `V1.0.2ea` to `V1.0.2` and from `V1.1.2ea` to `V1.1.2` in the diagram).

If the stable release requires a bug fix, the bug fix will be done in `stable` branch. A re-release will be done with
a bump in `PATCH` version, as shown in `V1.0.3` in the diagram. This bug fix will be merged back to `early-access` branch
in which we will re-release the early access version with a bump in early access `PATCH` version, as shown in `V1.1.0ea`
to `V1.1.1ea` in the diagram (and not to `V1.0.3ea`). This bug fix will also be merged to `master` branch from
`early-access` branch.


References to how teams have multiple release channel:
- http://blog.atom.io/2015/10/21/introducing-the-atom-beta-channel.html
- https://docs.google.com/presentation/d/1uv_dNkPVlDFG1kaImq7dW-6PasJQU1Yzpj5IKG_2coA/present?slide=id.i0
- http://blog.rust-lang.org/2014/10/30/Stability.html

## Creating a Release

### How to create a release
1. Merge the appropriate branches  
 - Releasing an early-access version: Merge `master` branch to `early-access` branch.  
Releasing a stable version: Merge `early-access` branch to `stable` branch.
    - Use `git merge <branch> --no-commit --no-ff` (LOCALLY) so that a merge commit won't be made, in which you can make relevant changes (e.g. changing version number and early access flag) before committing with the version of the software as the commit message.
2. Update version numberings
  - Update the versions of the application and its dependencies in `MainApp` and in `build.gradle`.
    - If the main application is an early access version, set `IS_EARLY_ACCESS` in `MainApp` as `true` and add `ea` at the end of version in `build.gradle`.
    - For custom dependencies, manually check the commits to see if its package has been modified since the last release. If it has, modify its version numbering (usually bumping the minor or major version, according to semantic versioning).
3. Compile the custom dependencies
  - Run `gradle` task `createInstallerJar`
4. Update version data
  - Generate partially-updated `VersionData.json` containing the list of libraries and information
    - Run `gradle` task `generateVersionData`
      - Information of unchanged libraries will be copied from the old `VersionData.json`
      - The console will print a list of libraries which needs to be manually updated for the new version
  - Upload all updated libraries
    - Upload them to https://github.com/HubTurbo/addressbook/releases/tag/Resources
  - Update `VersionData.json`
    - Open `VersionData.json` and manually update the new fields accordingly
    - Fill in the links to download the new libraries.
    - Change the OS compatibility of the new libraries to ensure that only the libraries relevant to an OS will be loaded and checked
5. Create a new commit
  - Commit and push the files for release - name the commit `V<MAJOR>.<MINOR>.<PATCH>` (with suffix `ea` if it's an early access version)
    - This is so that the following GitHub release (done in Step 7) will appropriately tag this commit containing the updated `VersionData.json`
6. Create the release JAR by running the Gradle task `createInstallerJar`
  - this must be run again to use the updated `VersionData.json`
7. Draft a new release in [GitHub](https://github.com/HubTurbo/addressbook/releases) and tag the corresponding branch (`early-access` or `stable`)
  - Use the version number for both the tag and the title.
8. Upload the generated `installer-V*.*.*.jar` found at `build/libs` to the latest release.
9. Publish!

## More About Release
The main application of `addressbook` is configured to be released as a [non-fat JAR](http://stackoverflow.com/questions/19150811/what-is-a-fat-jar),
that is, libraries that it depends on will not be included inside the `addressbook` JAR itself. Instead, the JARs of these libraries
will be put in `lib` folder which can then be included during runtime by setting the classpath to include the `lib` folder.

For example:
```
addressbook/
  |-> launcher.jar
  |-> lib/
       |-> apache-commons-io.jar
       |-> apache-logging.jar
       |-> jxbrowser.jar
       |-> resource.jar
```

This set-up is used to reduce the size of updates to be downloaded whenever a new version of `addressbook` is released. With
this set-up, only main application JAR and libraries that need to be updated will be downloaded, essentially reducing the
time taken to download updates.

For convenience, the whole set-up mentioned above is packed into 1 JAR file
which will self-unpack itself on first run. It can be deleted after installation, and the main application can be launched through the launcher instead.

Several gradle tasks have been prepared to make it effortless to create a release of `addressbook` with the set-up mentioned above.

### Gradle Tasks for Release

The following explains the Gradle tasks that are required for a release.

#### generateVersionData
Generates the version data (VersionData.json) which will be used by user's application instance to know if it has an update
and what to update. It reads the dependencies of the latest version of `addressbook` and put those dependencies into the version data. To make it easier for developers, it will use previous version's values for libraries that did not change. Developers then need to manually update the new dependencies information (e.g. download link, OS they are meant for).

`generateVersionData` has its own source set which includes everything and its dependencies are extended from main application compile dependencies. This is to make it easier for generateVersionData to read any information it needs to create version data. The main class it uses is `addressbook/util/VersionDataGenerator.java`

#### createCommonsJar
Creates the commons jar dependency. This jar contains the classes found in the `commons` package, including `FileUtil` and `OsDetector`, and is used as a dependency for most components.

#### createUpdaterJar
Creates the updater jar dependency. This file contains the components which are required for the migration of the application to a newer version.

See: [Updater](Updater.md)

#### createLauncherJar
Creates launcher executable file, to launch the main application with custom arguments.

See: [Launcher](Launcher.md)

#### createInstallerJar
Creates the executable packed JAR for the user to download and use. The packed JAR contains the main application and all the libraries needed for the main application to run.

Note that running this will also run the other mentioned tasks in a specified order. See `build.gradle` for more details (`dependsOn`/`mustRunAfter`).

On first run, it unpacks the libraries and the main application, then runs the launcher. On subsequent runs, it simply attempts to restore any missing files if the current version is not newer than the installer's (i.e. has not been updated) then simply runs the launcher. However, it is expected that the user will delete this file after installation.

### Possible Improvements

#### Automate generateVersionData
Ideally, we don't need to host those libraries JAR on our own in GitHub release. We can grab the JARs from the Maven repositories
that `gradle` uses to get those libraries. Unfortunately, the lack of documentation of `gradle` makes it impossible to
get the URL of those JAR in the Maven repositories, hence the manual need to update the download links and upload the new
libraries to GitHub release.

#### GUI for generateVersionData
Instead of having to deal with text file of update data which is prone to error, we can have a GUI which generateUpdateData
uses to show what libraries have been changed in the latest version with fields to update the download links. Also,
we can have a dropdown option for the OS which the libraries are needed in so developers don't need to type them manually.

#### Installer dependencies
Use of ShadowJAR should be considered to enable logging.
