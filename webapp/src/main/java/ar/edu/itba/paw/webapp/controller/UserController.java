package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import ar.edu.itba.paw.webapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/users")
@Component
public class UserController {

    private final UserService us;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response listUsers(@QueryParam("page") @DefaultValue("1") final int page) {
        List<UserDto> users = us.listUsers(page).stream().map(UserDto.mapper(uriInfo)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<UserDto>>(users) {})
                .link(URI.create(""), "prev").link(URI.create(""), "next")
                .link(URI.create(""), "first").link(URI.create(""), "last")
                .build();
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public Response createUser(final UserDto user) {

        User newUser = us.create(user.getUsername(), user.getPassword());

        return Response.created(uriInfo.getBaseUriBuilder()
                .path("users").path(String.valueOf(newUser.getUserId())).build())
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") final int userId) {
        Optional<UserDto> maybeUser = us.findById(userId).map(UserDto.mapper(uriInfo));

        if (maybeUser.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(maybeUser.get()).build();
    }

    @DELETE
    public Response deleteUser(final int userId) {
        return null;
    }
}
