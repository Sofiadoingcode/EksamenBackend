package facades;

import dtos.ProjectDTO;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ProjectFacade {
    private static EntityManagerFactory emf;
    private static ProjectFacade instance;

    private ProjectFacade() {
    }


    public static ProjectFacade getProjectFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<ProjectDTO> getAllProjects () {
        EntityManager em = emf.createEntityManager();
        Query queryListProjects = em.createQuery("SELECT p FROM Project p", Project.class);
        List<Project> listProjects = queryListProjects.getResultList();

        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for (int i = 0; i < listProjects.size(); i++) {
            projectDTOS.add(new ProjectDTO(listProjects.get(i)));
        }
        return projectDTOS;

    }


}
