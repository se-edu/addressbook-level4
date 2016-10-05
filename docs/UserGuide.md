# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `KeyboardWarrior.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your KeyboardWarrior.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**` Do CS2103 Tutorial` : 
     adds a task `Do CS2103 Tutorial` KeyboardWarrior.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`
 
#### Adding a keep in view:
Adds a floating task to the KeyboardWarrior<br>
Format: `TASK`

Examples: 
* `Buy Chocolate`
* `Download Github`
* `Download Eclipse`

#### Adding a Deadline:
Adds a floating task to the KeyboardWarrior<br>
Format: `TASK by [DATE] [TIME]`

Examples: 
* `Do CS2103 Tutorial by Thursday`
* `Submit Lab report by 020314 2030`

#### Adding a calendar task: `add`
Adds a specific task to the KeyboardWarrior that will be able to show any combinations of the following parameters:<br>

Format: `add DATE TIME to [TIME] TASK @ [VENUE]`

Examples: 
* `add 010116 1810 Go to the mall`
* `add Sunday 0210 to 0300 Group Meeting @I3 MR9`
* `add Fri 1410 to 1600 Basketball Tryouts @13 Computing Dr 117417`

#### search all task: `find`
Search all task to the KeyboardWarrior<br>
Format: `find [DATE] [TIME] [KEYWORD] @[VENUE]`

Examples: 
* `find 010215`
* `find 030216 2345`
* `find basketball`
* `find @I3`

#### Show Calendar : `show`
Shows a calendar in the KeyboardWarrior.<br>
Format: `show [TIMEFRAME]`

Examples: 
* `show week`
* `show month`
* `show Saturday`
* `show 0405`
* `show Feb`

#### Complete a Keep in View or Deadline : `complete`
Mark a todo as complete and delete it from the KeyboardWarrior. Irreversible.<br>
Format: `complete INDEX`

> Complete and delete the todo at the specified `INDEX`. 
   The index refers to the index alphabet shown on the Keep in View.<br>
  The index **must be a alphabet** a, b, c, ...
  The index refers to the index number shown on the Deadline.<br>
  The index **must be a positive integer for Deadline** 1, 2, 3, ...

Example: 
* `complete b`<br>
   Mark the item b as complete and delete it from the keep in view.
* `complete 2`<br>
   Mark the 2nd item as complete and delete it from the todo.

#### Deleting a Keep in View or Deadline : `delete`
Deletes the specified keep in view or deadline from the KeyboardWarrior. Irreversible.<br>
Format: `delete INDEX`

> Complete and delete the todo at the specified `INDEX`. 
   The index refers to the index alphabet shown on the Keep in View.<br>
  The index **must be a alphabet** a, b, c, ...
  The index refers to the index number shown on the Deadline.<br>
  The index **must be a positive integer for Deadline** 1, 2, 3, ...

Example: 
* `delete b`<br>
  Deletes item b in the keep in view.
* `delete 2`<br>
  Deletes the 2nd item in the deadline.

#### Remove a task from the calendar : `remove`
Remove a specified calendar task from the KeyboardWarrior. Irreversible.<br>
Format: `remove INDEX`

> Deletes the calendar task at the specified `INDEX`
  The index refers to the index number shown on the calendar<br>
  The index **must be a positive integer** 1, 2, 3, ...

Example: 
* `remove 2`<br>
  Deletes the 2nd item in the calendar.

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous KeyboardWarrior folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Help | `help`
(Keep in View)| `TASK`
(Deadline)| `TASK by [DATE] [TIME]`
Add | `add DATE TIME to [TIME] TASK @ [VENUE]`
Complete | `complete INDEX`
Remove | `remove INDEX`
Delete | `delete INDEX`
Find | `find [DATE] [TIME] [KEYWORD] @[VENUE]`
Show | `show`
Exit | `exit`
