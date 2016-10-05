# User Guide

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

#### Adding a task: `add`
Adds a task to the todo-list.<br>
Format: `add TASK_NAME s/START_TIME e/END_TIME`
Example: `add Assignment 3 s/1400 e/1600`

> Persons can have any number of tags (including 0)

#### Editing a task: `edit`
Edit a task info of a chosen index number.<br>
Format: `edit INDEX_NUMBER n/TASK_NAME s/START_TIME e/END_TIME`
Example: `edit 1 n/Assignment 2 s/1200 e/1400`

#### Marking a completed task : `done`
Mark a task with given index number as done.<br>
Format: `done INDEX_NUMBER`
Example: `done 1`

#### Deleting a task : `delete`
Delete a task with given index number.<br>
Format: `delete INDEX_NUMBER`
Example: `delete 1`

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
Todo-list data are saved in the hard disk automatically after any command that changes the data.<br>


## Command Summary

Command | Format  
--------- | :-------- 
Add	| `add TASK_NAME s/START_TIME e/END_TIME`
Edit	| `edit INDEX_NUMBER n/TASK_NAME s/START_TIME e/END_TIME`
Delete	| `delete INDEX_NUMBER`
Done	| `done INDEX_NUMBER`
Help	| `help`
Exit	| `exit`

