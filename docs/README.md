# Clara User Guide

Clara is a command-line personal assistant that manages a list of tasks.

## Listing tasks

Enter `list` to display all added tasks in order.

## Adding tasks

Add a task using one of these commands:

- `todo <description>` — a task without a date or time.
- `deadline <description> /by <time>` — a task due by a specified time.
- `event <description> /from <start-time> /to <end-time>` — a task with start and end times.

Dates and times are stored as text.
The character `|` is reserved for Clara's save format and cannot be used in task details.

## Marking tasks as done

Enter `mark <task-number>` to mark a task as done, or `unmark <task-number>` to mark a task as not done.
The task number is the number shown in the output of doing `list`.

## Deleting tasks

Enter `delete <task-number>` to remove a task from the list.
The task number is the number shown in the output of doing `list`.

## Saving tasks

Clara automatically saves the task list after you add, mark, unmark, or delete a task. When Clara starts, it restores the previously saved task list.

## Handling invalid commands

Clara explains invalid commands and inputs, then lets you try again. For example, `delete` without a valid task number displays the required command format.

## Exiting the app

Enter `bye` to exit the app.


*Co-maintained by Codex.*
