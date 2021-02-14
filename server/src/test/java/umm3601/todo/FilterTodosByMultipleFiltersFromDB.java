package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class FilterTodosByMultipleFiltersFromDB {
  @Test
  public void filterTodoByMultipleFilters() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] ownedByBlancheTodos = db.filterTodosByOwner(allTodos, "Blanche");
    Todo[] blancheAndIncompleteTodos = db.filterTodosByStatus(ownedByBlancheTodos, false);
    assertEquals(21, blancheAndIncompleteTodos.length, "The array was not as long as expected.");
    Todo[] blancheAndIncompleteAndHomeworkTodos = db.filterTodosByCategory(blancheAndIncompleteTodos, "homework");
    assertEquals(9, blancheAndIncompleteAndHomeworkTodos.length, "The array is an incorrect size");
    Todo[] limitTo5 = db.filterTodosByLimit(blancheAndIncompleteAndHomeworkTodos, 5);
    assertEquals(5, limitTo5.length, "The array was not limited properly");
  }
}
