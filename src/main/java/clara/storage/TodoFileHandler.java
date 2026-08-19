package clara.storage;

import clara.exception.ClaraException;
import clara.task.Deadline;
import clara.task.Event;
import clara.task.Task;
import clara.task.Todo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Handles saving and loading tasks to and from Clara's task data file. */
public class TodoFileHandler {
  private static final Path FILE_PATH = Path.of("data", "todo-list.txt");
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

  // AI-assisted saved-task format and validation. See CITATIONS.md [C-004].

  /**
   * Stores one task per line using the following format:
   *
   * <pre>
   * task   ::= type "|" status "|" title "|" time1 "|" time2
   * type   ::= "t" | "d" | "e"
   * status ::= "x" | "o"
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
    final String[] arguments = line.split("\\|", -1);
    if (arguments.length != 5) {
      throw new ClaraException("Saved task has an invalid number of fields.");
    }
    if (!arguments[1].equals("x") && !arguments[1].equals("o")) {
      throw new ClaraException("Saved task has an invalid status.");
    }
    if (arguments[2].isBlank()) {
      throw new ClaraException("Saved task needs a title.");
    }

    Task task =
        switch (arguments[0]) {
          case "t" -> {
            if (!arguments[3].isEmpty() || !arguments[4].isEmpty()) {
              throw new ClaraException("Saved todo task must not have times.");
            }
            yield new Todo(arguments[2]);
          }
          case "d" -> {
            if (arguments[3].isBlank() || !arguments[4].isEmpty()) {
              throw new ClaraException("Saved deadline task has invalid times.");
            }
            yield new Deadline(arguments[2], LocalDateTime.parse(arguments[3], formatter));
          }
          case "e" -> {
            if (arguments[3].isBlank() || arguments[4].isBlank()) {
              throw new ClaraException("Saved event task needs start and end times.");
            }
            yield new Event(
                arguments[2],
                LocalDateTime.parse(arguments[3], formatter),
                LocalDateTime.parse(arguments[4], formatter));
          }
          default -> throw new ClaraException("Saved task has an unknown type.");
        };
    task.setDone(arguments[1].equals("x"));
    return task;
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
      case Todo todo -> "t|" + (todo.isDone() ? "x" : "o") + "|" + todo.getTaskName() + "||";
      case Deadline deadline ->
          "d|"
              + (deadline.isDone() ? "x" : "o")
              + "|"
              + deadline.getTaskName()
              + "|"
              + deadline.getDeadlineTime().format(formatter)
              + "|";
      case Event event ->
          "e|"
              + (event.isDone() ? "x" : "o")
              + "|"
              + event.getTaskName()
              + "|"
              + event.getFromTime().format(formatter)
              + "|"
              + event.getToTime().format(formatter);
      default -> throw new IllegalArgumentException("Unknown subclass of Task encountered");
    };
  }

  // AI-assisted buffered file saving. See CITATIONS.md [C-004].

  /**
   * Saves all tasks to the task data file, replacing any previously saved tasks.
   *
   * @param tasks the list of tasks to save
   * @throws ClaraException if the directory cannot be created or the tasks cannot be written to the
   *     file
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
    } catch (IOException e) {
      throw new ClaraException("Unable to load saved tasks");
    }
  }
}
