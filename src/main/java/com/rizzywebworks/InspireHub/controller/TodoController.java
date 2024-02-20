package com.rizzywebworks.InspireHub.controller;


import com.rizzywebworks.InspireHub.model.AuthenticationFailedException;
import com.rizzywebworks.InspireHub.model.TodoRecord;
import com.rizzywebworks.InspireHub.model.TodoRequest;
import com.rizzywebworks.InspireHub.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequest todoRequest) {
        try {
            TodoRecord createdTodo = todoService.createTodo(todoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while creating the todo"));
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Object> getTodoById(@PathVariable Long id) {
        try {
            TodoRecord todo = todoService.getTodoById(id);
            return ResponseEntity.ok(todo);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while retrieving the todo"));
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        try {
            TodoRecord updatedTodo = todoService.updateTodo(id, todoRequest);
            return ResponseEntity.ok(updatedTodo);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while updating the todo"));
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while deleting the todo"));
        }
    }

    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<Object> getTodosByUserId(@PathVariable Long userId) {
        try {
            List<TodoRecord> todos = todoService.getTodosByUserId(userId);
            return ResponseEntity.ok(todos);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while retrieving todos for the user"));
        }
    }


}

