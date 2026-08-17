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
    // TODO: add citations: https://chatgpt.com/share/6a8013d9-a11c-83ec-9940-edc0bf614544
    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    return "[D]" + super.toString() + " (by: " + this.deadlineTime.format(displayFormatter) + ")";
  }
}
