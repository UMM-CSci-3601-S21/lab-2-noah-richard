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
    // Filter if status defined
    if (queryParams.containsKey("status")) {
      String statusParam = queryParams.get("status").get(0);
      boolean statusBool;
      if (statusParam.equals("complete")) {
        statusBool = true;
      } else {
        statusBool = false;
      }
      filteredTodos = filterTodosByStatus(filteredTodos, statusBool);
    }
    // Filter if contains is defined
    if (queryParams.containsKey("contains")) {
      String containsParam = queryParams.get("contains").get(0);
      filteredTodos = filterTodosByContains(filteredTodos, containsParam);
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

  /**
   * Gets an array of all todos having the target status (complete/incomplete = true/false)
   * @param todos the list of todos to filter by status
   * @param statusBool the status we want to filter by; true is "complete", false is "incomplete"
   * @return an array of todos having the same status as given
   */
  public Todo[] filterTodosByStatus(Todo[] todos, boolean statusBool) {
    return Arrays.stream(todos).filter(x -> x.status == statusBool).toArray(Todo[]::new);
  }

  /**
   * Gets an array of all todos having the target string in their bodies
   * @param todos the list of todos to filter by the string
   * @param containsParam a string to search the bodies of the todos for
   * @return an array of todos whose bodies contain the given string
   */
  public Todo[] filterTodosByContains(Todo[] todos, String containsParam) {
    return Arrays.stream(todos).filter(x -> x.body.contains(containsParam) == true).toArray(Todo[]::new);
  }
}
