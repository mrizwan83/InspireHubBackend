package com.rizzywebworks.InspireHub.model;

import com.rizzywebworks.InspireHub.entity.TodoEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoMapper {

    public TodoEntity toTodoEntity(TodoRequest todoRequest) {
        return TodoEntity.builder()
                .title(todoRequest.getTitle())
                .body(todoRequest.getBody())
                .dueDate(todoRequest.getDueDate())
                .completed(todoRequest.isCompleted())
                .priority(todoRequest.getPriority())
                .status(todoRequest.getStatus())
                .build();
    }

    // Helper method to convert TodoEntity to TodoRecord
    public TodoRecord mapToTodoRecord(TodoEntity todoEntity) {
        return new TodoRecord(todoEntity.getId(), todoEntity.getTitle(), todoEntity.getBody(), todoEntity.getDueDate(), todoEntity.isCompleted(), todoEntity.getPriority(),todoEntity.getStatus(),todoEntity.getCreatedAt(), todoEntity.getUpdatedAt());
    }
}