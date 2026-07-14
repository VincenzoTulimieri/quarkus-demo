package org.acme.task.dto;

import org.acme.task.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskEntityDTO(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime created,
        LocalDate dueDate,
        Long userId
        ) {

}
