package com.rizzywebworks.InspireHub.model;

import com.rizzywebworks.InspireHub.entity.TodoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoMapper {

    public TodoEntity toTodoEntity(TodoRequest todoRequest) {
        return TodoEntity.builder()
                .title(todoRequest.getTitle())
                .body(todoRequest.getBody())
                .dueDate(todoRequest.getDueDate())
                .completed(todoRequest.isCompleted())
                .build();
    }

    // Helper method to convert TodoEntity to TodoRecord
    public TodoRecord mapToTodoRecord(TodoEntity todoEntity) {
        return new TodoRecord(todoEntity.getId(), todoEntity.getTitle(), todoEntity.getBody(), todoEntity.getDueDate(), todoEntity.isCompleted(), todoEntity.getCreatedAt(), todoEntity.getUpdatedAt());
    }
}