package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DeveloperDTO;
import dtos.UserDTO;
import facades.DeveloperFacade;
import facades.ProjectFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("developers")
public class DeveloperResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DeveloperFacade FACADE =  DeveloperFacade.getDeveloperFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("user/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id")String username) throws EntityNotFoundException {
        DeveloperDTO developerDTO = FACADE.getDeveloperFromUsername(username);
        return Response.ok().entity(GSON.toJson(developerDTO)).build();

    }


}
