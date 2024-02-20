package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.TodoEntity;
import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.TodoMapper;
import com.rizzywebworks.InspireHub.model.TodoRecord;
import com.rizzywebworks.InspireHub.model.TodoRequest;
import com.rizzywebworks.InspireHub.repository.TodoRepository;
import com.rizzywebworks.InspireHub.repository.UserRepository;
import com.rizzywebworks.InspireHub.security.UserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));
        return todoMapper.mapToTodoRecord(todoEntity);
    }


    @Override
    public TodoRecord updateTodo(Long id, TodoRequest todoRequest) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));

        // Update the todos details
        todoEntity.setTitle(todoRequest.getTitle());
        todoEntity.setBody(todoRequest.getBody());
        todoEntity.setDueDate(todoRequest.getDueDate());
        todoEntity.setCompleted(todoRequest.isCompleted());

        // Save the updated todos entity
        TodoEntity updatedTodo = todoRepository.save(todoEntity);

        // Map the updated todos entity to a TodoRecord and return it
        return todoMapper.mapToTodoRecord(updatedTodo);
    }


    @Override
    public void deleteTodo(Long id) {
        TodoEntity todoEntity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo item not found with id: " + id));

        // Delete the todos entity from the database
        todoRepository.delete(todoEntity);
    }


    @Override
    public List<TodoRecord> getTodosByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        List<TodoEntity> todoEntities = user.getTodos();
        return todoEntities.stream()
                .map(todoMapper::mapToTodoRecord)
                .collect(Collectors.toList());
    }

}
