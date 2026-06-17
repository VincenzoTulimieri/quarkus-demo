package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return new Response("Bello de zio");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saluta(Request request){
        return new Response("Ciao bello hai scritto: " + request.message());
    }


    public record Request(String message){

    }

    public record Response(String response){

    }
}
