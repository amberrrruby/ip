package clara.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents an event task with a start time and an end time.
 */
public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    /**
     * Creates an event with the specified name, start time, and end time.
     *
     * @param taskName the name of the event
     * @param fromTime the start time of the event
     * @param toTime   the end time of the event
     */
    public Event(String taskName, LocalDateTime fromTime, LocalDateTime toTime) {
        super(taskName);
        // NOTE: AI-assisted invariant assertions. See CITATIONS.md [C-015].
        assert fromTime != null : "Event start time must be present";
        assert toTime != null : "Event end time must be present";
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public LocalDateTime getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(LocalDateTime newFromTime) {
        assert newFromTime != null : "Event start time must be present";
        this.fromTime = newFromTime;
    }

    public LocalDateTime getToTime() {
        return this.toTime;
    }

    public void setToTime(LocalDateTime newToTime) {
        assert newToTime != null : "Event end time must be present";
        this.toTime = newToTime;
    }

    /**
     * Returns a string representation of this event, including its start and end times.
     *
     * @return a formatted string representing this event
     */
    @Override
    public String toString() {
        // NOTE: AI-assisted `LocalDateTime` pattern implementation. See CITATIONS.md [C-005].
        // NOTE: AI-assisted `Locale` specification for date formatting. See CITATIONS.md [C-011].
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", Locale.US);

        return "[E]"
                + super.toString()
                + " (from: "
                + this.fromTime.format(displayFormatter)
                + " to: "
                + this.toTime.format(displayFormatter)
                + ")";
    }
}
