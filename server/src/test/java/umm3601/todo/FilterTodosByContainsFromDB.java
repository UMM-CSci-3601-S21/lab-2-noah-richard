package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class FilterTodosByContainsFromDB {
  @Test
  public void filterTodoByContains() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] allIpsumTodos = db.filterTodosByContains(allTodos, "ipsum");
    assertEquals(60, allIpsumTodos.length, "Incorrect number of todos containing ipsum.");

    Todo[] allExNonTodos = db.filterTodosByContains(allTodos, "ex non");
    assertEquals(1, allExNonTodos.length, "Incorrect number of todos containing ex non.");

    Todo[] allExNonExTodos = db.filterTodosByContains(allTodos, "ex non ex");
    assertEquals(0, allExNonExTodos.length, "Incorrect number of todos containing ex non ex.");
  }
}
