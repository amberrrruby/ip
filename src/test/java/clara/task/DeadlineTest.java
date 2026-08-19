package clara.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

// NOTE: AI-assisted test suite implementation. See CITATIONS.md [C-010].

/**
 * Unit tests for {@link Deadline}.
 */
public class DeadlineTest {

    @Test
    public void toString_uncompletedDeadline_correctFormat() {
        LocalDateTime time = LocalDateTime.of(2025, 10, 15, 18, 0);
        Deadline deadline = new Deadline("submit report", time);

        assertFalse(deadline.isDone());
        assertEquals("submit report", deadline.getTaskName());
        assertEquals(time, deadline.getDeadlineTime());
        assertEquals("[D][ ] submit report (by: Oct 15 2025, 6:00 PM)", deadline.toString());
    }

    @Test
    public void toString_completedDeadline_correctFormat() {
        LocalDateTime time = LocalDateTime.of(2025, 10, 15, 18, 0);
        Deadline deadline = new Deadline("submit report", time);
        deadline.setDone(true);

        assertTrue(deadline.isDone());
        assertEquals("[D][X] submit report (by: Oct 15 2025, 6:00 PM)", deadline.toString());
    }

    @Test
    public void setDeadlineTime_validTime_timeUpdated() {
        LocalDateTime initialTime = LocalDateTime.of(2025, 10, 15, 18, 0);
        LocalDateTime newTime = LocalDateTime.of(2025, 11, 20, 12, 30);
        Deadline deadline = new Deadline("submit report", initialTime);

        deadline.setDeadlineTime(newTime);
        assertEquals(newTime, deadline.getDeadlineTime());
    }
}
