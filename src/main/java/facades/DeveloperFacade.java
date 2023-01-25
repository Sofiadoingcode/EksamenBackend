package facades;

import dtos.DeveloperDTO;
import entities.Developer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DeveloperFacade {
    private static EntityManagerFactory emf;
    private static DeveloperFacade instance;

    private DeveloperFacade() {
    }


    public static DeveloperFacade getDeveloperFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DeveloperFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public DeveloperDTO getDeveloperFromUsername (String username) {
        EntityManager em = getEntityManager();
        Query queryListDevs = em.createQuery("SELECT d FROM Developer d WHERE d.user.userName = :username ", Developer.class);
        queryListDevs.setParameter("username", username);
        List<Developer> listDeveloper = queryListDevs.getResultList();

        return new DeveloperDTO(listDeveloper.get(0));

    }

}
