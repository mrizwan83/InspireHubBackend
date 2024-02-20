package com.rizzywebworks.InspireHub.model;

import com.rizzywebworks.InspireHub.entity.Priority;
import com.rizzywebworks.InspireHub.entity.Status;

import java.time.LocalDateTime;

public record TodoRecord(
        Long id,
        String title,
        String body,
        LocalDateTime dueDate,
        boolean completed,
        Priority priority,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
