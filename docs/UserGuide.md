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
 
#### Adding a todo task:
Adds a floating task to the KeyboardWarrior<br>
Format: `TASK by [DATE] [TIME]`

Examples: 
* `Buy Chocolate`
* `Download Github by Tuesday`
* `Download Eclipse by 020314 2030`

#### Adding a calendar task: `add`
Adds a specific task to the KeyboardWarrior that will be able to show any combinations of the following parameters:<br>

Format: `add DATE TIME to TIME TASK @[VENUE]`

Examples: 
* `add 010116 1810 to 1900 Go to the mall`
* `add Sunday 0210 to 0300 Group Meeting @I3 MR9`
* `add Fri 1410 to 1600 Basketball Tryouts @MPSH`

#### search all task: `find`
Search all task to the KeyboardWarrior<br>
Format: `find [DATE] [TIME] [KEYWORD] @[VENUE]`

Examples: 
* `find 010215`
* `find 030216 2345`
* `find basketball`
* `find @I3`

#### Listing all persons : `list`
Shows a list of todo task in the KeyboardWarrior.<br>
Format: `list`

#### Finding all persons containing any keyword in their name: `find`
Finds persons whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples: 
* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

#### Deleting a person : `delete`
Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br> 
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

#### Select a person : `select`
Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`. 
  The index refers to the index number shown in the most recent listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples: 
* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br> 
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

#### Clearing all entries : `clear`
Clears all entries from the address book.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data 
Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
