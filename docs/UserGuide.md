# User Guide

* [Description](#description)
* [String the program](#starting-the-program)
* [Features](#features)
* [Command Summary](#command-summary)

##Description
(Project Name) is a Command Line Interface Task Management Application that helps you manage your workflow. There are two main parts in the app: Todo List in which you can plan what tasks are to be done for the day, and Calendar that you indicate deadlines of the tasks or events to attend weekly/monthly. Data in Todo List and Calendar are synchronized. For instance, if a task is done before a deadline, it will be indicated as `done` in Calendar as well. At each starting time of tasks in Todo List, the app will give a notification that you should start working.

## Starting the program

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `todoList.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your TodoList.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 
4. Type the command in the command box and press <kbd>Enter</kbd> to execute it.<br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Refer to the [Features](#features) section below for details of each command.<br>

## Features

#### Viewing help : `help`
Show features and respective commands of the app.<br>
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a task: `add_task`
Adds a task to the todo-list.<br>
Format: `add_task TASK_NAME p/PRIORITY s/START_TIME e/END_TIME`<br>
Example: `add_task Assignment 3 p/1 s/1400 e/1600`

> Tasks will be rearranged in the Todo-List based on their priority

#### Deleting a task : `delete_task`
Delete a task with given index number.<br>
Format: `delete_task INDEX_NUMBER`<br>
Example: `delete_task 1`

#### Marking a completed task : `done`
Mark a task with given index number as done.<br>
Format: `done INDEX_NUMBER`<br>
Example: `done 1`


#### Adding an event: `add_event`
Adds an event/deadline to the todo-list.<br>
Format: `add_task EVENT_NAME d/DATE s/START_TIME e/END_TIME`<br>
Example(event): `add_event Tim's birthday party d/25-12-2016 s/1400 e/1600`<br>
Example(deadline): `add_event Assignment 3 deadline d/25-12-2016 s/1600 e/1600`

> Deadline has the same starting and ending time
> DATE format is DD-MM-YYYY

#### Deleting an event : `delete_event`
Delete an event starting with given name.<br>
Format: `delete_event NAME`<br>
Example: `delete_event Tim`s birthday party`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
Todo-list data are saved in the hard disk automatically after any command that changes the data.<br>


## Command Summary

Command | Format  
------------------ | :-------- 
Add Task	| `add_task TASK_NAME p/PRIORITY s/START_TIME e/END_TIME`
Delete Task	| `delete_task INDEX_NUMBER`
Done Task	| `done INDEX_NUMBER`
Add Event	| `add_event Tim`s birthday party d/25-12-2016 s/1400 e/1600`
Delete Event	| `delete_event NAME`
Help		| `help`
Exit		| `exit`


