package org.acme.task.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.task.entity.TaskEntity;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<TaskEntity> {

}
