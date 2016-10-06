# Developer Guide 

* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E: Product Survey](#appendix-e-product-survey)


## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.
    
2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given 
   in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.
  
#### Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**
* Reason: Eclipse fails to recognize new files that appeared due to the Git pull. 
* Solution: Refresh the project in Eclipse:<br> 
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.
  
**Problem: Eclipse reports some required libraries missing**
* Reason: Required libraries may not have been downloaded during the project import. 
* Solution: [Run tests using Gardle](UsingGradle.md) once (to refresh the libraries).
 

## Design

### Architecture

<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connect them up with each other.
* At shut down: Shuts down the components and invoke cleanup method where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Two of those classes play important roles at the architecture level.
* `EventsCentre` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.

The rest of the App consists four components.
* [**`UI`**](#ui-component) : The UI of tha App.
* [**`Logic`**](#logic-component) : The command executor.
* [**`Model`**](#model-component) : Holds the data of the App in-memory.
* [**`Storage`**](#storage-component) : Reads data from, and writes data to, the hard disk.

Each of the four components
* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images\SDforDeletePerson.png" width="800">

>Note how the `Model` simply raises a `AddressBookChangedEvent` when the Address Book data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images\SDforDeletePersonEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct 
  coupling between components.

The sections below give more details of each component.

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`,
`StatusBarFooter`, `BrowserPanel` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/address/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a person) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeletePersonSdForLogic.png" width="800"><br>

### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/seedu/address/model/Model.java)

The `Model`,
* stores a `UserPref` object that represents the user's preferences.
* stores the Address Book data.
* exposes a `UnmodifiableObservableList<ReadOnlyPerson>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Address Book data in xml format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file 
(default: `config.json`):


## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI. 
   These are in the `guitests` package.
  
2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.address.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units 
     (those code units are assumed to be working).<br>
      e.g. `seedu.address.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as 
      how the are connected together.<br>
      e.g. `seedu.address.logic.LogicManagerTest`
  
**Headless GUI Testing** :
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode. 
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.
 
#### Troubleshooting tests
 **Problem: Tests fail because NullPointException when AssertionError is expected**
 * Reason: Assertions are not enabled for JUnit tests. 
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described 
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option). <br>
   Delete run configurations created when you ran tests earlier.
  
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.
 
 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/) 
    and upload the JAR file your created.
   
### Managing Dependencies

A project often depends on third-party libraries. For example, Address Book depends on the
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
`* * *` | user | add a new task |
`* * *` | user | delete a task | remove tasks that I no longer need
`* * *` | user | edit a task | edit task that I enter wrongly
`* * *` | user | list all tasks | list all the tasks that are created
`* * *` | user | find a task by name or tag | locate details of the tasks without having to go through the entire list
`* * *` | user | complete a task | complete the task and stored in the completed list in the application
`* * *` | user | undo | undo the previous command
`* * *` | user | exit | save and quit the application
`* * *` | advanced user | add alias to command | enter the command quickly
`* * ` | user | view a task | view the complete details of the task
`* * ` | user | remove a tag | remove a tag on an exisiting task
`* * ` | user | uncomplete a task | uncomplete the task that was previously completed
`* * ` | user | redo | redo a undo command
`* * ` | user | remove alias | remove alias that is tagged to the command
`*` | user with many tasks in the application | sort task by name/start date/end date/deadline | locate a task easily
`*` | user | change theme of the application | change to the user's favourite theme

## Appendix B : Use Cases

(For all use cases below, the **System** is the `Application` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Usage Instructions
**MSS**

1. User want to view all the available commands in the application.
2. Application show a list of available commands and instructions for all commands.
    Use Case ends

#### Use case : Add task

**MSS**

1. User request to add new tasks with its details.
2. Application add the task with specified details.  
3. Application display successful message.
Use case ends.  

**Extensions**  
1a. User specify with start date and end date.

> 1a1. User specify invalid date format.
  Application shows an error message.
  Use case ends.
  
1b. User specify with deadline.  

> 1b1. User specify invalid date deadline format.
   Application shows an error message.
   Use case ends.

#### Use case : Find tasks with specific keyword

**MSS**

1. User request to find task with specific keyword.
2. Application show the list of task that its names & tag contain the keyword.
Use case ends.  

**Extensions**  
2a. The list is empty.

> 2a1. Application show an error message.
   Use case ends.

#### Use case : List all task

**MSS**

1. User request to list all the tasks.
2. Application show the list of task with respective details.  
Use case ends.  

**Extensions**  
2a. The list is empty.

> 2a1. Application show error message.
   Use case ends.
   
#### Use case: Delete task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to delete a specific task in the list.
4. Application request confirmation from the user.
5. User confirm the confirmation.
4. Application deletes the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.

#### Use case: edit task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to edit a specific task in the list.
4. Application edit the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.

#### Use case : Undo command

**MSS**
1. User request to undo command by a specific number.
2. Application request confirmation from the user.
3. User confirm the confirmation.
2. Application undo the command repeatedly based on the given number.
Use case ends.

**Extensions**
1a. The given number exceed the total number of tasks.
> Application will show an error message.
Use case ends.

#### Use case : Redo command

**MSS**
1. User request to redo command by a specific number.
2. Application request confirmation from the user.
3. User confirm the confirmation.
4. Application redo the command repeatedly based on the given number.
Use case ends.

**Extensions**
1a. The given number exceed the total number of undo commands.
> Application will show an error message.
Use case ends.

#### Use case: Complete task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to complete a task.
4. Application complete the task and remove it from the current task list and add into the completed task list.<br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.
  
#### Use case: Uncomplete task

**MSS**

1. User requests a list of completed tasks or find compelted task with keyword.
2. Application shows a list of completed tasks.
3.  User requests to uncomplete a task.
4. Application request confirmation from the user.
5. User confirm the confirmation.
6. Application uncomplete the task and remove it from the completed task list and add it back to the current task list. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends.

3a. The given index is invalid.

> 3a1. Application shows an error message.
  Use case ends.

#### Use case: Remove tag from a task

**MSS**

1. User requests a list of tasks or find task with keyword.
2. Application shows a list of tasks.
3. User requests to remove the tag of a specific task in the list.
4. Application request confirmation from the user.
5. User confirm the confirmation.
6. Application deletes the tag that is associated to the task. <br>
Use case ends.  

**Extensions**  

2a. The list is empty.

> Use case ends

3a. The given index is invalid.

> 3a1. Application shows an error message  
  Use case ends.

#### Use case: Sorting of tasks

**MSS**

1. User request to sort the list of tasks based on either start date, end date, deadline or alphabetical order.
2. Application sort the tasks according to the user preference. <br>
Use case ends.

#### Use case : Alias

**MSS**

1. User request to set alias for specific command.
2. Application set the Alias for the command.<br>
Use case ends.

**Extensions**

1a. The given alias is already set.
> 1a1. Application will show an error message.
Use case ends.

1b. The given command is invalid.
>1b1. Application will show an error message.
Use case ends.

#### Use Case: Remove Alias

**MSS**

1. User request for a list of alias that is currently set.
2. Application show a list of alias and its respective command.
3. User request to remove alias from the specific command.
4. Application remove the alias of the specific command.<br>
Use case ends.

**Extensions**
1a. The list is empty.
> Use case ends.

3a. The given index of the alias is invalid.
> 3a1. Application will show an error message.
Use case ends.

3b. The given command is invalid.
> 3b1. Application will show an error message.
Use case ends.

#### Use case: Change theme of the Application

**MSS**

1. User requests to change the theme of the application.
2. Application shows a list of available themes.
3. User request the specific theme in the list.
4. Application change the theme. <br>
Use case ends.  

**Extensions**  

3a. The given index is invalid.

> 3a1. Application shows an error message.  
  Use case ends.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 persons.
3. Should come with automated unit tests and open source code.
4. Should favor DOS style commands over Unix-style commands.
5. User-friendly interface

{More to be added}

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

**##### Private contact detail**

> A contact detail that is not meant to be shared with others

## Appendix E : Product Survey

**{TODO: Add a summary of competing products}**
