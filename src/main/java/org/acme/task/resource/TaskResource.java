package org.acme.task.resource;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.task.dto.TaskEntityDTO;
import org.acme.task.entity.TaskEntity;
import org.acme.task.service.TaskService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskService taskService;

    @GET
    public List<TaskEntityDTO> getAllTasks() {
       return taskService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") Long id) {
        Optional<TaskEntityDTO> task = taskService.findById(id);
        if (task.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Task with id " + id + " not found")
                    .build();
        }
        TaskEntityDTO taskDTO = task.get();
        return Response.ok(taskDTO).build();
    }

    @POST
    @Path("/users/{userId}")
    public Response createTask(@PathParam("userId") Long userId, @Valid TaskEntity taskEntity) {
        TaskEntityDTO newTask = taskService.createTask(taskEntity,userId);
        return Response.created(URI.create("/tasks/" + newTask.id()))
                .entity(newTask)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") Long id, @Valid TaskEntity taskEntity) {
        TaskEntityDTO updatedTask = taskService.updateTask(taskEntity,id);
        return Response.ok(updatedTask).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTaskById(@PathParam("id") Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Task non trovato")
                    .build();
        }
        return Response.noContent().build();

    }

}
