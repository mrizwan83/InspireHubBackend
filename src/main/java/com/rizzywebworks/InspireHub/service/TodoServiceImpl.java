package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.TodoEntity;
import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.AuthenticationFailedException;
import com.rizzywebworks.InspireHub.model.TodoMapper;
import com.rizzywebworks.InspireHub.model.TodoRecord;
import com.rizzywebworks.InspireHub.model.TodoRequest;
import com.rizzywebworks.InspireHub.repository.TodoRepository;
import com.rizzywebworks.InspireHub.repository.UserRepository;
import com.rizzywebworks.InspireHub.security.UserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{



    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    private final TodoMapper todoMapper;

    @Override
    public TodoRecord createTodo(TodoRequest todoRequest) {
        // Extract user ID from the authentication token

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getUserId();

        TodoEntity todoEntity = todoMapper.toTodoEntity(todoRequest);
        // Set the user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        todoEntity.setUser(user);
        TodoEntity savedTodo = todoRepository.save(todoEntity);
        return todoMapper.mapToTodoRecord(savedTodo);
    }

    @Override
    public TodoRecord getTodoById(Long id) {
        // Retrieve the todos entity by ID
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));

        // Check if the authenticated user owns the todos
        checkTodoOwnership(todoEntity);

        // If the user owns the todos, map it to a TodoRecord and return it
        return todoMapper.mapToTodoRecord(todoEntity);
    }


    @Override
    public TodoRecord updateTodo(Long id, TodoRequest todoRequest) {
        // Retrieve the todos entity by ID
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));

        // Check if the authenticated user owns the todos
        checkTodoOwnership(todoEntity);

        // Update the todos details
        todoEntity.setTitle(todoRequest.getTitle());
        todoEntity.setBody(todoRequest.getBody());
        todoEntity.setDueDate(todoRequest.getDueDate());
        todoEntity.setCompleted(todoRequest.isCompleted());
        todoEntity.setPriority(todoRequest.getPriority());
        todoEntity.setStatus(todoRequest.getStatus());

        // Save the updated todos entity
        TodoEntity updatedTodo = todoRepository.save(todoEntity);

        // Map the updated todos entity to a TodoRecord and return it
        return todoMapper.mapToTodoRecord(updatedTodo);
    }


    @Override
    public void deleteTodo(Long id) {
        // Retrieve the todos entity by ID
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));

        // Check if the authenticated user owns the todos
        checkTodoOwnership(todoEntity);

        // Delete the todos entity from the database
        todoRepository.delete(todoEntity);
    }


    @Override
    public List<TodoRecord> getTodosByUserId(Long userId) {
        // Get the authenticated user's ID from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInUserId = ((UserPrincipal) authentication.getPrincipal()).getUserId();

        // Ensure that the requested userId matches the logged-in user's ID
        if (!userId.equals(loggedInUserId)) {
            throw new AuthenticationFailedException("Unauthorized access: You can only retrieve your own todos.");
        }

        // Proceed to fetch the todos for the authenticated user
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        List<TodoEntity> todoEntities = user.getTodos();
        return todoEntities.stream()
                .map(todoMapper::mapToTodoRecord)
                .collect(Collectors.toList());
    }

    // Helper method to check if the authenticated user owns the todos
    private void checkTodoOwnership(TodoEntity todoEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInUserId = ((UserPrincipal) authentication.getPrincipal()).getUserId();

        if (!todoEntity.getUser().getId().equals(loggedInUserId)) {
            throw new AuthenticationFailedException("Unauthorized access: You can only access update or delete your own todos.");
        }
    }


}
