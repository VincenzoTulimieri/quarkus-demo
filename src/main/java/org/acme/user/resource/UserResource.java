package org.acme.user.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.user.entity.UserEntity;
import org.acme.user.service.UserService;

import java.net.URI;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id){
        UserEntity user = userService.getUserById(id);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Utente non trovato")
                    .build();
        }
        return Response.ok(user).build();
    }

    @POST
    public Response createUser(@Valid UserEntity user){
        UserEntity createdUser = userService.createUser(user);
        return Response.created(URI.create("/users/" + createdUser.getId()))
                .entity(createdUser)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid UserEntity newData){
            UserEntity updatedUser = userService.updateUser(id, newData);
            return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id){
        boolean deleted = userService.deleteUser(id);
        if (!deleted){
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("Utente non trovato")
                    .build();
        }
        return Response.noContent().build();
    }

}
