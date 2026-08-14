import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clara {
  private void printList(final List<String> tasks) {
    System.out.println("\n[Clara] Here are your tasks in a list:");
    for (int i = 0; i < tasks.size(); i++) {
      String currentTask = Integer.toString(i + 1) + ". " + tasks.get(i);
      System.out.println(currentTask);
    }
  }

  private void addAndPrintTask(final String userInput, List<String> tasks) {
    if (tasks.size() >= 100) {
      System.out.println("\nfatal: Level 2 assumption broken: more than 100 tasks provided.");
      return;
    } else {
      tasks.add(userInput);
      String echoString = "\n[Clara] added: " + userInput;
      System.out.println(echoString);
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Clara clara = new Clara(); // Instantiated to allow for pattern matching below
    String greetingLine = "\n[Clara] Hello. I'm Clara. How can I assist you?";
    System.out.println(greetingLine);

    List<String> tasks = new ArrayList<>(100);

    boolean terminate = false;
    while (!terminate) {
      System.out.print(">> ");
      final String userInput = scanner.nextLine();

      switch (userInput) {
        case "bye" -> {
          terminate = true;
        }
        case "list" -> {
          clara.printList(tasks);
        }
        default -> {
          clara.addAndPrintTask(userInput, tasks);
        }
      }
    }

    String exitLine = "[Clara] Goodbye. See you again.";
    System.out.println(exitLine);
  }
}
