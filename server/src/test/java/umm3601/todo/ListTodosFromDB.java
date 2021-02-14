package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 * Testing the filterTodosByStatus method from DatabaseTodo with different _status_ queries.
 */
public class ListTodosFromDB {

  private static DatabaseTodo db;
  private static Map<String, List<String>> queryParams;

  @BeforeEach
  public void makeDB() throws IOException {
    db = new DatabaseTodo("/todos.json");
    queryParams = new HashMap<>();
  }

  @Test
  public void testSize() {
    assertEquals(300, db.size(), "The size method gives the incorrect size");
  }

  @Test
  public void testGetTodo() throws IOException {
    assertTrue(db.getTodo("58895985ae3b752b124e7663").owner.equals("Fry"));
  }

  @Test
  public void testCompleteListTodos() throws IOException {

    queryParams.put("status", Arrays.asList(new String[] { "complete" }));
    assertEquals(143, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testIncompleteListTodos() throws IOException {


    queryParams.put("status", Arrays.asList(new String[] { "incomplete" }));
    assertEquals(157, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testContainsListTodos() throws IOException {

    queryParams.put("contains", Arrays.asList(new String[] { "irure laborum" }));
    assertEquals(2, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testOwnerListTodos() throws IOException {

    queryParams.put("owner", Arrays.asList(new String[] { "Blanche" }));
    assertEquals(43, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testCategoryListTodos() throws IOException {

    queryParams.put("category", Arrays.asList(new String[] { "software design" }));
    assertEquals(74, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testLimitListTodos() throws IOException {

    queryParams.put("limit", Arrays.asList(new String[] { "4" }));
    assertEquals(4, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testLimitListTodosTooBig() throws IOException {

    queryParams.put("limit", Arrays.asList(new String[] { "10000" }));
    assertEquals(300, db.listTodos(queryParams).length, "The method did not filter properly");
  }
  @Test
  public void testOrderBy() throws IOException {
    queryParams.put("orderBy", Arrays.asList(new String [] {"owner"}));
    for (int index = 0; index < 51; index++) {
      assertEquals("Barry", db.listTodos(queryParams)[index].owner, "The expected owner did not match the actual owner for the expected indexes.");
    }
  }
  @Test
  public void testLimitListTodosNegative() throws IOException {

    queryParams.put("limit", Arrays.asList(new String[] { "-5" }));
    Assertions.assertThrows(BadRequestResponse.class, () -> {
      db.listTodos(queryParams);
    });
  }
  @Test
  public void testLimitListTodosInvalidArgument() throws IOException {

    queryParams.put("limit", Arrays.asList(new String[] { "a" }));
    Assertions.assertThrows(BadRequestResponse.class, () -> {
      db.listTodos(queryParams);
    });
  }
  }
