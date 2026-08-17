import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

  public static void requireNoArguments(final String command, final String arguments)
      throws ClaraException {
    if (!arguments.isEmpty()) {
      throw new ClaraException(command + " does not accept arguments");
    }
  }

  public static int parseTaskIndex(final String command, final String arguments)
      throws ClaraException {
    try {
      return Integer.parseInt(arguments);
    } catch (NumberFormatException ex) {
      // INFO: Used to be AI-assisted invalid-index input validation. See CITATIONS.md [C-001].
      throw new ClaraException("Use: " + command + " <task number>.");
    }
  }

  // NOTE: AI-assisted task-command input validation. See CITATIONS.md [C-002].
  public static Todo parseTodo(String arguments) throws ClaraException {
    if (arguments.isBlank()) {
      throw new ClaraException("A todo needs a description.");
    }
    if (arguments.indexOf('|') != -1) {
      throw new ClaraException("The character '|' is reserved and cannot be used in task details.");
    }
    return new Todo(arguments);
  }

  public static Deadline parseDeadline(String arguments) throws ClaraException {
    if (arguments.indexOf('|') != -1) {
      throw new ClaraException("The character '|' is reserved and cannot be used in task details.");
    }
    String[] nameAndTime = arguments.split(" /by ", 2);

    if (nameAndTime.length != 2 || nameAndTime[0].isBlank() || nameAndTime[1].isBlank()) {
      throw new ClaraException("Use: deadline <name> /by <time>.");
    }

    // TODO: add citations: https://chatgpt.com/share/6a8013d9-a11c-83ec-9940-edc0bf614544
    try {
      LocalDateTime deadline = LocalDateTime.parse(nameAndTime[1], formatter);
      return new Deadline(nameAndTime[0], deadline);
    } catch (DateTimeParseException ex) {
      throw new ClaraException("Time format: yyyy-MM-dd HHmm (e.g. 2025-12-25 1357).");
    }
  }

  public static Event parseEvent(String arguments) throws ClaraException {
    if (arguments.indexOf('|') != -1) {
      throw new ClaraException("The character '|' is reserved and cannot be used in task details.");
    }
    String[] nameAndRest = arguments.split(" /from ", 2);
    if (nameAndRest.length != 2 || nameAndRest[0].isBlank()) {
      throw new ClaraException("Use: event <name> /from <time> /to <time>.");
    }

    String[] fromTimeAndToTime = nameAndRest[1].split(" /to ", 2);
    if (fromTimeAndToTime.length != 2
        || fromTimeAndToTime[0].isBlank()
        || fromTimeAndToTime[1].isBlank()) {
      throw new ClaraException("Use: event <name> /from <time> /to <time>.");
    }

    // TODO: add citations: https://chatgpt.com/share/6a8013d9-a11c-83ec-9940-edc0bf614544
    try {
      LocalDateTime fromTime = LocalDateTime.parse(fromTimeAndToTime[0], formatter);
      LocalDateTime toTime = LocalDateTime.parse(fromTimeAndToTime[1], formatter);
      return new Event(nameAndRest[0], fromTime, toTime);
    } catch (DateTimeParseException ex) {
      throw new ClaraException("Time format: yyyy-MM-dd HHmm (e.g. 2025-12-25 1357).");
    }
  }
}
