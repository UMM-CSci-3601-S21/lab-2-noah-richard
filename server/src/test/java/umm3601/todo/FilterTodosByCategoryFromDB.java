package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

/**
 * Tests the umm3601.todo.DatabaseTodo filterTodosByCategory with _category_ query
 */
public class FilterTodosByCategoryFromDB {
  @Test
  public void filterTodoByCategory() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] softwareDesignTodos = db.filterTodosByCategory(allTodos, "software design");
    assertEquals(74, softwareDesignTodos.length, "Incorrect number of todos with software design category.");

    Todo[] homeworkTodos = db.filterTodosByCategory(allTodos, "homework");
    assertEquals(79, homeworkTodos.length, "Incorrect number of todos with homework category.");

  }
}
