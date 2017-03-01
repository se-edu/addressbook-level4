# Developer Guide

* [Setting Up](#setting-up)
* [Target Users](#target-users)
* [Testing](#testing)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E: Product Surveys](#appendix-e--product-surveys)


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

## Target Users
Our main target users (Jim): 
* Are office workers and power users
* Are willing to use the task manager in advanced and complicated ways
* Have many tasks at hand with varying deadlines
* Prefer to not use a mouse so that he can input tasks easily


## Testing

* In Eclipse, right-click on the `test/java` folder and choose `Run as` > `JUnit Test`

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | user | add a floating task | add tasks that need to be done ‘some-day’
`* * *` | user | add a task with deadlines | know when I must finish the task
`* * *` | user | view all the tasks by the order I added them | be more organised
`* * *` | user | delete a task | get rid of tasks I no longer want to track
`* * *` | user | check a task as done (not deleted) | know I’ve finished it
`* * *` | user | view a history of all the tasks I’ve done | check what I’ve done
`* * *` | new user | view the help guide | know more about the different commands there are and how to use them
`* * *` | user | edit my tasks | update them
`* * *` | user | undo my last command | undo mistakes/typos
`* * *` | user | add events that have a start and end time | know what my day will be like
`* * *` | user | search for tasks by deadline/description | see what task I entered
`* * *` | user | specify where to keep my tasks (file and folder) | move them around if need be
`* * *` | power user | set tags to tasks | group related tasks by tags
`* * *` | power user | search by tags | know everything I have to do related to that tag
`* *` | user | sort tasks by deadline | see quickly what task has the closest deadline
`* *` | user | list tasks that don't currently have deadlines | add deadlines to tasks without deadlines
`* *` | power user | use shortened versions of commands | save time when inputting tasks
`* *` | user | add different priorities to my tasks | know which tasks need to be done first
`* *` | user | list my tasks in priority order | see which tasks have different priorities
`* *` | user | undo multiple commands | remove changes done by a few wrong commands
`* *` | user | enter recurring tasks | not have to enter the same tasks repeatedly
`* *` | power user | map standard commands to my preferred shortcut commands | be familiar with my own  modified commands
`* *` | user | make tasks that have very close deadlines to appear as special priority | be reminded to complete them
`* *` | user | set notifications for deadlines for my tasks, so that I can get reminded to do it.
`* *` | power user | set roughly how much time it requires to finish a task | know how long I need to start and finish a task and not leave it halfway
`* *` | power user | tell the program how much time I have right now | the program can assign me a task to complete in that time
`* *` | user | set tasks that are currently underway | be aware of what are the tasks I’m working on right now
`* *` | user | redo my last action | reverse accidental undo commandss
`*` | user with many tasks in the to do list | sort tasks by name | locate a task easily
`*` | user | list the tasks that are due soon | minimize chance of me missing my deadline
`*` | paranoid user | set password | protect my privacy
`*` | power user | sync tasks and events to Google Calendar | see my tasks and events alongside Google Calendar

## Appendix B : Use Cases

(For all use cases below, the **System** is the `ezDo` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Adding a task

**MSS**

1. User enters command to add task, along with relevant arguments
2. ezDo adds the task and gives confirmation
Use case ends.

**Extensions**

1a. The user enters an invalid command

> 1a1. ezDo shows an error message and prompts the user to retry
  Use case resumes at step 1
  
#### Use case: Updating a task

**MSS**

1. User specifies task to update by index along with relevant details
2. ezDo updates the task and gives confirmation
Use case ends.

**Extensions**

1a. The user enters an invalid command

> 1a1. ezDo shows an error message and prompts the user to retry
Use case resumes at step 1

1b. The indexed task does not exist

> 1b1. ezDo shows an error message and prompts the user to select another index
Use case resumes at step 1

#### Use case: Deleting a task

**MSS**

1. User enters index of task to delete
2. ezDo deletes the task and gives confirmation
Use case ends. 

**Extensions**

1a. The indexed task does not exist

> 1a1. ezDo shows an error message and prompts the user to select another index
Use case resumes at step 1

#### Use case: Specifying save location

**MSS**

1. User enters command to change save location, along with the new file path
2. ezDo updates the save location to the given path
Use case ends. 

**Extensions**

1a. The folder does not exist

> 1a1. ezDo shows an error message and prompts the user to choose another path
Use case resumes at step 1

1b. The user enters an invalid command

> 1b1. ezDo shows an error message and prompts the user to try again
Use case resumes at step 1

## Appendix C : Non Functional Requirements

1. As a new user, things are intuitive so I don’t have to waste time learning.
2. Should come with a very high level of automated testing.
3. Should have user-friendly commands.
4. Should be able to store at least 1000 tasks.


## Appendix D : Glossary
##### Mainstream OS

> Windows, Linux, Unix, OS X

##### 

## Appendix E : Product Surveys
### Trello
#### Pros
- Simple to use because it is visual and self-explanatory
- Can tag tasks with different colours and users who also use Trello
- Has all the functions that a task manager should have and can fulfill Jim's personal needs in a task manager

#### Cons
- Need to pay a sum of money monthly to access all features
- Does not sort tasks in deadline order - tasks must be moved manually

#### Verdict - NO
Trello requires most of the work to be done with a mouse (moving of tasks by mouse etc). Moreover, Trello does not sort tasks by deadline or priority. Jim will find it inconvenient if he wishes to view his tasks in a certain order.

### Sticky Notes
#### Pros
- Comes bundled free with every Microsoft PC. No need to buy.
- Simple to use, like its real-world counterpart - Post-it notes
- Can rearrange notes on desktop to place at desired position
- Can resize notes for better visibility
- Can change color of sticky notes
- Can change font, font size, and other text options

#### Cons
- Requires keyboard shortcuts to change font and font options
- Can clutter up screen with many sticky notes
- No easy way to differentiate what is most important
- No reminders (Need Windows 10 Cortana - privacy!)

#### Verdict - NO
Sticky notes is not so good for Jim. 
Jim follows 'Inbox Zero'. He no longer has to worry about an inbox full of emails, because he gets a desktop full of notes now instead. 
Sticky notes require mouse-clicks and Jim prefers typing.
Doesn't show what has to be done by what deadline.

### Wunderlist
#### Pros
- Has all the features expected from a standard to-do list application and more, such as due dates, notes, attachments, reminders and synchronisation.
- Has a keyboard shortcut to quickly input tasks (Quick Add).
- Intuitive user experience. 


#### Cons
- Quick Add still requires mouse input if you wish to specify a due date.
- Does not support tagging of tasks. 


#### Verdict - NO
Wunderlist does not suit Jim's needs.
Though it supports basic task creation with the keyboard alone, it does not allow Jim to record essential details such as the deadlines or priority without using a mouse. 

### Nirvana for GTD
#### Pros
- Android and iOS platforms are available for use.
- Simple and clean layout.
- Creating a task can be done using command prompt entry.
- Focus view is available to view tasks or projects that require immediate attention.

#### Cons
- Due time cannot be specified for any task.
- Reminders cannot be set for any task.
- Limited commands (e.g. Unable to delete a task).
- No native apps provided for OS X and Windows platforms. 

#### Verdict - NO
For Jim, the applications allows him to zoom in to tasks or projects that require immediate action. However, this application is restricting  him to add tasks or projects using the command line interface. To edit or delete a task he has to use the graphical interface which will decrease his productivity and therefore it is not suitable for him. 
