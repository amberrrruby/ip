import java.util.List;

// NOTE: AI-assisted OOP refactoring and extraction of responsibilites.
// See CITATIONS.md [C-009].

/**
 * Represents the main application for Clara, a simple task management chatbot.
 */
public class Clara {

    /**
     * Starts the Clara application, loads saved tasks, and processes user commands.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.greet();

        TaskList tasks = new TaskList();
        try {
            ui.showLoadingTasks();
            TodoFileHandler.loadTasksFromDisk(tasks.getTasks());
            ui.showLoadingDone();
        } catch (ClaraException ex) {
            ui.showLoadingError(ex);
        }

        boolean terminate = false;
        while (!terminate) {
            final String[] userInput = ui.readCommand().trim().split("\\s+", 2);
            if (userInput[0].isEmpty()) {
                continue;
            }

            try {
                String command = userInput[0];
                String arguments = userInput.length == 2 ? userInput[1] : "";
                switch (command) {
                case "bye" -> {
                    Parser.requireNoArguments(command, arguments);
                    terminate = true;
                }
                case "list" -> {
                    Parser.requireNoArguments(command, arguments);
                    ui.showTaskList(tasks);
                }
                case "mark" -> {
                    int taskIndexToMark = Parser.parseTaskIndex(command, arguments);
                    Task markedTask = tasks.markTask(taskIndexToMark);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskMarked(markedTask, taskIndexToMark);
                }
                case "unmark" -> {
                    int taskIndexToUnmark = Parser.parseTaskIndex(command, arguments);
                    Task unmarkedTask = tasks.unmarkTask(taskIndexToUnmark);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskUnmarked(unmarkedTask, taskIndexToUnmark);
                }
                case "delete" -> {
                    int taskIndexToDelete = Parser.parseTaskIndex(command, arguments);
                    Task deletedTask = tasks.deleteTask(taskIndexToDelete);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskDeleted(deletedTask, taskIndexToDelete, tasks.size());
                }
                case "todo" -> {
                    Todo todo = Parser.parseTodo(arguments);
                    tasks.addTask(todo);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskAdded(todo, tasks.size());
                }
                case "deadline" -> {
                    Deadline deadline = Parser.parseDeadline(arguments);
                    tasks.addTask(deadline);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskAdded(deadline, tasks.size());
                }
                case "find" -> {
                    List<Integer> matchingIndices = tasks.findMatchingIndices(arguments);
                    ui.showFindResults(tasks, matchingIndices, arguments);
                }
                case "event" -> {
                    Event event = Parser.parseEvent(arguments);
                    tasks.addTask(event);
                    TodoFileHandler.flushTasksToDisk(tasks.getTasks());
                    ui.showTaskAdded(event, tasks.size());
                }
                default -> {
                    throw new ClaraException("Unknown command: " + command);
                }
                }
            } catch (ClaraException ex) {
                ui.displayErrorAtConsole(ex);
            }
        }

        ui.end();
    }
}
