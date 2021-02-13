package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class FilterTodosByLimitFromDB {
    @Test
    public void filterTodoByStatus() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] filter10Todos = db.filterTodosByLimit(allTodos, 10);
    assertEquals(10, filter10Todos.length, "Incorrect number of todos were put into the list");

    Todo[] filterExcessTodos = db.filterTodosByLimit(allTodos, allTodos.length);
    assertEquals(300, filterExcessTodos.length, "The todos were unsuccessfully limited to maximum size");

    Todo[] filter0Todos = db.filterTodosByLimit(allTodos, 0);
    assertEquals(0, filter0Todos.length, "Incorrect number of todos were put into the list");
    }
}
