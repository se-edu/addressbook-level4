# Testing

## Background
We are using [Gradle](https://docs.gradle.org/)'s [wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) to setup testing configurations and run tests.

We work towards enabling **headless testing** (using [TestFX](https://github.com/TestFX/TestFX)) so that the test results will be more consistent between different machines. In addition, it will reduce disruption for the tester - the tester can continue to do his or her own work on the machine!

Related resources:
 - [Gradle's User Guide](https://docs.gradle.org/current/userguide/userguide.html)
  - [Gradle's Java Plugin](https://docs.gradle.org/current/userguide/java_plugin.html)
  - [Gradle's Jacoco Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
 - [Coveralls Gradle Plugin](https://github.com/kt3k/coveralls-gradle-plugin)
 - [Travis CI Documentation](https://docs.travis-ci.com/)


## Tests Information

Tests can be found in the `./src/test/java` folder. We have grouped and packaged the tests into the following types.

1. Unit Tests - `address` and `commons` package
  - Logic Testing

2. GUI Unit Tests - `guiunittests` package
    - Tests the UI interaction within a single component, and ensure its behaviour holds.

3. System Tests - `guitests` package
  - Tests the UI interaction with the user as well as the interaction between various components (e.g. passing of data)

## Running Tests

As mentioned, we do our testing by running gradle tasks.
Tests' settings are mostly contained in `build.gradle` and `.travis.yml`.

### Available Gradle Tasks
There are a few key gradle tasks defined that we can play around with:  
#### Testing
- `allTests` to run all tests
- `guiTests` to run tests in the `guitests` package
- `guiUnitTests` to run tests in the `guiunittests` package
- `unitTests` to run tests in the `address` package

#### Test mode
- `headless` to indicate headless mode

#### Others
- `checkStyle` to run code style checks
  - `PMD`, `FindBugs` and `Checkstyle`
- `clean` to remove previously built files
    - Running tasks repeatedly may not work unless the build files are `clean`ed first.
- `coverage` to generate coverage information after tests have been run
  - Generated coverage reports can be found at `./build/reports/jacoco/coverage/coverage.xml`
- `coveralls` to upload data from CI services to coveralls.io (no reason to run this locally)

### Local Testing
#### How to do some common testing-related tasks
- To run all tests in headless mode: `./gradlew`
  - It will run `clean`, `headless`, `allTests`, `coverage` tasks.
- To run all tests in headful mode: `./gradlew clean allTests coverage`
- To run checkstyle followed by headful tests `./gradlew clean checkStyle allTests coverage`
- Running specific test classes in a specific order
  - When troubleshooting test failures,
  you might want to run some specific tests in a specific order.  
    - Create a test suite (to specify the test order):
     ```java
     package address;

     import org.junit.runner.RunWith;
     import org.junit.runners.Suite;

     @RunWith(Suite.class)
     @Suite.SuiteClasses({
             /*The tests to run, in the order they should run*/
             address.browser.BrowserManagerTest.class,
             guitests.PersonOverviewTest.class
     })

     public class TestsInOrder {
         // the class remains empty,
         // used only as a holder for the above annotations
     }
     ```
    - Run the test suite using the gradle command <br>
   `./gradlew clean headless allTests --tests address.TestsInOrder`


### CI Testing
- We run our CI builds on [Travis CI](https://travis-ci.org/HubTurbo/addressbook)
- See [Configuring Travis CI](../integration/Configuring Travis CI.md) for more details
