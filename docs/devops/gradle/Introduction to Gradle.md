# Gradle

## Background
[Gradle](https://gradle.org/) is an integration technology that addressbook uses for:
- [handling project dependencies](./Managing Dependencies.md)
- [building the application JAR](./Building the Application.md)
- [testing](./Testing.md) 
    - checking for bugs & code style violations
    - determining coverage of test cases

We have wrapped our project in a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), so that anyone who has cloned the repository can perform the same gradle tasks to manage dependencies or perform automated testing.
The gradle details are defined in `build.gradle`, which is a Groovy script. You can start learning them from [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).

## Gradle Tasks
Gradle tasks are run using `gradlew <task1> <task2> ...` on Windows and `./gradlew <task1> ...` on Mac/Linux.

There are default gradle tasks provided for each gradle plugin (e.g. `java`, `checkstyle`) used in the script. In addition, we are able to define custom gradle tasks if required. (See [Testing.md](./Testing.md) for examples)

See `Figure 45.1` in Gradle's [Java Plugin](https://docs.gradle.org/current/userguide/java_plugin.html) to see the hierarchical overview of common gradle tasks.
