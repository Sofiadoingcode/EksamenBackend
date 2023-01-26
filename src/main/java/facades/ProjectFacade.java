package facades;

import dtos.MixProjectDTO;
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

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new ProjectDTO(project);
    }

    public ProjectDTO addDeveloperToProject(Long developerID, Long projectID) {
        EntityManager em = getEntityManager();
        Developer developer;
        Project project;
        try {
            em.getTransaction().begin();
            developer = em.find(Developer.class, developerID);
            project = em.find(Project.class, projectID);
            project.addDeveloper(developer);
            em.merge(project);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new ProjectDTO(project);

    }

    public List<ProjectDTO> getAllProjectsOnDev (Long devID) {
        EntityManager em = emf.createEntityManager();
        Query queryListProjects = em.createQuery("SELECT p FROM Project  p INNER JOIN p.developers d WHERE d.id = :dev_id" , Project.class);
        queryListProjects.setParameter("dev_id", devID);
        List<Project> listProjects = queryListProjects.getResultList();

        List<ProjectDTO> projects = new ArrayList<>();
        for (int i = 0; i < listProjects.size(); i++) {
            projects.add(new ProjectDTO(listProjects.get(i)));
        }
        return projects;

    }


    public void calculate (Long projectID) {
        EntityManager em = emf.createEntityManager();
        List<ProjectHoursDTO> phList = ProjectHoursFacade.getProjectHoursFacade(emf).getAllPHFromProject(projectID);



    }



//    public MixProjectDTO getFullHoursAndPrice(Long projectID) {
//
//
//
//    }


}
