package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.model.TodoRecord;
import com.rizzywebworks.InspireHub.model.TodoRequest;

import java.util.List;

public interface TodoService {
    TodoRecord createTodo(TodoRequest todoRequest);

    TodoRecord getTodoById(Long id);

    TodoRecord updateTodo(Long id, TodoRequest todoRequest);

    void deleteTodo(Long id);

    List<TodoRecord> getTodosByUserId(Long userId);
}
