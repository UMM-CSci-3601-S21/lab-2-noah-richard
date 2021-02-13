package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import io.javalin.http.BadRequestResponse;

public class DatabaseTodo {

  private Todo[] allTodos;

  public DatabaseTodo(String todoDataFile) throws IOException {
    Gson gson = new Gson();
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    allTodos = gson.fromJson(reader, Todo[].class);
  }

  public int size() {
    return allTodos.length;
  }

  public Todo getTodo(String id) {
    return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

  public Todo[] listTodos(Map<String, List<String>> queryParams) {
    Todo[] filteredTodos = allTodos;

    // Limit how many todos appear
    if (queryParams.containsKey("limit")) {
      String limitParam = queryParams.get("limit").get(0);
      try {
        int targetLimit = Integer.parseInt(limitParam);
        if (targetLimit > filteredTodos.length){
          targetLimit = filteredTodos.length;
        } else if (targetLimit < 0) {
          throw new BadRequestResponse("Cannot enter a negative limit value.");
        }
        filteredTodos = filterTodosByLimit(filteredTodos, targetLimit);
      } catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified limit '" + limitParam + "' is not a number.");
      }
    }

    return filteredTodos;
  }

  /**
   * Get an array limited to a certain number of entries.
   *
   * @param todos the list of todos to limit
   * @param limitParam the number of todos wanted
   * @return an array with a limited number of entries
   */
  public Todo[] filterTodosByLimit(Todo[] todos, int limitParam) {
    Todo[] limitedTodos = new Todo[limitParam];
    for (int limitArray = 0; limitArray < limitParam; limitArray++) {
      limitedTodos[limitArray] = todos[limitArray];
    }
    return limitedTodos;
  }
}
