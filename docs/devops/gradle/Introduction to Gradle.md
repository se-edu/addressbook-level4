# Gradle

## Background
[Gradle](https://gradle.org/) is an integration technology that addressbook uses for continuous integration:
- building the application JAR
- handling project dependencies
- testing (including coverage of test cases)
- checking for bugs & code style violations

We have wrapped our project in a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), so that anyone who has cloned the repository can perform the same gradle tasks to manage dependencies or perform automated testing.
The gradle details are defined in `build.gradle`, which is a Groovy script. You can start learning them from [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).


## Gradle Tasks

There are default gradle tasks provided for each plugin (e.g. `java`, `checkstyle`) used in the gradle script. In addition, we are able to define custom gradle tasks if required. (See [Testing.md](./Testing.md))
See Gradle's [Java Plugin](https://docs.gradle.org/current/userguide/java_plugin.html)'s `Figure 45.1` to see the hierarchical overview of common gradle tasks.

### Managing Dependencies

To manage dependencies, we simply have to define [maven repositories](https://maven.apache.org/guides/introduction/introduction-to-repositories.html) in `build.gradle` to download each of them.
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