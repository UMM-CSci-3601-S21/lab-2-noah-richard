package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class FilterTodosByStatusFromDB {
    @Test
    public void filterTodoByStatus() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] statusCompleteTodos = db.filterTodosByStatus(allTodos, true);
    assertEquals(143, statusCompleteTodos.length, "Incorrect number of todos with status complete");

    Todo[] statusIncompleteTodos = db.filterTodosByStatus(allTodos, false);
    assertEquals(157, statusIncompleteTodos.length, "Incorrect number of todos with status incomplete");
    }
  }

