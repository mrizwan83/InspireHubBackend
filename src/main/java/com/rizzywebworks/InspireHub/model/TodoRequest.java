package com.rizzywebworks.InspireHub.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class TodoRequest {
    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Body is required")
    private String body;

    private LocalDateTime dueDate;

    private boolean completed;

}

