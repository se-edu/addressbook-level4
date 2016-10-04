# User Guide

## Table of Contents
<!-- MarkdownTOC -->

1. &nbsp; &nbsp; [Introduction](#1-introduction)
2. &nbsp; &nbsp; [Quick Start](#2-quick-start)
3. &nbsp; &nbsp; [Getting Started](#3-getting-started)

  1. &nbsp; &nbsp; [Add Tasks](#31-add-tasks)
  2. &nbsp; &nbsp; [Delete Tasks](#32-delete-tasks)
  3. &nbsp; &nbsp; [Undo and Redo](#33-undo-and-redo)
  4. &nbsp; &nbsp; [View](#34-view)
  5. &nbsp; &nbsp; [Edit Tasks](#35-edit-tasks)
  6. &nbsp; &nbsp; [Mark completed tasks](#36-mark-completed-tasks)
  7. &nbsp; &nbsp; [Suggest available timeslots](#37-suggest-available-timeslots)
  8. &nbsp; &nbsp; [Find](#38-find)
  9. &nbsp; &nbsp; [Exit](#39-exit)
  10. &nbsp; &nbsp; [Help](#310-help)
 
4. &nbsp; &nbsp; [Smart Features](#4-smart-features)

  1. &nbsp; &nbsp; [FlexiCommand](#41-flexicommand)
  2. &nbsp; &nbsp; [Saving the data](#42-saving-the-data)

<!-- /MarkdownTOC -->

## 1. Introduction
SmartyDo is a to-do-list application. It is simple, non-intrusive and extremely easy to use. If you don't like to remember your tasks or have some trouble remembering them, but doesn't want an application thats too bulky or that doesn't integrate naturally to your desktop, SmartyDo is the application for you!

## 2. Quick Start
1. **Launch SmartyDo** Simply double-click on the `SmartyDo.jar` file to start SmartyDo. You will be greeted with a simple interface that has four parts a CalendarBox, a VisualBox, a MessageBox and a command bar. This command bar is where you enter short commands to tell SmartyDo what to do. 

2. **Letting SmartyDo do the remembering for you** You can simply type the commands you want SmartyDo to execute for you in the command bar. Type “help” or “help [command]” for more information.

3. **Let’s get started**

## 3. Getting Started
**Command Format**<br>
Words in `UPPER_CASE` are the parameters.<br>
Items in `SQUARE_BRACKETS` are optional.<br>
Items with `...` after them can have multiple instances.<br>
The order of parameters is flexible<br>

### 3.1. **Add Tasks:**
Adds a Task to SmartyDo which requires a description of the task `TASK_NAME`.<br> 
You can specify the `[priority]` of the task which enables you to `view` tasks by their importance level for the day.<br> 
If the starting time and/or the deadline of the task is known , you can include it by specifying `[StartTime]` and `[EndTime]` <br>
Since tasks may be of recurring nature, `[Recur]` frequency can be indicated by entering the frequency.  

Summary of the parameters and their usage

| Parameter     |     Usage     |  	    |
| ------------- |:-------------:| -----:|
| `TASK_NAME`   |    _**/d**_   |       |
| `[Priority]`  |    _**/p**_   |       |
| `[StartTime]` |    _**/st**_  |       |
| `[EndTime]`   |  _**/et**_    |		|
| `[Recur]`     | _**/freq X**_ | daily = 1, weekly = 2 , fortnightly = 3, monthly = 4| 

Examples:

<img src="images/AddBasicCmd.png" width="500"><br>

- `add TASK_NAME [Priority][Tag][StartTime][EndTime][Recur]`

> The application **does not support adding** new `Tasks` which **conflicts** with your current schedule. 
> However, with priority system you may wish to reschedule the lower priority task to a free time slot.

### 3.2. **Delete Tasks:**
Deletes the specified Task from the SmartyDo.

Format: `delete INDEX`

Deletes the task at the specified INDEX. The index refers to the index number shown in the VisualBox.

The index must be a positive integer 1, 2, 3, ...

Examples:

<img src="images/Delete1.png" width="500"><br>
- `view 11/10/2016`
- `delete 1`
- Deletes the 1st task of 11/10/2016.


- `find homework`
- `delete 1`
- Deletes the 1st task in the results of the find command.

### 3.3. **Undo and Redo:**

With `undo`, you are allowed to reverse your previous changes sequentially while `redo` allows you to reverse the change done by `undo`.

Example:
- `add Add /d Archery  Introduction /st 0900 /loc Multipurpose Field `
- `delete 1`
- `undo`
 
SmartyDo updates your schedule where it was before you executed an undoable action. If you enter `redo`, the most recent `undo` change is reverted.  

<img src="images/Redo.png" width="500"><br>

- `redo`
> If you enter any undoable command after entering `redo` or `undo`, your history of actions would be _**removed**_. <br>
> You need to have recently asked **SmartyDo** to perform **at least one undoable command** to execute `undo`. <br>
> As for `redo`, it requires the application to have executed at least one `undo` action recently  

| Undoable Commands |
| ----------------- |
| `add`	   	    |
| `delete`	    |
| `edit`	    |  
| `mark`	    |

> SmartyDo **does not store** history of actions in your computer.
> The history of actions are initialized to empty stack when SmartyDo is opened.

### 3.4. **View:**
View the task/day/month/year/list identified by the parameter.

Format: `view PARAM`

Examples:
- `view 11/10/2016`
- `view year 2016`
- `view month 10`
- `view incomplete tasks`

### 3.5. **Edit Tasks:**
Edit the specified Task from the SmartyDo.

Format: `edit INDEX FIELD NEW_VALUE`

Edits the task at the specified INDEX. The index refers to the index number shown in the VisualBox.

The index must be a positive integer 1, 2, 3, ...

Examples:

- `view 11/10/2016`
- `edit 2 time 1400`
- Edits the 2nd task of 11/10/2016. Changes its deadline to 1400 .


- `find homework`
- `edit 1 time 1400`
- Edits the 1st task in the results of the find command. Chanes its deadline to 1400.

### 3.6. **Mark completed tasks:**
Marks the task at the specified INDEX as complete. The index refers to the index number shown in the VisualBox.

Example:

- 'view 11/10/2016'
- 'Done 2'
- Marks the 2nd task of 11/10/2016 as completed task.

Format: Done INDEX

### 3.7. **Suggest available timeslots:**
Recommends the list of time blocks for a specified task based on task duration and space availability.

Format: suggest [date][duration]

### 3.8. **Find:**
Finds tasks whose names contain any of the given keywords.

Format: `find KEYWORD [MORE\_KEYWORDS]`
- The search is case sensitive. e.g cs will not match CS
- The order of the keywords does not matter. e.g. project cs2103  will match cs2103 project
- Only the task's name is searched.
- Only full words will be matched e.g. Math will not match Mathematics
- Tasks matching at least one keyword will be returned (i.e. OR search). e.g. Homework will match CS3230 Homework


### 3.9. **Exit:**
Exits the program.

Format: `exit`

### 3.10. **Help:**
Format: `help`

Help is also shown if you enter an incorrect command e.g. abcd

## 4. Smart Features

### 4.1. **FlexiCommand**
It is okay if you cannot remember the syntax entirely! As long as you remember the keyword some reshuffling of the parameters entered is fine. Our program will ask you for confirmation if we are unsure what you want.

### 4.2. **Saving the data**
SmartyDo data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.
