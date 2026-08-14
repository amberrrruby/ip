import java.util.Scanner;

public class Clara {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String greetingLine = "\n[Clara] Hello. I'm Clara. How can I assist you?";
    System.out.println(greetingLine);

    while (true) {
      System.out.print(">> ");
      String userInput = scanner.nextLine();

      // TODO: Simplified parsing model. Only echo supported at Level 1.
      if ("bye".equals(userInput)) {
        break;
      }

      String echoString = "\n[Clara] (echoed:) " + userInput;
      System.out.println(echoString);
    }

    String exitLine = "[Clara] Goodbye. See you again.";
    System.out.println(exitLine);
  }
}
