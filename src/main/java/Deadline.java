import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
  private LocalDateTime deadlineTime;

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

  @Override
  public String toString() {
    // NOTE: AI-assisted `LocalDateTime` pattern implementation. See CITATIONS.md [C-005].
    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    return "[D]" + super.toString() + " (by: " + this.deadlineTime.format(displayFormatter) + ")";
  }
}
