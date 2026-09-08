# Clara User Guide

Clara is a personal assistant that manages a list of tasks.

## Using the graphical interface

Run Clara to open its chat window. Type a command in the text field at the bottom, then press
`Enter` or select `Send`. Your messages appear on the right, while Clara's replies appear on the
left. The conversation scrolls automatically as new messages are added.

Enter `bye` to display Clara's farewell and close the application. `bye` does not accept any
additional text; for example, `bye later` is reported as an invalid command.

## Listing tasks

Enter `list` to display all added tasks in order.

## Adding tasks

Add a task using one of these commands:

- `todo <description>` — a task without a date or time.
- `deadline <description> /by <time>` — a task due by a specified time.
- `event <description> /from <start-time> /to <end-time>` — a task with start and end times.

Times must be of the format `yyyy-MM-dd HHmm`. Internally they are also stored that way.

The character `|` is reserved for Clara's save format and cannot be used in task details.

## Marking tasks as done

Enter `mark <task-number>` to mark a task as done, or `unmark <task-number>` to mark a task as not done.
The task number is the number shown in the output of doing `list`.

## Setting task priority

Enter `priority <task-number> <low|medium|high>` to set a task's priority. New tasks have
`Medium` priority by default. Task listings show the priority after the completion status; for
example, `[X] High submit report`.

## Deleting tasks

Enter `delete <task-number>` to remove a task from the list.
The task number is the number shown in the output of doing `list`.

## Saving tasks

Clara automatically saves the task list after you add, mark, unmark, delete, or change a task's
priority. When Clara starts, it restores the previously saved task list.

## Finding tasks

Enter `task <target>` to find tasks with names containing the search target.

## Handling invalid commands

Clara explains invalid commands and inputs, then lets you try again. For example, `delete` without a valid task number displays the required command format.

## Exiting the app

Enter `bye` to exit the app.

*The task priority feature was implemented with Codex AI assistance. See `CITATIONS.md` [C-018].*

*Co-maintained by Codex.*
