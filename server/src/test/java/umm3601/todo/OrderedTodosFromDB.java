package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class OrderedTodosFromDB {
  @Test
  public void orderTodoByOwner() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] sortedTodos = db.orderedTodos(allTodos, "owner");
    for (int index = 0; index < 51; index++) {
      assertEquals("Barry", sortedTodos[index].owner, "The expected owner did not match the actual owner for the expected indexes.");
    }
  }
}
