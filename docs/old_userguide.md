## User Guide

[1. Introduction](#section-1)

[2. Quick Start](#section-2)

[3. Features](#section-3)

[3.1. Viewing help: `help`](#section-3.1)

[3.2. Adding a wish: `add`](#section-3.2)

[3.3. Listing all wishes: `list`](#section-3.3)

[3.4. Viewing your saving history: `history`](#section-3.4)

[3.5. Editing a wish: `edit`](#section-3.5)

[3.6. Locating a wish: `find`](#section-3.6)

[3.7. Deleting a wish: `delete`](#section-3.7)

[3.8. Ranking your wishes: `rank`](#section-3.8)

[3.9. Reordering the priority of your wishes: `swap`](#section-3.9)

[3.10. Save money for a specific wish: `save`](#section-3.10)

[3.11. Undoing previous command: `undo`](#section-3.11)

[3.12. Redoing the previously undone command: `redo`](#section-3.12)

[3.13. Clearing all entries: `clear`](#section-3.13)

[3.14. Exiting the program: `exit`](#section-3.14)

[4. FAQ](#section-4)

[5. Command Summary](#section-5)

### 1. Introduction <a id="section-1"></a>
SaveGoals (SG) is a piggy bank in a digital app form, made for people who need a user-friendly solution for keeping track of their disposable income. Not only does SG keep records of your savings, it also allows users to set items as goals for users to work towards, serving as a way to cultivate the habit of saving. SG is made for people who have material wants in life, but are uncertain of purchasing said wants, by helping them to reserve disposable income so that they know they can spend on their wants. So what are you waiting for? Go to Section 2, “Quick Start” and start saving!

### 2. Quick Start <a id="section-2"></a>
1. Ensure you have Java version 9 or later installed on your Computer.
2. Download the latest [savegoals.jar]() here.
3. Copy the file to the folder you want to use as the home folder for your Save Goals app.
4. Double-click the .jar file to start the app. The GUI should appear in a few seconds.
image::Ui.png[width="790"]
5. Type the command in the command box and press Enter to execute it.
    *  Typing help and pressing enter will bring up a help window.
6. Here are some commands that you can try:
    * `list`: Lists all the items you have set as wishes (sorted by due date).
    * `save AMOUNT`
        * `save 20.50`: adds $20.50 to your nearest goal that’s in-progress.
    * `add n/WISH_NAME p/PRICE t/[d/TIME_GIVEN]/[d/END_DATE]`
        * `add n/uPhoneXX p/1000 d/5m`:  adds an item “uPhoneXX” as a goal to be completed in 5 months.
        * `add n/uPhoneXX p/1000 d/12112018`:  adds an item “uPhoneXX” as a goal that costs $1000 and sets it to be completed by 12-11-2018.
    * `clear`: clears view.
    * `help`: displays list of command with usage.
    * `exit`: exits the app command.
7. Refer to [Section 3, “Features”](#section-3) for details of each command.

### 3. Features <a id="section-3"></a>

---
#### Command Format
>* Words in `UPPER_CASE` are the parameters to be supplied by the user
    * e.g. in `add WISH`, `WISH` is a parameter which can be used as add iPhone.
* Items in square brackets are optional
    * e.g. in `save AMOUNT [INDEX]`, `INDEX` is an optional parameter, since the save command can be used as `save 40`.
* The `/` symbol between parameters means that you can use either of the parameters types in the command
    * e.g. in `add WISH PRICE [TIME_GIVEN]/[START_DATE to END_DATE]`, you provide either the `TIME_GIVEN` parameter, `START_DATE` and `END_DATE` parameters, or none of them.
>* Items with `...` after them can be used multiple times.
---

#### 3.1 Viewing help: `help` <a id="section-3.1"></a>
Format: `help`


#### 3.2 Adding a wish: `add` <a id="section-3.2"></a>
Adds a wish to your wish list
Format: `add n/WISH_NAME p/PRICE t/[d/TIME_GIVEN]/[d/END_DATE]`

---
>* `[END_DATE]`: Specified in _ddmmyyyy_ format. Separators are optional. Allowed separators are either dashes ‘-’, forward slashes ‘/’ or periods ‘.’
>* `[TIME_GIVEN]`: Specified in terms of days, weeks or months or years, prefixes marking such time periods are _‘d’, ‘w’, ‘m’_ and _‘y’_ respectively.
---

Examples:
* `add n/smallRice p/999 d/2d`
* `add n/kfcBook_13inch p/2300 d/6m3w`
* `add n/prinkles p/1.95 d/24/04/2020`


#### 3.3 Listing all wishes: `list` <a id="section-3.3"></a>
Shows a list of all the goals you have set, sorted by date by default.
Format: `list`


#### 3.4. Viewing your saving history: `history` <a id="section-3.4"></a>
Shows a history of wishes you have accomplished, from newest to oldest.
Format: `history`


#### 3.5 Editing a wish: `edit` <a id="section-3.5"></a>
Edits an existing wish in the list
Format: `edit INDEX [n/WISH_NAME] [p/PRICE] [d/TIME_GIVEN]/[d/END_DATE] [t/TAG]`

---
>* Edits the wish at the specified `INDEX`. `INDEX` refers to the index number shown in the displayed list of goals. `INDEX` must be a positive integer 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the wish will be removed i.e. adding of tags is not cumulative.
>* You can remove all tags by typing `t/` without specifying any tags after it.
---

Examples:
* `edit 1 n/Macbook Pro t/Broke wishes`
Edits the name of the wish and the tag of the 1st goal to be Macbook Pro and Broke wishes respectively
* `edit 2 p/22 d/22w`
Edits the price and time given to accomplish the 2nd goal to 22 (in the chosen currency) and 22 weeks respectively.


#### 3.6 Locating a wish: `find` <a id="section-3.6"></a>
Finds wishes which satisfy the given search predicate.
Format: `find SEARCH_PREDICATE [MORE_SEARCH_PREDICATES]`

---
>* The user can search using the following search predicates:
    * `NAME`
    * `DATE`
    * `PRICE`
    * `TAG`
* `NAME`is the default search predicate.
* The search is case insensitive e.g. watch will match Watch.
* Only full words will be matched e.g. wat will not match watch.
* Goals matching at least one keyword will be returned e.g. watch will return apple watch, pebble watch.
* `DATE` is the creation date of the wish. It should match the correct format as specified in [Section 3.2 Date Format]().
>* `PRICE` is the sale price of the item. It should be a positive number corrected to the smallest denomination of the currency.
---

Examples:
* `find 22d`
Returns wish with stipulated time given of 22 days.
* `find watch t/broke wishes`
Returns any wish with name containing watch, with tag broke wishes.


#### 3.7 Deleting a wish: `delete` <a id="section-3.7"></a>
Deletes the specified wish from the list.
Format: `delete INDEX`

---
>* `INDEX` refers to the index number shown in the displayed list.
>* `INDEX` must be a positive integer 1, 2, 3...
---

Examples:
* `list`
`delete 2`
Deletes the 2nd wish in the list.
* `find watch`
`delete 1`
Deletes the 1st wish in the results of the find command (if any).


#### 3.8 Ranking your wishes: `rank` <a id="section-3.8"></a>
Ranks the wishes by specified wish field so that future savings are allocated in the order or ranking.
Format: `rank WISH_FIELD [RANK_ORDER]`

---
> `RANK_ORDER` can be `-a`(ascending) or `-d`(descending). By default it is set to ascending.
---

Examples:
* `rank Date -d`
Ranks the wishes in descending order of date created.
* `rank Price`
Ranks the wishes in ascending order of sale price


#### 3.9 Reordering the priority of your wishes: `swap` <a id="section-3.9"></a>
Reorders the priority of wishes by swapping wishes at the specified indices.
Format: `reorder OLD_INDEX NEW_INDEX `

---
>* A smaller numerical value for index indicates higher priority.
>* Indices must be a positive integer 1, 2, 3...
---

Examples:
* `swap 7 1`
Swaps wishes at index 7 and index 1
* `swap 1 8`
Swaps wishes at index 1 and index 8


#### 3.10 Save money for a specific wish: `save` <a id="section-3.10"></a>
Channels savings for a specified wish.
Format: `save AMOUNT [INDEX]`

---
>* `INDEX` should be a positive integer 1, 2, 3…
* `AMOUNT` should be a positive value to the smallest denomination of the currency.
>* If no `INDEX` is specified, money will be transferred to the wish which has the closest due date.
---

Examples:
* `save 1000 1`
Adds $1000 into the item at index 1.


#### 3.11 Undoing previous command: `undo` <a id="section-3.11"></a>
Restores SaveGoals to the state before the previous undoable command was executed.
Format: `undo`

---
> Undoable commands: commands that modify SaveGoals content (`add, delete, edit, save, rank`)
---

Examples:
* `delete 1`
`list `
`undo`
Reverses the `delete 1` command.
* `list`
`find 22d`
`undo`
The `undo` command fails as there are no undoable commands executed previously.
* `delete 1`
`clear`
`undo`
`undo`
The first `undo` reverses the `clear` command. The second `undo` reverses the `delete 1` command.

#### 3.12 Redoing the previously undone command: `redo` <a id="section-3.12"></a>
Reverses the most recent `undo` command.
Format: `redo`

Examples:
* `delete 1`
`list`
`undo`
`redo`
`undo` reverses the `delete 1` command. Then, the `redo` command reapplies the `delete 1` command.
* `delete 1`
`redo`
The `redo` command fails as there are no `undo` commands executed previously.


#### 3.13 Clearing all entries: `clear` <a id="section-3.13"></a>
Clears all entries from SaveGoals.
Format: `clear`


#### 3.14 Exiting the program: `exit` <a id="section-3.14"></a>
Exits the program.
Format: `exit`


### 4. FAQ <a id="section-4"></a>
* **Q**: How do I transfer my data to another computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SaveGoals folder.

### 5. Command Summary <a id="section-5"></a>
* Help: `help`
* Add: `add n/WISH_NAME p/PRICE t/[d/TIME_GIVEN]/[d/END_DATE]`
e.g. add n/kfcBook_13inch p/2300 d/6m3w
* List: `list`
* History: `history`
* Edit: `edit INDEX [n/WISH_NAME] [p/PRICE] [d/TIME_GIVEN]/[d/END_DATE] [t/TAG]`
e.g. edit 1 n/Macbook Pro t/Broke wishes
* Find: `find SEARCH_PREDICATE [MORE_SEARCH_PREDICATES]`
e.g. find 22d
* Delete: `delete INDEX`
e.g. delete 1
* Rank: `rank WISH_FIELD [RANK_ORDER]`
e.g. rank Date -d
* Swap: `swap OLD_INDEX NEW_INDEX`
e.g. swap 1 8
* Save: `save AMOUNT [INDEX]`
e.g. save 1000 1
* Undo: `undo`
* Redo: `redo`
* Clear: `clear`
* Exit: `exit`
