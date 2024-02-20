package com.rizzywebworks.InspireHub.model;

import java.time.LocalDateTime;

public record TodoRecord(Long id,
                         String title,
                         String body,
                         LocalDateTime dueDate,
                         boolean completed,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {
}
