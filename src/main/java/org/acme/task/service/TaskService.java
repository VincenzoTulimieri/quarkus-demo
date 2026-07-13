package org.acme.task.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.task.entity.TaskEntity;
import org.acme.task.repository.TaskRepository;
import org.acme.user.entity.UserEntity;
import org.acme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TaskService {
    @Inject
    TaskRepository taskRepository;

    @Inject
    UserRepository userRepository;

    public List<TaskEntity> findAll() {
        return taskRepository.listAll();
    }

    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.findByIdOptional(id);
    }

    @Transactional
    public Optional<TaskEntity> createTask(TaskEntity newTask, Long userId) {
        Optional<UserEntity> user = userRepository.findByIdOptional(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        newTask.setUser(user.get());
        newTask.setCreated(LocalDateTime.now());
        taskRepository.persist(newTask);
        return Optional.of(newTask);
    }

    @Transactional
    public Optional<TaskEntity> updateTask(TaskEntity updated, Long taskId) {
        Optional<TaskEntity> updatedTask = taskRepository.findByIdOptional(taskId);
        if (updatedTask.isEmpty()) {
            return Optional.empty();
        }
        updated.setTitle(updated.getTitle());
        updated.setDescription(updated.getDescription());
        updated.setStatus(updated.getStatus());
        updated.setDueDate(updated.getDueDate());
        return Optional.of(updated);
    }
}
