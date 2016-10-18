# User Guide

## Table of Contents
1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Getting Started](#3-getting-started)
  1. [Help](#31-help)
  2. [Add Tasks](#32-add-tasks)
  3. [View](#33-view)
  4. [Edit Tasks](#34-edit-tasks)
  5. [Undo and Redo](#35-undo-and-redo)
  6. [Delete Tasks](#36-delete-tasks)
  7. [Mark completed tasks](#37-mark-completed-tasks)
  8. [Suggest available timeslots](#38-suggest-available-timeslots)
  9. [Find](#39-find)
  10. [Exit](#310-exit)
4. [Smart Features](#4-smart-features)
  1. [FlexiCommand](#41-flexicommand)
  2. [Saving the data](#42-saving-the-data)

<!-- /MarkdownTOC -->

## 1. Introduction
SmartyDo is a **to-do-list** application. With SmartyDo, forgetting upcoming deadlines and sleepless nights over incomplete tasks are a thing of the past. SmartyDo **increases your efficiency** by showing the lists of tasks that can be completed simultaneously. Treat SmartyDo like your personal assistant and just focus on **completing your tasks**!

## 2. Quick Start
1. **Launch SmartyDo**: Simply double-click on the `SmartyDo.jar` file to start SmartyDo. You will be greeted with a simple interface that has four parts a **CalendarBox**, a **VisualBox**, a **MessageBox** and a **Command Bar**. Screenshot below shows SmartyDo's interface.

<img src="images/WelcomeScreen.png" width="500"><br>

**Command Bar** is where you enter short commands to tell SmartyDo what to do.<br>
**CalendarBox** is the box that shows calender at the top right corner.<br>
**VisualBox** is the box where list of tasks are shown.<br>
**MessageBox** is shows the result of your command.<br>


2. **Letting SmartyDo do the remembering for you**: You can simply type the commands you want SmartyDo to execute for you in the command bar. Type “help” or “help [command]” for more information.

## 3. Getting Started
**Command Format**<br>
Words in `lower case` are the command.<br>
Words in `upper case` are the parameters.<br>
Items in `square brackets` are optional.<br>
Items with `...` after them can have multiple instances.<br>
The order of parameters is flexible<br>

### 3.1. **Requesting Help From SmartyDo**
You can always use `help` command when you forgot the commands and its format. Help is also shown if you enter an incorrect command e.g. abcd <br>

**Example:**<br>
- Enter `help` into command box in order to view the list of commands.

### 3.2. **Adding Task Into SmartyDo**
To add a task into SmartyDo, you need to use `add` command. There are number of paramaters you can enter with `add` command.<br>

Here is the summary of the parameters and their usage: 

| Parameter     |     Usage     |   Format Requirements    |
| ------------- |:-------------:| -----:|
| `TASK_NAME`   |    _**/n**_   |       |
| `[START_TIME]` |    _**/st**_  |       |
| `[END_TIME]`   |  _**/et**_    |        |
| `[TAG]`         | _**#**_ | alphanumeric |
| `[LOCATION]` | _**/loc**_ |  alphanumeric          |
| `DATE` | **/d** | DDMMYY |

- `TASK_NAME` is the name of the task and this parameter is compulsary.
- `[START_TIME]` and `[END_TIME]` is the starting time and ending time of the task respectively. You may consider to use these parameters when starting time and/or deadline is known.
- `[TAG]` is the characteristic you can add to the task. Such tags can be "Urgent", "HighPriority" and etc.
- `[LOCATION]` is the place of task being done. You can use this parameter to remind you where to go to complete the task.
- `DATE` is the date of the task and this parameter is compulsary.

Format : `add /n TASK_NAME /d DATE #TAG /loc LOCATION /st START_TIME /et END_TIME`
> You don't have to enter the optional parameters when you don't need them. The order of the parameters are not fixed. You can enter the parameters in any order. For example, `add /st START_TIME #TAG /n TASK_NAME /d DATE` is also correct format.

**Example:**<br>
Lets say you want to add task named "User Guide" which is due 12 October 2016 with tag "CS2103". All you need to do is entering `add #CS2103 /n User Guide /d 121016` into command bar. The screenshot of this example is shown below.

<img src="images/AddBasicCmd.png" width="500"><br>

After entering the command, MessageBox will show you task is successfully added into SmartyDo and you will see the updated list of task in the VisualBox.

### 3.3. **View Details Of Specific Task**
View the task/day/month/year/list identified by the parameter. A full detailed description will appear in a pop up window.

Format: `view PARAM`

**Example:**<br>
Lets say you want to know detailed information about tasks in 12 October 2016. All you need to do is enter `view 121016` into command box. The screenshot of this example is shown below.


<img src="images/view.png" width="500"><br>

After entering the command, a popup window will appear and show detailed description about tasks in 12 October 2016.

### 3.4. **Finding Specific Tasks**
If you want to find tasks that contain specific keyword in their name, you can use `find` command. `find` command is a command that will list all the tasks matching atleast one keyword. You can enter more than one keyword for `find` command.

Format: `find KEYWORD [MORE_KEYWORDS]`

**Example:**<br>


> Beware that keywords are case sensitive and only the task's name is searched. However, the order of the keywords does not matter. e.g. `find cs2103 project` is same as `find project cs2103`

### 3.5. **Editing Task Details**
You might want to edit details of a task for several reasons. For example, when deadline was extended you will need to update the SmartyDo for this change. Using `edit` command will solve this problem.

Format: `edit INDEX FIELD NEW_VALUE`

You should first find the task that you wish to edit by using the `view` or `find` command to avoid editing the wrong task entry. <br>
Edits the task at the specified INDEX. The index refers to the index number shown in the VisualBox.<br>


The index must be a positive integer 1, 2, 3, ...

Examples:

<img src="images/edit.png" width="500"><br>

- `edit 1 time 1400`
- Edits the 1st task of the current list. Changes its deadline to 1400 .


- `find homework`
- `edit 1 time 1400`
- Edits the 1st task in the results of the find command. Chanes its deadline to 1400.

### 3.6. **Undo and Redo:**

With `undo`, you are allowed to reverse your previous changes sequentially while `redo` allows you to reverse the change done by `undo`.

Example:

<img src="images/Undo.png" width="500"><br>
- `edit 1 time 1400`
- `undo`

SmartyDo updates your schedule where it was before you executed an undoable action. If you enter `redo`, the most recent `undo` change is reverted.  

<img src="images/Redo.png" width="500"><br>

- `redo`

> If you enter any undoable command after entering `redo` or `undo`, your history of actions would be _**removed**_. <br>
> You need to have recently asked **SmartyDo** to perform **at least one undoable command** to execute `undo`. <br>
> As for `redo`, it requires the application to have executed at least one `undo` action recently  

| Undoable Commands |
| ----------------- |
| `add`         |
| `delete`      |
| `edit`      |  
| `mark`      |

> SmartyDo **does not store** history of actions in your computer.
> Your history of actions resets when SmartyDo is launched.


### 3.7. **Delete Tasks:**
Deletes the specified Task from the SmartyDo.

Format: `delete INDEX`

Deletes the task at the specified INDEX. The index refers to the index number shown in the VisualBox.

The index must be a positive integer 1, 2, 3, ...

Examples:

<img src="images/Delete1.png" width="500"><br>
- `view 12/10/2016`
- `delete 1`
- Deletes the 1st task of 11/10/2016.


- `find homework`
- `delete 1`
- Deletes the 1st task in the results of the find command.

### 3.8. **Mark completed tasks:**
Marks the task at the specified INDEX as complete. The index refers to the index number shown in the VisualBox.

Example:
<!---
To link to previous "delete" command in final user guide, insert undo command : purpose for making a story
-->

<img src="images/complete.png" width="500"><br>

- `view 12/10/2016`
- `Done 1`
- Marks the 1st task of 11/10/2016 as completed task.

Format: Done INDEX

### 3.9. **Suggest available timeslots:**
Recommends the list of time blocks for a specified task based on task duration and space availability.

Format: suggest [date][duration]


### 3.10. **Exit:**
Exits the program.

Format: `exit`

## 4. Smart Features

### 4.1. **FlexiCommand**
It is okay if you cannot remember the syntax entirely! As long as you remember the keyword some reshuffling of the parameters entered is fine. Our program will ask you for confirmation if we are unsure what you want.

### 4.2. **Saving the data**
SmartyDo data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.
