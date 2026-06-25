package org.acme.user.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.user.entity.UserEntity;
import org.acme.user.service.UserService;

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
    public UserEntity getUserById(Long id){
        return userService.getUserById(id);
    }


}
