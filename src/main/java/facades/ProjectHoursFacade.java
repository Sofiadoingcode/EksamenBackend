package facades;

import dtos.*;
import entities.Developer;
import entities.Project;
import entities.ProjectHours;
import entities.User;

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

    public List<ProjectHoursDTO> getAllPH () {
        EntityManager em = emf.createEntityManager();
        Query queryListProjects = em.createQuery("SELECT ph FROM ProjectHours ph", ProjectHours.class);
        List<ProjectHours> listPHs = queryListProjects.getResultList();

        List<ProjectHoursDTO> phDTOS = new ArrayList<>();
        for (int i = 0; i < listPHs.size(); i++) {
            phDTOS.add(new ProjectHoursDTO(listPHs.get(i)));
        }
        return phDTOS;

    }

    public List<ProjectHoursDTO> getAllPHFromProjectandDev(Long projectID, Long devID) {
            EntityManager em = getEntityManager();
            Query queryListPHs = em.createQuery("SELECT ph FROM ProjectHours ph WHERE ph.developer.id = :dev_id AND ph.project.id = :project_id", ProjectHours.class);
            queryListPHs.setParameter("dev_id", devID);
            queryListPHs.setParameter("project_id", projectID);
            List<ProjectHours> listPHs = queryListPHs.getResultList();
            List<ProjectHoursDTO> phs = new ArrayList<>();
            for (int i = 0; i < listPHs.size(); i++) {
                phs.add(new ProjectHoursDTO(listPHs.get(i)));
            }
            return phs;

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

    public double calculateAllHoursSpentOnProject(Long projectID) {
        double totalHours = 0;
        List<ProjectHoursDTO> phDTOS = getAllPHFromProject(projectID);

        for(ProjectHoursDTO phDTO: phDTOS) {
            totalHours += phDTO.getHoursSpent();

        }

        return totalHours;
    }

    public double calculateFullPriceOfProject(Long projectID) {
        double totalPrice = 0;

        List<DeveloperDTO> devDTOs = DeveloperFacade.getDeveloperFacade(emf).getAllDevelopersOnProject(projectID);
        for(DeveloperDTO devDTO: devDTOs) {
            List<ProjectHoursDTO> phDTOs = getAllPHFromProjectandDev(projectID, devDTO.getId());
            for(ProjectHoursDTO phDTO: phDTOs) {
                totalPrice += phDTO.getHoursSpent()*devDTO.getBillingPrHour();
            }

        }

        return totalPrice;
    }

    public double calculateFullPriceOfOnePH(Long phID) {
        double totalPrice = 0;
        DeveloperDTO devDTO = getDevFromPH(phID);
        ProjectHoursDTO phDTO = getById(phID);
        totalPrice = devDTO.getBillingPrHour() * phDTO.getHoursSpent();

        return totalPrice;
    }

    public PHMixDTO getPHStats (Long phID) {
        double totalPH = calculateFullPriceOfOnePH(phID);

        PHMixDTO phMixDTO = new PHMixDTO(totalPH);

        return phMixDTO;

    }

    public DeveloperDTO getDevFromPH (Long phID) {
        EntityManager em = getEntityManager();
        ProjectHours ph = em.find(ProjectHours.class, phID);
        DeveloperDTO devDTO = DeveloperFacade.getDeveloperFacade(emf).getById(ph.getDeveloper().getId());

        return devDTO;

    }

    public ProjectHoursDTO getById(Long id) {
        EntityManager em = emf.createEntityManager();

        ProjectHours ph = em.find(ProjectHours.class, id);

        return new ProjectHoursDTO(ph);
    }

    public MixProjectDTO getProjectStatistics (Long projectID) {
        double totalHours = calculateAllHoursSpentOnProject(projectID);
        double totalPrice = calculateFullPriceOfProject(projectID);

        MixProjectDTO mixProjectDTO = new MixProjectDTO(totalHours, totalPrice);

        return mixProjectDTO;

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

    public ProjectHoursDTO updatePH(ProjectHoursDTO phDTO, Long projectID, Long devID) {
        EntityManager em = getEntityManager();
        ProjectHours ph = new ProjectHours(phDTO);
        Developer developer;
        Project project;
        ProjectHoursDTO phUpdated;
        try {
            em.getTransaction().begin();
            developer = em.find(Developer.class, devID);
            project = em.find(Project.class, projectID);
            ph.setDeveloper(developer);
            ph.setProject(project);
            ProjectHours ph1 = em.merge(ph);
            em.getTransaction().commit();
            phUpdated = new ProjectHoursDTO(ph1);
        } finally {
            em.close();
        }
        return phUpdated;
    }

    public ProjectHoursDTO deletePH(Long phID) {
        EntityManager em = getEntityManager();
        ProjectHoursDTO phDTO;
        try {
            em.getTransaction().begin();
            ProjectHours ph = em.find(ProjectHours.class, phID);
            phDTO = new ProjectHoursDTO(ph);
            em.remove(ph);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return phDTO;
    }


}
