import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task with a deadline. */
public class Deadline extends Task {
  private LocalDateTime deadlineTime;

  /**
   * Creates a deadline task with the specified name and deadline.
   *
   * @param taskName the name of the task
   * @param deadlineTime the deadline of the task
   */
  public Deadline(String taskName, LocalDateTime deadlineTime) {
    super(taskName);
    this.deadlineTime = deadlineTime;
  }

  public LocalDateTime getDeadlineTime() {
    return this.deadlineTime;
  }

  public void setDeadlineTime(LocalDateTime newDeadlineTime) {
    this.deadlineTime = newDeadlineTime;
  }

  /**
   * Returns a string representation of this deadline task, including its deadline.
   *
   * @return a formatted string representing this deadline task
   */
  @Override
  public String toString() {
    // NOTE: AI-assisted `LocalDateTime` pattern implementation. See CITATIONS.md [C-005].
    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    return "[D]" + super.toString() + " (by: " + this.deadlineTime.format(displayFormatter) + ")";
  }
}
