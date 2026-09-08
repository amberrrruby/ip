package clara.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import clara.exception.ClaraException;
import clara.task.Deadline;
import clara.task.Event;
import clara.task.Priority;
import clara.task.Task;
import clara.task.Todo;

/**
 * Handles saving and loading tasks to and from Clara's task data file.
 */
public class TodoFileHandler {
    private static final Path FILE_PATH = Path.of("data", "todo-list.txt");
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HHmm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private static final String FIELD_SEPARATOR = "|";
    private static final String FIELD_SEPARATOR_PATTERN = Pattern.quote(FIELD_SEPARATOR);
    private static final int PRESERVE_TRAILING_EMPTY_FIELDS = -1;

    private static final int FIELD_COUNT = 6;
    private static final int TYPE_FIELD_INDEX = 0;
    private static final int STATUS_FIELD_INDEX = 1;
    // NOTE: AI-assisted task priority feature. See CITATIONS.md [C-017].
    private static final int PRIORITY_FIELD_INDEX = 2;
    private static final int TITLE_FIELD_INDEX = 3;
    private static final int FIRST_TIME_FIELD_INDEX = 4;
    private static final int SECOND_TIME_FIELD_INDEX = 5;

    private static final String TODO_TYPE = "t";
    private static final String DEADLINE_TYPE = "d";
    private static final String EVENT_TYPE = "e";

    private static final String DONE_STATUS = "x";
    private static final String NOT_DONE_STATUS = "o";

    private static final String LOW_PRIORITY = "l";
    private static final String MEDIUM_PRIORITY = "m";
    private static final String HIGH_PRIORITY = "h";

    private static final String EMPTY_FIELD = "";

    // AI-assisted saved-task format and validation. See CITATIONS.md [C-004].

    /**
     * Stores one task per line using the following format:
     *
     * <pre>
     * task     ::= type "|" status "|" priority "|" title "|" time1 "|" time2
     * type     ::= "t" | "d" | "e"
     * status   ::= "x" | "o"
     * priority ::= "l" | "m" | "h" -- corresponding to low, medium, high
     * </pre>
     *
     * <p>Todo tasks leave {@code time1} and {@code time2} empty, while deadline tasks leave {@code
     * time2} empty.
     *
     * @param line the serialized task line to parse
     * @return the task represented by the given line
     * @throws ClaraException if the line is malformed or contains invalid task data
     */
    private static Task parseLine(final String line) throws ClaraException {
        if (line.isBlank()) {
            throw new ClaraException("Saved task line cannot be empty.");
        }
        final String[] arguments = line.split(FIELD_SEPARATOR_PATTERN, PRESERVE_TRAILING_EMPTY_FIELDS);
        if (arguments.length != FIELD_COUNT) {
            throw new ClaraException("Saved task has an invalid number of fields.");
        }
        if (!arguments[STATUS_FIELD_INDEX].equals(DONE_STATUS)
                && !arguments[STATUS_FIELD_INDEX].equals(NOT_DONE_STATUS)) {
            throw new ClaraException("Saved task has an invalid status.");
        }
        Priority priority = parsePriority(arguments[PRIORITY_FIELD_INDEX]);
        if (arguments[TITLE_FIELD_INDEX].isBlank()) {
            throw new ClaraException("Saved task needs a title.");
        }

        Task task =
            switch (arguments[TYPE_FIELD_INDEX]) {
                case TODO_TYPE -> {
                    if (!arguments[FIRST_TIME_FIELD_INDEX].isEmpty()
                            || !arguments[SECOND_TIME_FIELD_INDEX].isEmpty()) {
                        throw new ClaraException("Saved todo task must not have times.");
                    }
                    yield new Todo(arguments[TITLE_FIELD_INDEX]);
                }
                case DEADLINE_TYPE -> {
                    if (arguments[FIRST_TIME_FIELD_INDEX].isBlank()
                            || !arguments[SECOND_TIME_FIELD_INDEX].isEmpty()) {
                        throw new ClaraException("Saved deadline task has invalid times.");
                    }
                    yield new Deadline(arguments[TITLE_FIELD_INDEX],
                            LocalDateTime.parse(
                                    arguments[FIRST_TIME_FIELD_INDEX], DATE_TIME_FORMATTER));
                }
                case EVENT_TYPE -> {
                    if (arguments[FIRST_TIME_FIELD_INDEX].isBlank()
                            || arguments[SECOND_TIME_FIELD_INDEX].isBlank()) {
                        throw new ClaraException("Saved event task needs start and end times.");
                    }
                    yield new Event(
                            arguments[TITLE_FIELD_INDEX],
                            LocalDateTime.parse(
                                    arguments[FIRST_TIME_FIELD_INDEX], DATE_TIME_FORMATTER),
                            LocalDateTime.parse(
                                    arguments[SECOND_TIME_FIELD_INDEX], DATE_TIME_FORMATTER));
                }
                default -> throw new ClaraException("Saved task has an unknown type.");
            };
        task.setDone(arguments[STATUS_FIELD_INDEX].equals(DONE_STATUS));
        task.setPriority(priority);
        return task;
    }

    /**
     * Converts a priority code in the saved-task format to a task priority.
     *
     * @param priorityCode the priority code to convert
     * @return the matching task priority
     * @throws ClaraException if the priority code is invalid
     */
    private static Priority parsePriority(String priorityCode) throws ClaraException {
        return switch (priorityCode) {
            case LOW_PRIORITY -> Priority.LOW;
            case MEDIUM_PRIORITY -> Priority.MEDIUM;
            case HIGH_PRIORITY -> Priority.HIGH;
            default -> throw new ClaraException("Saved task has an invalid priority.");
        };
    }

    // AI-assisted task serialization. See CITATIONS.md [C-004].

    /**
     * Converts a task into its serialized file representation.
     *
     * @param task the task to serialize
     * @return a single line representing the task in the saved-task format
     * @throws IllegalArgumentException if the task is not a supported subclass of {@link Task}
     */
    private static String taskToFileLine(final Task task) {
        return switch (task) {
            case Todo todo -> String.join(FIELD_SEPARATOR, TODO_TYPE, getStatus(todo),
                    getPriority(todo), todo.getTaskName(), EMPTY_FIELD, EMPTY_FIELD);
            case Deadline deadline -> String.join(FIELD_SEPARATOR, DEADLINE_TYPE, getStatus(deadline),
                    getPriority(deadline), deadline.getTaskName(),
                    deadline.getDeadlineTime().format(DATE_TIME_FORMATTER), EMPTY_FIELD);
            case Event event -> String.join(FIELD_SEPARATOR, EVENT_TYPE, getStatus(event),
                    getPriority(event), event.getTaskName(),
                    event.getFromTime().format(DATE_TIME_FORMATTER),
                    event.getToTime().format(DATE_TIME_FORMATTER));
            default -> throw new IllegalArgumentException("Unknown subclass of Task encountered");
        };
    }

    private static String getStatus(Task task) {
        return task.isDone() ? DONE_STATUS : NOT_DONE_STATUS;
    }

    private static String getPriority(Task task) {
        return switch (task.getPriority()) {
            case LOW -> LOW_PRIORITY;
            case MEDIUM -> MEDIUM_PRIORITY;
            case HIGH -> HIGH_PRIORITY;
        };
    }

    // AI-assisted buffered file saving. See CITATIONS.md [C-004].

    /**
     * Saves all tasks to the task data file, replacing any previously saved tasks.
     *
     * @param tasks the list of tasks to save
     * @throws ClaraException if the directory cannot be created or the tasks cannot be written to the
     *                        file
     */
    public static void flushTasksToDisk(final List<Task> tasks) throws ClaraException {
        try {
            Files.createDirectories(FILE_PATH.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
                for (Task task : tasks) {
                    writer.write(taskToFileLine(task));
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            throw new ClaraException("Unable to write saved tasks");
        }
    }

    // AI-assisted buffered file loading. See CITATIONS.md [C-004].

    /**
     * Loads saved tasks from the task data file into the given list.
     *
     * <p>If the data file does not exist, the list is left unchanged. Otherwise, the existing
     * contents of the list are cleared before the saved tasks are loaded.
     *
     * @param tasks the list into which saved tasks are loaded
     * @throws ClaraException if the saved task file cannot be read
     */
    public static void loadTasksFromDisk(List<Task> tasks) throws ClaraException {
        if (!Files.exists(FILE_PATH)) {
            return;
        }

        tasks.clear();

        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String line;

            while ((line = reader.readLine()) != null) {
                tasks.add(parseLine(line));
            }
        } catch (IOException exception) {
            throw new ClaraException("Unable to load saved tasks");
        }
    }
}
