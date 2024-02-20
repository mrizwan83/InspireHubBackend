package com.rizzywebworks.InspireHub.controller;


import com.rizzywebworks.InspireHub.model.TodoRecord;
import com.rizzywebworks.InspireHub.model.TodoRequest;
import com.rizzywebworks.InspireHub.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoRecord> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoRecord createdTodo = todoService.createTodo(todoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoRecord> getTodoById(@PathVariable Long id) {
        TodoRecord todo = todoService.getTodoById(id);
        return ResponseEntity.ok().body(todo);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoRecord> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoRecord updatedTodo = todoService.updateTodo(id, todoRequest);
        return ResponseEntity.ok().body(updatedTodo);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<List<TodoRecord>> getTodosByUserId(@PathVariable Long userId) {
        List<TodoRecord> todos = todoService.getTodosByUserId(userId);
        return ResponseEntity.ok().body(todos);
    }


}

