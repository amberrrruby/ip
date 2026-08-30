package clara.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", Locale.US);

    private LocalDateTime deadlineTime;

    /**
     * Creates a deadline task with the specified name and deadline.
     *
     * @param taskName     the name of the task
     * @param deadlineTime the deadline of the task
     */
    public Deadline(String taskName, LocalDateTime deadlineTime) {
        super(taskName);
        this.deadlineTime = deadlineTime;
    }

    public LocalDateTime getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalDateTime newDeadlineTime) {
        deadlineTime = newDeadlineTime;
    }

    /**
     * Returns a string representation of this deadline task, including its deadline.
     *
     * @return a formatted string representing this deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + deadlineTime.format(DISPLAY_DATE_TIME_FORMATTER) + ")";
    }
}
