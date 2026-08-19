package clara.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// NOTE: AI-assisted test suite implementation. See CITATIONS.md [C-010].

/**
 * Unit tests for {@link Todo}.
 */
public class TodoTest {

    @Test
    public void toString_uncompletedTodo_correctFormat() {
        Todo todo = new Todo("buy groceries");
        assertFalse(todo.isDone());
        assertEquals("buy groceries", todo.getTaskName());
        assertEquals("[T][ ] buy groceries", todo.toString());
    }

    @Test
    public void toString_completedTodo_correctFormat() {
        Todo todo = new Todo("buy groceries");
        todo.setDone(true);
        assertTrue(todo.isDone());
        assertEquals("[T][X]", todo.toString().substring(0, 6));
        assertEquals("[T][X] buy groceries", todo.toString());
    }

    @Test
    public void setTaskName_validName_nameUpdated() {
        Todo todo = new Todo("buy groceries");
        todo.setTaskName("buy fruits");
        assertEquals("buy fruits", todo.getTaskName());
    }
}
