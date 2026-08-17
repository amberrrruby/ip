import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
   * Todo tasks leave {@code time1} and {@code time2} empty; deadline tasks leave {@code time2}
   * empty. Titles and times must not contain {@code |} or a line break.
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
