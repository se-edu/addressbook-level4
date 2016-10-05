# Learning Outcomes
After studying this code and completing the corresponding exercises, you should be able to,

1. [Use High-Level Designs `[LO-HighLevelDesign]`](#use-high-level-designs-lo-highleveldesign)
1. [Use Event-Driven Programming `[LO-EventDriven]`](#use-event-driven-programming-lo-eventdriven`)
1. [Use API Design `[LO-ApiDesign]`](#use-api-design-lo-apidesign)
1. [Use Assertions `[LO-Assertions]`](#use-assertions-lo-assertions)
1. [Use Logging `[LO-Logging]`](#use-logging-lo-logging)
1. [Use Defensive Coding `[LO-DefensiveCoding]`](#use-defensive-coding-lo-defensivedoding)
1. [Use Build Automation `[LO-BuildAutomation]`](#use-build-automation-lo-buildautomation)
1. [Use Continuous Integration `[LO-ContinuousIntegration]`](#use-continuous-integration-lo-continuousintegration)

------------------------------------------------------------------------------------------------------

## Use High-Level Designs `[LO-HighLevelDesign]`

Note how the [Developer Guide](DeveloperGuide.md#architecture) describes the high-level design using an
_Architecture Diagrams_ and high-level sequence diagrams.

------------------------------------------------------------------------------------------------------

## Use Event-Driven Programming `[LO-EventDriven]`

Note how the [Developer Guide](DeveloperGuide.md#architecture) uses events to communicate with components 
without needing a direct coupling. Also note how the `EventsCenter` class acts as an event dispatcher to
facilitate communication between event creators and event consumers.

------------------------------------------------------------------------------------------------------

## Use API Design `[LO-ApiDesign]`

Note how components of AddressBook have well-defined APIs. For example, the API of the `Logic` component
is given in the [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)
<img src="images/LogicClassDiagram.png" width="800"><br>

**Resources**
* [A three-minutes video](https://www.youtube.com/watch?v=Un80XoRT1ME) of designing architecture of and 
  discovering component APIs for a Game of Tic-Tac-Toe. 

------------------------------------------------------------------------------------------------------

## Use Assertions `[LO-Assertions]`

Note how the AddressBook app uses Java `assert`s to verify assumptions.

**Resources**
 * [Programming With Assertions](http://docs.oracle.com/javase/6/docs/technotes/guides/language/assert.html) - a 
     guide from Oracle. 
 * [How to enable assertions in Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions)
 
#### Exercise: Add more assertions
 * Make sure assertions are enabled in Eclipse by forcing an assertion failure (e.g. add `assert false;` somewhere in 
 the code and run the code to ensure the runtime reports an assertion failure).
 
 * Add more assertions to AddressBook as you see fit.
 
------------------------------------------------------------------------------------------------------

## Use Logging `[LO-Logging]`

Note [how the AddressBook app uses Java's `java.util.log` package to do logging](DeveloperGuide.md#logging).

**Resources**
 * Tutorials
   * [Logging using java.util.logging](http://tutorials.jenkov.com/java-logging/index.html) - a tutorial by Jakob Jenkov
   * [Logging tutorial](http://docs.oracle.com/javase/7/docs/technotes/guides/logging/overview.html) - a more detailed 
   tutorial from Oracle.
 * Logging best practices 
   * [Apache Commons Logging guide](http://commons.apache.org/proper/commons-logging/guide.html#Message_PrioritiesLevels)
   * [10 Tips for Proper Application Logging](https://www.javacodegeeks.com/2011/01/10-tips-proper-application-logging.html)
   * [Base 22 Java Logging Standards and Guidelines](https://wiki.base22.com/display/btg/Java+Logging+Standards+and+Guidelines)

#### Exercise: Add more logging
 Add more logging to AddressBook as you see fit.
 
------------------------------------------------------------------------------------------------------
 
## Use Defensive Coding `[LO-DefensiveCoding]`
 
 Note how AddressBook uses the `ReadOnly*` interfaces to prevent objects being modified by clients who are not 
 supposed to modify them.
 
#### Exercise: identify more places for defensive coding
  Analyze the AddressBook code/design to identify,
   * where defensive coding is used
   * where the code can be more defensive

------------------------------------------------------------------------------------------------------

## Use Build Automation `[LO-BuildAutomation]`

Note [how the AddressBook app uses Gradle to automate build tasks](UsingGradle.md).

**Resources**
 * Tutorials
   * [Getting started with Gradle (Java)](https://gradle.org/getting-started-gradle-java/) - a tutorial from the Gradle team
   * [Another tutorial](http://www.tutorialspoint.com/gradle/) - from TutorialPoint 
 
#### Exercise: Use gradle to run tasks
 * Use gradle to do these tasks (instructions are [here](UsingGradle.md)) 
   : Run all tests in headless mode, build the jar file.
   
#### Exercise: Use gradle to manage dependencies
 * Note how the build script `build.gradle` file manages third party dependencies such as ControlsFx. <br>
   Update that file to manage a third-party library dependency.

------------------------------------------------------------------------------------------------------

## Use Continuous Integration `[LO-ContinuousIntegration]`

Note [how the AddressBook app uses Travis to perform Continuous Integration](UsingTravis.md).

**Resources**
 * Tutorials
   * [Getting started with Travis](https://docs.travis-ci.com/user/getting-started/) - a tutorial from the Travis team
 
#### Exercise: Use Travis in your own project
 * Set up Travis to perform CI on your own project.
 
------------------------------------------------------------------------------------------------------

{More to be added}
* Integration testing
* System testing
* Acceptance testing (+dogfooding)
* Equivalence classes
* Boundary value analysis
* Test input combination
* GUI test automation
* Design patterns
* Static analysis
* Code reviews
* Code coverage


