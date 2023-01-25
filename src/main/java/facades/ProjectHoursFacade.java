package facades;

import dtos.ProjectDTO;
import dtos.ProjectHoursDTO;
import entities.Developer;
import entities.Project;
import entities.ProjectHours;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProjectHoursFacade {
    private static EntityManagerFactory emf;
    private static ProjectHoursFacade instance;

    private ProjectHoursFacade() {
    }


    public static ProjectHoursFacade getProjectHoursFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ProjectHoursFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ProjectHoursDTO createProjectHours(ProjectHoursDTO phDTO, Long devID, Long projectID) {
        ProjectHours ph = new ProjectHours(phDTO);
        Developer developer;
        Project project;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            developer = em.find(Developer.class, devID);
            project = em.find(Project.class, projectID);
            ph.setDeveloper(developer);
            ph.setProject(project);
            em.persist(ph);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ProjectHoursDTO(ph);
    }


}
