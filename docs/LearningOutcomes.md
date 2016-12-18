# Learning Outcomes
After studying this code and completing the corresponding exercises, you should be able to,

1. [Use High-Level Designs `[LO-HighLevelDesign]`](#use-high-level-designs-lo-highleveldesign)
1. [Use Event-Driven Programming `[LO-EventDriven]`](#use-event-driven-programming-lo-eventdriven`)
1. [Use API Design `[LO-ApiDesign]`](#use-api-design-lo-apidesign)
1. [Use Assertions `[LO-Assertions]`](#use-assertions-lo-assertions)
1. [Use Logging `[LO-Logging]`](#use-logging-lo-logging)
1. [Use Defensive Coding `[LO-DefensiveCoding]`](#use-defensive-coding-lo-defensivecoding)
1. [Use Build Automation `[LO-BuildAutomation]`](#use-build-automation-lo-buildautomation)
1. [Use Continuous Integration `[LO-ContinuousIntegration]`](#use-continuous-integration-lo-continuousintegration)
1. [Use Code Coverage `[LO-CodeCoverage]`](#use-code-coverage-lo-codecoverage)
1. [Apply Test Case Design Heuristics `[LO-TestCaseDesignHeuristics]`](#apply-test-case-design-heuristics-lo-testcasedesignheuristics)
1. [Write Integration Tests `[LO-IntegrationTests]`](#write-integration-tests-lo-integrationtests)
1. [Perform System Testing `[LO-SystemTesting]`](#perform-system-testing-lo-systemtesting)
1. [Automate GUI Testing `[LO-AutomateGuiTesting]`](#automate-gui-testing-lo-automateguitesting)
1. [Apply Design Patterns `[LO-DesignPatterns]`](#apply-design-patterns-lo-designpatterns)
1. [Use Static Analysis `[LO-StaticAnalysis]`](#use-static-analysis-lo-staticanalysis)
1. [Do Code Reviews `[LO-CodeReview]`](#do-code-reviews-lo-codereview)

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
([![Build Status](https://travis-ci.org/se-edu/addressbook-level4.svg?branch=master)](https://travis-ci.org/se-edu/addressbook-level4))

**Resources**
 * Tutorials
   * [Getting started with Travis](https://docs.travis-ci.com/user/getting-started/) - a tutorial from the Travis team
 
#### Exercise: Use Travis in your own project
 * Set up Travis to perform CI on your own project.
 
------------------------------------------------------------------------------------------------------

## Use Code Coverage `[LO-CodeCoverage]`

Note how our CI server [Travis uses Coveralls to report code coverage](UsingTravis.md).
([![Coverage Status](https://coveralls.io/repos/github/se-edu/addressbook-level4/badge.svg?branch=master)](https://coveralls.io/github/se-edu/addressbook-level4?branch=master))
After setting up Coveralls for your project, you can visit Coveralls website to find details about the
coverage of code pushed to your repo. [Here](https://coveralls.io/github/se-edu/addressbook-level4?branch=master) is an example.

 
#### Exercise: Use EclEmma to measure coverage locally
 * Install the [EclEmma Eclipse Plugin](http://www.eclemma.org/) in your computer and use that to find code that 
   is not covered by the tests. This plugin can help you to find coverage details even before you push code 
   to the remote repo.
 
------------------------------------------------------------------------------------------------------

## Apply Test Case Design Heuristics `[LO-TestCaseDesignHeuristics]`

The [`StringUtilTest.java`](../src/test/java/seedu/address/commons/util/StringUtilTest.java) class gives some examples
of how to use _Equivalence Partitions_, _Boundary Value Analysis_, and _Test Input Combination Heuristics_ to improve
the efficiency and effectiveness of test cases testing the 
[`StringUtil.java`](../src/main/java/seedu/address/commons/util/StringUtil.java) class.

 
#### Exercise: Apply Test Case Design Heuristics to other places 
 * Find answers to these questions:
   * What is an Equivalence Partition? How does it help to improve E&E of testing?
   * What is Boundary Value Analysis? How does it help to improve E&E of testing?
   * What are the heuristics that can be used when combining multiple test inputs? 
 * Use the test case design heuristics mentioned above to improve test cases in other places.
 
------------------------------------------------------------------------------------------------------

## Write Integration Tests `[LO-IntegrationTests]`

Consider the [`StorageManagerTest.java`](../src/test/java/seedu/address/storage/StorageManagerTest.java) class.
* Test methods `prefsReadSave()` and `addressBookReadSave()` are integration tests. Note how they simply test if
  The `StorageManager` class is correctly wired to its dependencies.

* Test method `handleAddressBookChangedEvent_exceptionThrown_eventRaised()` is a unit test because it uses
  _dependency injection_ to isolate the SUT `StorageManger::handleAddressBookChangedEvent(...)` from its 
  dependencies.

Compare the above with [`LogicManagerTest`](../src/test/java/seedu/address/logic/LogicManagerTest.java). 
Many of the tests in that class (e.g. `execute_add_*` methods) tests are neither integration nor unit tests. 
They are a _integration + unit_ tests because they not only checks if the LogicManager is correctly wired to its
dependencies, but also checks the working of its dependencies. For example, the following two lines test the 
the `LogicManager` but also the `Parser`.
```java
@Test
public void execute_add_invalidArgsFormat() throws Exception {
    ...
    assertCommandBehavior("add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
    assertCommandBehavior("add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
    ...
}
```
 
#### Exercise: Write unit and integration tests for the same method. 
 * Write a unit test for a a high-level methods somewhere in the code base.
 * Write an integration test for the same method.
 
------------------------------------------------------------------------------------------------------

## Perform System Testing `[LO-SystemTesting]`

Note how tests below `src/test/java/guitests` package 
(e.g [`AddCommandTest.java`](../src/test/java/guitests/AddCommandTest.java)) are system tests because they test 
the entire system end-to-end.
 
#### Exercise: Write more system tests 
 * Add some more system tests to the existing system tests.
 
------------------------------------------------------------------------------------------------------

## Automate GUI Testing `[LO-AutomateGuiTesting]`

Note how this project uses TextFX library to automate GUI testing, including 
[_headless_ GUI testing](DeveloperGuide.md#headless-gui-testing).
 
#### Exercise: Write more automated GUI tests 
 * Add some more automated GUI tests.
 
------------------------------------------------------------------------------------------------------

## Apply Design Patterns `[LO-DesignPatterns]`

Here are some example design patterns used in the code base.
* **Singleton Pattern** : [`EventsCenter.java`](../src/main/java/seedu/address/commons/core/EventsCenter.java) is 
  Singleton class. Its single instance can be accessed using the `EventsCenter.getInsance()` method.
* **Facade Pattern** : [`StorageManager.java`](../src/main/java/seedu/address/storage/StorageManager.java) is 
  not only shielding the internals of the Storage component from outsiders, it is mostly redirecting methods calls 
  to its internal components (i.e. minimal logic in the class itself). Therefore, `StorageManager` can be considered a 
  Facade class.
* **Command Pattern** : The [`Command.java`](../src/main/java/seedu/address/logic/commands/Command.java) and its 
  sub classes implement the Command Pattern.
* **Observer Pattern** : The [event driven mechanism](DeveloperGuide.md#events-driven-nature-of-the-design) used by 
  this code base employs the Observer pattern.<br>
  For example, objects that are interested in events need to have the `@Subscribe` annotation in the class (this is
  similar to implementing an `<<Observer>>` interface) and register with the `EventsCenter`. When something noteworthy
  happens, an event is raised and the `EventsCenter` notifies all relevant subscribers. Unlike in the
  Observer pattern in which the `<<Observerable>>` class is notifying all `<<Observer>>` objects, here the 
  `<<Observable>>` classes simply raises an event and the `EventsCenter` takes care of the notifications.
* **MVC Pattern** : 
  * The 'View' part of the application is mostly in the `.fxml` files in the `src/main/resources/view`
  folder. 
  * `Model` component contains the 'Model'.
  * Sub classes of [`UiPart`](../src/main/java/seedu/address/ui/UiPart.java) (e.g. `PersonListPanel` ) 
  act as 'Controllers', each controlling some part of the UI and communicating with the 'Model' via a `Logic` 
  component which sits between the 'Controller' and the 'Model'. 
* **Abstraction Occurrence Pattern** : Not currently used in the app.

 
#### Exercise: Discover other possible applications of the patterns  
 * Find other possible applications of the patterns to improve the current design.
   e.g. where else in the design can you apply the Singleton pattern?
 * Discuss pros and cons of applying the pattern in each of the situations you found in the previous step.
 
#### Exercise: Find more applicable patterns 
 * Learn other _Gang of Four_ Design patterns to see if they are applicable to the app.

------------------------------------------------------------------------------------------------------
 
## Use Static Analysis `[LO-StaticAnalysis]`
 
 Note how this project uses the [CheckStyle](http://checkstyle.sourceforge.net/) static analysis tool to confirm 
 compliance with the coding standard.
 
 Other popular Java static analysis tools:
 * [Find Bugs](http://findbugs.sourceforge.net/)
 * [PMD](https://pmd.github.io/)
  
#### Exercise: Use the CheckStyle Eclipse plugin
  * Install the [CheckStyle Eclipse plugin](http://eclipse-cs.sourceforge.net/#!/) and use it to detect 
    coding standard violations.
    
------------------------------------------------------------------------------------------------------

## Do Code Reviews `[LO-CodeReview]`

* Note how some PRs in this project have been reviewed by other developers. 
  Here is an [example](https://github.com/se-edu/addressbook-level4/pull/147).
* Also not how we have used [Codacy](https://www.codacy.com) to do automate some part of the code review workload
  ([![Codacy Badge](https://api.codacy.com/project/badge/Grade/fc0b7775cf7f4fdeaf08776f3d8e364a)](https://www.codacy.com/app/damith/addressbook-level4?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=se-edu/addressbook-level4&amp;utm_campaign=Badge_Grade))

Here are some things you can comment on when reviewing code:
* Read the code from the perspective of a new developer.
  Identify parts that are harder to understand and suggest improvements.
* Point out any coding standard violations.
* Suggest better names for methods/variables/classes.
* Point out unnecessary code duplications.
* Check if the comments, docs, tests have been updated to match the code change.
* Check for violation of relevant principles such as the SOLID principles.
* Point out where SLAP can be improved. e.g. methods that are too long or has too deep nesting.
* Suggest any other code quality improvements.

**Resources**

* [Code Review Best Practices](https://www.kevinlondon.com/2015/05/05/code-review-best-practices.html) - 
  Blog post by Kevin London
* [Why Code Reviews Matter](https://www.atlassian.com/agile/code-reviews) - An article by Atlassian
 
#### Exercise: Review a PR

* Review a GitHub PR created by a team member. 


