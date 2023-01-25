package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectDTO;
import dtos.ProjectHoursDTO;
import facades.ProjectHoursFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("ph")
public class ProjectHoursResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final ProjectHoursFacade FACADE =  ProjectHoursFacade.getProjectHoursFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("{dev_id}/{project_id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createProjectHours(String content, @PathParam("dev_id") Long devID, @PathParam("project_id") Long projectID) {
        ProjectHoursDTO projecthoursDTO = GSON.fromJson(content, ProjectHoursDTO.class);
        ProjectHoursDTO phDTO1 = FACADE.createProjectHours(projecthoursDTO, devID, projectID);
        return Response.ok().entity(GSON.toJson(phDTO1)).build();
    }

    @GET
    @Path("project/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllProjectHoursOnProject(@PathParam("id") Long projectID) {
        List<ProjectHoursDTO> phsDTOs= FACADE.getAllPHFromProject(projectID);
        return Response.ok().entity(GSON.toJson(phsDTOs)).build();
    }


    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePH(@PathParam("id") Long phID) throws EntityNotFoundException {
        ProjectHoursDTO deleted = FACADE.deletePH(phID);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }
}
