package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;
import io.javalin.http.BadRequestResponse;

/**
 * Testing the ordering method in DatabaseTodo using all types of sorting.
 */
public class OrderedTodosFromDB {

  private static DatabaseTodo db;
  private static Map<String, List<String>> queryParams;
  private static Todo[] allTodos;

  @BeforeEach
  public void makeDB() throws IOException {
    db = new DatabaseTodo("/todos.json");
    queryParams = new HashMap<>();
    allTodos = db.listTodos(queryParams);
  }
  @Test
  public void orderTodoByOwner() throws IOException {

    Todo[] ownerSortedTodos = db.orderedTodos(allTodos, "owner");
    for (int index = 0; index < 51; index++) {
      assertEquals("Barry", ownerSortedTodos[index].owner, "The expected owner did not match the actual owner for the expected indexes.");
    }
  }
  @Test
  public void orderTodoByCategory() throws IOException {

    Todo[] categorySortedTodos = db.orderedTodos(allTodos, "category");
    for (int index = 0; index < 76; index++) {
      assertEquals("groceries", categorySortedTodos[index].category, "The expected category did not match the actual category for the expected indexes.");
    }
  }
  @Test
  public void orderTodoByStatus() throws IOException {
    Todo[] statusSortedTodos = db.orderedTodos(allTodos, "status");
    for (int index = 0; index < 157; index++) {
      assertEquals(false, statusSortedTodos[index].status, "The expected status did not match the actual status for the expected indexes.");
    }
  }
  @Test
  public void orderTodoByBody() throws IOException {
    Todo[] bodySortedTodos = db.orderedTodos(allTodos, "body");
    for (int index = 0; index < 2; index++) {
      assertEquals(true, bodySortedTodos[index].body.contains("Ad "), "The expected body did not match the actual body for the expected indexes.");
    }
  }
  @Test
  public void orderTodoInvalid() throws IOException {
    Assertions.assertThrows(BadRequestResponse.class, () -> {
      db.orderedTodos(allTodos, "nothing");
    });
  }
}
