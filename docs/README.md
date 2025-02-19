# User Manual for Angela (Just a Machine)

![product screenshot](https://LonelyFort.github.io/ip/Ui.png)

## Introduction

A straightforward and convenient task tracker which you can count on to keep track of the never-ending responsibilities in your life.

## Features (V1.3)

*   Manage Todo, Deadline and Events capable of handling dates.

*   Load and store tasks onto local device.

*   Filter tasks that contains specific keywords.

*   Flag task as important and filter out only important tasks.


## Installation Instructions

1.  If you have not installed Java 17 onto your local device, you may download it from [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

2.  Download latest release of Angela.jar from [here](https://github.com/LonelyFort/ip/releases).

3.  Place the jar file into an empty folder (Preferably under the same name Angela).


## Run Instructions

Simply open the Jar file to run the program.

If running fails:

1.  Open the terminal by right click -> **Open In Terminal**.

2.  Enter the following into the terminal window to run the program: `java -jar .\Angela.jar`


### Disclaimer

As a new file will be created when the program is running, please ensure you do not set the root file to require admin permission for the program to run smoothly.

## Create Task Command

### Important Things To Note

*   All commands displayed here are designed to be case insensitive.

*   Any characters before the first whitespace in the input are treated as command. Anything after are treated as TASK\_NAME.

*   Some commands require additional information to be given after a specific mini-command (e.g deadline command requires "by:") after TASK\_NAME. These mini-commands, however, are case sensitive.

*   Please strictly adhere to the syntax for the respective commands. Abbreviations or alternate representations are not recognised by Angela, unless designed for.


### DATETIME Format

Angela accepts DATETIME formatted in the following way:

| Date Time Format | Examples |
| --- | --- |
| YYYY-MM-DD HH:mm | 2025-01-01 00:00; 1900-12-31 23:59 |

### todo

Create a todo task that only contains the description of the task.

To flag task as important, add "i" after the command.

Todo Syntax: `todo TASK_NAME` / `t TASK_NAME`

To flag todo as important by default: `todoi TASK_NAME` / `ti TASK_NAME`

Examples: `todo go to gym` / `todoi fetch kids from Kindergarten`

### deadline

Create a deadline task that contain the description of the task and the dateTime the task needs to be completed by.

To flag task as important, add "i" after the command.

Deadline Syntax:

`deadline TASK_NAME by:DATETIME` / `d TASK_NAME by:DATETIME`

To flag deadline as important by default:

`deadlinei TASK_NAME by:DATETIME` / `di TASK_NAME by:DATETIME`

Example:

`deadline complete assignment by:2025-03-07 23:59` / `deadlinei prepare pitch by:2025-02-28 16:00`

\*DATETIME format can be found under [DATETIME Format.](https://LonelyFort.github.io/ip/#datetime-format)

\*Invalid DATETIME will be flagged by Angela.

\*Mini-command "by:" is case-sensitive.

### event

Create an event task that contains the description of the event, the start dateTime and the endDate time of the event.

To flag task as important, add "i" after the command.

Event syntax:

`event TASK_NAME from:DATETIME to:DATETIME` / `e TASK_NAME from:DATETIME to:DATETIME`

To flag event as important by default:

`eventi TASK_NAME from:DATETIME to:DATETIME` / `ei TASK_NAME from:DATETIME to:DATETIME`

Example:

`event taylor swift concert from:2026-08-26 00:00 to:2026-08-30 00:00` / `eventi meteorite shower from:2025-10-22 20:00 to:2025-10-22 22:00`

\*DATETIME format can be found under [DATETIME Format.](https://LonelyFort.github.io/ip/#datetime-format)

\*Invalid DATETIME will be flagged by Angela.

\*Mini-commands "from:" and "to:" are case sensitive.

\*DATETIME in "to:" mini-command must be after the DATETIME in "from:" mini-command.

### List Of Accepted Command Abbreviations

*   todo -> t

*   todoi -> ti

*   deadline -> d

*   deadlinei -> di

*   event -> e

*   eventi -> ei


## Modify Task Commands

### Important Things To Note

*   All commands displayed here are designed to be case insensitive.

*   For task modification commands, it will read and modify the task that belongs in its respective index on the list, rather than the task description.

*   Any characters before the first whitespace in the input are treated as command. Anything after are treated as TASK\_INDEX which takes in a positive integer (number larger than 0).

*   Please strictly adhere to the syntax for the respective commands. Abbreviations or alternate representations are not recognised by Angela, unless designed for.


### check

Check a task located at a given index as completed.

Check syntax: `check TASK_INDEX` / `c TASK_INDEX`

Example: `check 2`

\*TASK\_INDEX must be a positive integer (whole number larger than 0).

### uncheck

Uncheck a task located at a given index as incomplete.

Uncheck syntax: `uncheck TASK_INDEX` / `uc TASK_INDEX`

Example: `uncheck 1`

\*TASK\_INDEX must be a positive integer (whole number larger than 0).

### delete

Delete a task located at a given index.

Delete syntax: `delete TASK_INDEX` / `del TASK_INDEX`

Example: `delete 5`

\*IMPORTANT: Performing this command will move all tasks with indexes larger than TASK\_INDEX up in the list. Please check the index of your stored task after performing this command.

\*IMPORTANT: This command is irreversible. Please make sure you have entered the correct TASK\_INDEX before proceeding.

\*TASK\_INDEX must be a positive integer (whole number larger than 0).

### marki

Mark a task located at a given index as important.

Marki syntax: `marki TASK_INDEX` / `mi TASK_INDEX`

Example: `marki 3`

\*TASK\_INDEX must be a positive integer (whole number larger than 0).

### unmarki

Remove importance of a tank located at a given index.

Unmarki syntax: `unmarki TASK_INDEX` / `ui TASK_INDEX`

Example: `unmarki 1`

\*TASK\_INDEX must be a positive integer (whole number larger than 0).

### List Of Accepted Command Abbreviations

*   check -> c

*   uncheck -> uc

*   delete -> del

*   marki -> mi

*   unmarki -> ui


## Print Task Commands

### Important Things To Note

*   All commands displayed here are designed to be case insensitive.

*   Please strictly adhere to the syntax for the respective commands. Abbreviations or alternate representations are not recognised by Angela, unless designed for.

*   For detail-less commands (e.g list), any trailing whitespaces after the command will be ignored by Angela. Do not enter any characters after these types of command as it will be treated as an invalid input.

*   For commands with details (e.g find KEYWORD), any characters before the first whitespace in the input are treated as command.


### Printed Task Interpretation

`[E*][X] TASK_NAME (from: 2025-10-10 08:00 to: 2025-10-10 10:00)`

*   `[E*]` box represents task type. T-> Todo. D-> Deadline. E-> Event. Importance of task is indicated by star symbol.

*   `[X]` box represents task completion. Completion status is represented by X symbol in the box. If incompleted, an empty box will be shown.

*   `TASK_NAME` task name as indicated when creating this task.

*   `()` represents the dateTime as indicated when creating this task, if applicable. Deadline task will be printed as `(by: DATETIME)` while event will be printed as `(from: DATETIME to: DATETIME)`.


### list

Prints out the entire task list stored in the database.

List syntax: `list` / `l`

### find

Print all tasks that contain the given KEYWORD.

Find syntax: `find KEYWORD` / `f KEYWORD`

Example: `find assignment`

\*KEYWORD is case sensitive.

### findi

Print all important tasks on the list, or the ones that contain the given KEYWORD.

Findi syntax: `findi` / `fi`

To print important tasks that contain KEYWORD: `findi KEYWORD` / `fi KEYWORD`

Example: `findi final exam`

\*KEYWORD is case sensitive.

### List Of Accepted Command Abbreviations

*   list -> l

*   find -> f

*   findi -> fi


## Miscellaneous Commands

### exit

Exits the program. Command is case insensitive and ignores trailing white spaces.

### manual

Provides the link to this page.

### easter egg commands

For those who know the game Lobotomy Corp, there are some special responses Angela can give when certain commands are entered. These commands will not be shown in this user guide as it contains spoilers to the game. If you are still keen otherwise, you may check the source code of Angela.
