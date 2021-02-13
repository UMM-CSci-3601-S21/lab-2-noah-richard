package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

public class FilterTodosByOwnerFromDB {
    @Test
    public void filterTodoByStatus() throws IOException {
    DatabaseTodo db = new DatabaseTodo("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] filterByBlanche = db.filterTodosByOwner(allTodos, "Blanche");
    assertEquals(43, filterByBlanche.length, "Incorrect number of todos were put into the list");

    Todo[] filterByFry = db.filterTodosByOwner(allTodos, "Fry");
    assertEquals(61, filterByFry.length, "Incorrect number of todos were put into the list");

    Todo[] filterByRichard = db.filterTodosByOwner(allTodos, "Richard");
    assertEquals(0, filterByRichard.length, "Incorrect number of todos were put into the list");
    }
}
