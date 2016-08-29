# Managing Dependencies

To manage dependencies, we simply have to define [maven repositories](https://maven.apache.org/guides/introduction/introduction-to-repositories.html) in `build.gradle` for the script to download each of them.
This will allow us to avoid adding dependencies into the repository.
- Consistent version of dependencies between developers
- If there are updates to the dependencies, developers will be able to download the latest versions (only if specified in the script) without having to update the script
- Less bloated repository

The following tasks are related to ensuring that the required dependencies are downloaded and used.

#### `compileJava`
`assemble`, `build`, `jar` are **examples** of tasks that will call `compileJava`.
This will check whether the project has the required dependencies to run the main program, and download any missing dependencies before compiling the classes.

See `build.gradle` -> `allprojects` -> `dependencies` -> `compile` for the list of dependencies required.

#### `compileTestJava`
`test` is an **example** of a task that will call `compileTestJava`.
This will check whether the project has the required dependencies to perform testing, and download any missing dependencies before compiling the test classes.

See `build.gradle` -> `allprojects` -> `dependencies` -> `testCompile` for the list of dependencies required.