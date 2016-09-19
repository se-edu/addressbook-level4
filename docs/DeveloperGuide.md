# Developer Guide [Outdated]

* [Setting Up](#setting-up)
* [Design](#design)
* [Testing](#testing)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Gloassary](#appendix-d--glossary)

- [Gradle](devops/gradle/Introduction to Gradle.md)
- [Configuration](addressbook/Configuration.md)
- [Logging](addressbook/Logging.md)
- [Setting up Travis CI](devops/integration/Configuring Travis CI.md)

## Setting up

#### Prerequisites

1. **JDK 8** or later
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))


#### Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse plugin** as given in the prerequisites above)
2. Click `File` > `Import`
3. Click `General` > `Existing Projects into Workspace` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

## Design

Architecture overview

UI

Logic

etc.

## Testing

**In Eclipse**: 
* To run all tests, right-click on the `src/test/java` folder and choose 
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose 
  to run as a JUnit test.
  
**Using Gradle**:
* Open a command window in the project folder and type `g`


Tests can be found in the `./src/test/java` folder. We have grouped and packaged the tests into the following types.

1. Unit Tests - `address` and `commons` package
  - Logic Testing

2. GUI Unit Tests - `guiunittests` package
    - Tests the UI interaction within a single component, and ensure its behaviour holds.

3. System Tests - `guitests` package
  - Tests the UI interaction with the user as well as the interaction between various components (e.g. passing of data)
  
  We work towards enabling **headless testing** (using [TestFX](https://github.com/TestFX/TestFX)) so that the test results will be more consistent between different machines. In addition, it will reduce disruption for the tester - the tester can continue to do his or her own work on the machine!
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
   
   


 - [Travis CI Documentation](https://docs.travis-ci.com/)


## Running Tests

As mentioned, we do our testing by running gradle tasks.
Tests' settings are mostly contained in `build.gradle` and `.travis.yml`.



### CI Testing
- We run our CI builds on [Travis CI](https://travis-ci.org/HubTurbo/addressbook)
- See [Configuring Travis CI](../integration/Configuring Travis CI.md) for more details

What are dependencies and A project often depends on third party libraries. For example, Address Book depends on the 
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a new person |
`* * *` | user | delete a person | remove entries that I no longer need
`* * *` | user | find a person by name | locate details of persons without having to go through the entire list
`* *` | user | hide [private contact details](#private-contact-detail) by default | minimize chance of someone else seeing them by accident
`*` | user with many persons in the address book | sort persons by name | locate a person easily


## Appendix B : Use Cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Delete person

**MSS**

1. User requests to list persons
2. AddressBook shows a list of persons
3. User requests to delete a specific person in the list
4. AddressBook deletes the person <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. AddressBook shows an error message <br>
  Use case resumes at step 2

## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java 8 or higher installed.
2. Should be able to hold up to 1000 persons.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Private contact detail

> A contact detail that is not meant to be shared with others
