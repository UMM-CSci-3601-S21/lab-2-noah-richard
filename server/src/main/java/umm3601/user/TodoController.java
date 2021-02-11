package umm3601.user;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class TodoController {

  private DatabaseTodo database;

  public TodoController(DatabaseTodo database) {
    this.database = database;
  }

  public void getTodo (Context ctx) {
    String id = ctx.pathParam("id", String.class).get();
    Todo todo = database.getTodo(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(201);
    } else {
      throw new NotFoundResponse("No user with id " + id + " was found.");
    }
  }

  public void getTodos(Context ctx) {
    Todo[] todos = database.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }
}
