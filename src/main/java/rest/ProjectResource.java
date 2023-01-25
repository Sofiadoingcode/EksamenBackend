package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectDTO;
import facades.ProjectFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects")
public class ProjectResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ProjectFacade FACADE =  ProjectFacade.getProjectFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllProjects() {
        List<ProjectDTO> projectDTOs= FACADE.getAllProjects();
        return Response.ok().entity(GSON.toJson(projectDTOs)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createProject(String content) {
        ProjectDTO projectDTO = GSON.fromJson(content, ProjectDTO.class);
        ProjectDTO projectDTO1 = FACADE.createProject(projectDTO);
        return Response.ok().entity(GSON.toJson(projectDTO1)).build();
    }

}
