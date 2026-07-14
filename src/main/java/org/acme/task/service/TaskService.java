package org.acme.task.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.task.dto.TaskEntityDTO;
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
    public TaskEntity createTask(TaskEntity newTask, Long userId) {
        UserEntity user = userRepository.findByIdOptional(userId).orElseThrow(() -> new IllegalArgumentException("Utente non trovate"));
        newTask.setUser(user);
        newTask.setCreated(LocalDateTime.now());
        taskRepository.persist(newTask);
        return newTask;
    }

    @Transactional
    public TaskEntityDTO updateTask(TaskEntity updated, Long taskId) {
        TaskEntity updatedTask = taskRepository.findByIdOptional(taskId).orElseThrow(() -> new IllegalArgumentException("Task non trovate"));
        updatedTask.setTitle(updated.getTitle());
        updatedTask.setDescription(updated.getDescription());
        updatedTask.setStatus(updated.getStatus());
        updatedTask.setDueDate(updated.getDueDate());
        return toDTO(updatedTask);
    }

    @Transactional
    public boolean deleteTask(Long taskId) {
        TaskEntity task = taskRepository.findByIdOptional(taskId).orElseThrow(() -> new IllegalArgumentException("Task non trovata"));
        taskRepository.delete(task);
        return true;
    }

    private TaskEntityDTO toDTO(TaskEntity task) {
        return new TaskEntityDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreated(),
                task.getDueDate(),
                task.getUser().getId()
        );
    }
}
