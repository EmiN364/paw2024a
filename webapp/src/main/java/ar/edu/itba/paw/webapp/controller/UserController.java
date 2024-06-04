package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Path("/users")
@Component
public class UserController {

    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response listUsers(@QueryParam("page") @DefaultValue("1") final int page) {
        List<User> users = us.listUsers(page);

        return Response.ok(users)
                .link(URI.create(""), "prev").link(URI.create(""), "next")
                .link(URI.create(""), "first").link(URI.create(""), "last")
                .build();
    }
    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createUser() {
        return null;
    }

    @GET
    @Path("/{id}")
    public Response getUser(final int userId) {
        return null;
    }

    @DELETE
    public Response deleteUser(final int userId) {
        return null;
    }
}
