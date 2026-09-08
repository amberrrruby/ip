package clara;

import java.util.List;

import clara.exception.ClaraException;
import clara.parser.Parser;
import clara.storage.TodoFileHandler;
import clara.task.Priority;
import clara.task.Task;
import clara.task.TaskList;

// NOTE: AI-assisted OOP refactoring and extraction of responsibilites.
// See CITATIONS.md [C-009].
// AI-assisted GUI command-response integration. See CITATIONS.md [C-013].
// AI-assisted persistence-failure rollback. See CITATIONS.md [C-017].

/**
 * Represents the main application for Clara, a simple task management chatbot.
 */
public class Clara {
    private static final String CLARA_HEADER = "[Clara] ";

    private final TaskList tasks;
    private final String startupMessage;
    private boolean shouldExit;

    /**
     * Creates Clara and restores any previously saved tasks.
     */
    public Clara() {
        tasks = new TaskList();
        startupMessage = loadTasks();
    }

    /**
     * Starts the Clara application, loads saved tasks, and processes user commands.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        Clara clara = new Clara();
        System.out.println("\n" + CLARA_HEADER + clara.getStartupMessage());

        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            while (true) {
                System.out.print(">> ");
                String input = scanner.nextLine();
                String response = clara.getResponse(input);
                if (!response.isEmpty()) {
                    System.out.println("\n" + CLARA_HEADER + response);
                }
                if (clara.shouldExit()) {
                    return;
                }
            }
        }
    }

    /**
     * Returns Clara's greeting and task-loading result.
     *
     * @return the message to display when Clara starts
     */
    public String getStartupMessage() {
        return startupMessage;
    }

    /**
     * Indicates whether a validated {@code bye} command has requested application shutdown.
     *
     * @return {@code true} if Clara should exit
     */
    public boolean shouldExit() {
        return shouldExit;
    }

    /**
     * Processes one user command and returns Clara's reply.
     *
     * @param input the user command
     * @return Clara's response to the command
     */
    public String getResponse(String input) {
        final String[] userInput = input.trim().split("\\s+", 2);
        if (userInput[0].isEmpty()) {
            return "";
        }

        try {
            String command = userInput[0];
            String arguments = userInput.length == 2 ? userInput[1] : "";
            return switch (command) {
                case "bye" -> {
                    Parser.requireNoArguments(command, arguments);
                    shouldExit = true;
                    yield "Goodbye. See you again.";
                }
                case "list" -> {
                    Parser.requireNoArguments(command, arguments);
                    yield formatTaskList();
                }
                case "mark" -> {
                    int taskIndexToMark = Parser.parseTaskIndex(command, arguments);
                    Task markedTask = tasks.markTask(taskIndexToMark);
                    try {
                        TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    } catch (ClaraException ex) {
                        markedTask.setDone(false);
                        throw ex;
                    }
                    yield "marked task " + taskIndexToMark + ":\n| " + markedTask;
                }
                case "unmark" -> {
                    int taskIndexToUnmark = Parser.parseTaskIndex(command, arguments);
                    Task unmarkedTask = tasks.unmarkTask(taskIndexToUnmark);
                    try {
                        TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    } catch (ClaraException ex) {
                        unmarkedTask.setDone(true);
                        throw ex;
                    }
                    yield "unmarked task " + taskIndexToUnmark + ":\n| " + unmarkedTask;
                }
                case "delete" -> {
                    int taskIndexToDelete = Parser.parseTaskIndex(command, arguments);
                    Task deletedTask = tasks.deleteTask(taskIndexToDelete);
                    try {
                        TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    } catch (ClaraException ex) {
                        tasks.getTasks().add(taskIndexToDelete - 1, deletedTask);
                        throw ex;
                    }
                    yield formatTaskDeleted(deletedTask, taskIndexToDelete);
                }
                // NOTE: AI-assisted task priority feature. See CITATIONS.md [C-017].
                case "priority" -> {
                    String[] priorityArguments = arguments.split("\\s+", 2);
                    if (priorityArguments.length != 2) {
                        throw new ClaraException("Use: priority <task number> <low|medium|high>.");
                    }
                    int taskIndex = Parser.parseTaskIndex(command, priorityArguments[0]);
                    Priority priority = Parser.parsePriority(priorityArguments[1]);
                    Task task = tasks.getTask(taskIndex);
                    task.setPriority(priority);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    yield "changed priority of task " + taskIndex + ":\n| " + task;
                }
                case "todo" -> addTask(Parser.parseTodo(arguments));
                case "deadline" -> addTask(Parser.parseDeadline(arguments));
                case "event" -> addTask(Parser.parseEvent(arguments));
                case "find" -> formatFindResults(Parser.parseFindQuery(arguments));
                default -> throw new ClaraException("Unknown command: " + command);
            };
        } catch (ClaraException ex) {
            return "Something went wrong:\n" + ex.getMessage() + "\nTry again.";
        }
    }

    /**
     * Loads saved tasks and produces the corresponding startup message.
     *
     * @return the startup message
     */
    private String loadTasks() {
        try {
            TodoFileHandler.loadTasksFromDisk(tasks.getTasks());
            return "Hello. I'm Clara. How can I assist you?\n\nLoading tasks...\nDone.";
        } catch (ClaraException ex) {
            return "Hello. I'm Clara. How can I assist you?\n\nLoading tasks...\n"
                    + "Something went wrong while loading tasks:\n"
                    + ex.getMessage()
                    + "\nTasks will not be loaded, and we'll start clean.";
        }
    }

    /**
     * Formats the current task list.
     *
     * @return the formatted task list response
     */
    private String formatTaskList() {
        if (tasks.isEmpty()) {
            return "There are no tasks. Yay...?";
        }

        StringBuilder response = new StringBuilder("Here are your tasks in a list:");
        for (int i = 0; i < tasks.size(); i++) {
            response.append("\n").append(i + 1).append(". ").append(tasks.getTasks().get(i));
        }
        return response.toString();
    }

    /**
     * Saves an added task and formats its confirmation.
     *
     * @param task the task to add
     * @return the formatted confirmation response
     * @throws ClaraException if the task cannot be saved
     */
    private String addTask(Task task) throws ClaraException {
        tasks.addTask(task);
        try {
            TodoFileHandler.flushTasksToDisk(tasks.getTasks());
        } catch (ClaraException ex) {
            tasks.getTasks().remove(task);
            throw ex;
        }
        return "added:\n| " + task + " (task #" + tasks.size() + ")";
    }

    /**
     * Formats the confirmation after a task is deleted.
     *
     * @param deletedTask the task that was deleted
     * @param taskIndex   the 1-based index of the deleted task
     * @return the formatted deletion response
     */
    private String formatTaskDeleted(Task deletedTask, int taskIndex) {
        return "deleted task "
                + taskIndex
                + ":\n| "
                + deletedTask
                + " ("
                + tasks.size()
                + " task"
                + (tasks.size() == 1 ? " " : "s ")
                + "remain)";
    }

    /**
     * Finds matching tasks and formats the results.
     *
     * @param query the text to search for
     * @return the formatted search response
     */
    private String formatFindResults(String query) {
        List<Integer> matchingIndices = tasks.findMatchingIndices(query);
        StringBuilder response = new StringBuilder("Finding tasks with task names containing: ")
                .append(query);
        for (int taskIndex : matchingIndices) {
            response.append("\n")
                    .append(taskIndex + 1)
                    .append(". ")
                    .append(tasks.getTasks().get(taskIndex));
        }
        return response.append("\n")
                .append(matchingIndices.size())
                .append(" task")
                .append(matchingIndices.size() == 1 ? " " : "s ")
                .append("found.")
                .toString();
    }
}
