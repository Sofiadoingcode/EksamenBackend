package facades;

import dtos.DeveloperDTO;
import dtos.ProjectDTO;
import dtos.ProjectHoursDTO;
import entities.Developer;
import entities.Project;
import entities.ProjectHours;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    public List<ProjectHoursDTO> getAllPHFromProject(Long projectID) {
            EntityManager em = getEntityManager();
            Query queryListPHs = em.createQuery("SELECT ph FROM ProjectHours ph WHERE ph.project.id = :project_id ", ProjectHours.class);
            queryListPHs.setParameter("project_id", projectID);
            List<ProjectHours> listPHs = queryListPHs.getResultList();
            List<ProjectHoursDTO> phs = new ArrayList<>();
            for (int i = 0; i < listPHs.size(); i++) {
                phs.add(new ProjectHoursDTO(listPHs.get(i)));
            }
            return phs;

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
