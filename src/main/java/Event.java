import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
  private LocalDateTime fromTime;
  private LocalDateTime toTime;

  public Event(String taskName, LocalDateTime fromTime, LocalDateTime toTime) {
    super(taskName);
    this.fromTime = fromTime;
    this.toTime = toTime;
  }

  public LocalDateTime getFromTime() {
    return this.fromTime;
  }

  public void setFromTime(LocalDateTime newFromTime) {
    this.fromTime = newFromTime;
  }

  public LocalDateTime getToTime() {
    return this.toTime;
  }

  public void setToTime(LocalDateTime newToTime) {
    this.toTime = newToTime;
  }

  @Override
  public String toString() {
    // NOTE: AI-assisted `LocalDateTime` pattern implementation. See CITATIONS.md [C-005].
    DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    return "[E]"
        + super.toString()
        + " (from: "
        + this.fromTime.format(displayFormatter)
        + " to: "
        + this.toTime.format(displayFormatter)
        + ")";
  }
}
