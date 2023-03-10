package facades;

import dtos.*;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectHoursFacadeTest {
    private static EntityManagerFactory emf;
    private static ProjectHoursFacade facade;

    Role user, admin;
    User user1, user2, user3, user4, user5, user6;
    Project p1, p2, p3, p4, p5;
    Developer d1, d2, d3, d4;
    ProjectHours ph1, ph2, ph3, ph4;
    MixProjectDTO m1;

    public ProjectHoursFacadeTest() {
    }
    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ProjectHoursFacade.getProjectHoursFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNamedQuery("ProjectHours.deleteAllRows").executeUpdate();
            em.createNamedQuery("Developer.deleteAllRows").executeUpdate();
            em.createNamedQuery("Project.deleteAllRows").executeUpdate();

            user = new Role("user");
            admin = new Role("admin");
            user1 = new User("useradmin", "ua123");
            user2 = new User("admin", "a123");
            user3 = new User("user", "u123");
            user4 = new User("user1", "u123");
            user5 = new User("user2", "u123");
            user6 = new User("user3", "u123");

            p1 = new Project("Project1", "This is project 1");
            p2 = new Project("Project2", "This is project 2");
            p3 = new Project("Project3", "This is project 3");
            p4 = new Project("Project4", "This is project 4");
            p5 = new Project("Project5", "This is project 5");

            d1 = new Developer("Hans", "23456789", 350.0);
            d2 = new Developer("Yvonne", "23456780", 400.0);
            d3 = new Developer("Lene", "23456781", 700.0);
            d4 = new Developer("Karl", "23456782", 150.0);

            ph1 = new ProjectHours(5, 1, "Pay this!", d1, p1);
            ph2 = new ProjectHours(3, 5, "Pay this!", d2, p1);
            ph3 = new ProjectHours(2, 4, "Pay this!", d3, p2);
            ph4 = new ProjectHours(6, 4, "Pay this!");



            user1.addRole(user);
            user1.addRole(admin);
            user2.addRole(admin);
            user3.addRole(user);
            user4.addRole(user);
            user5.addRole(user);
            user6.addRole(user);

            user3.setDeveloper(d1);
            user4.setDeveloper(d2);
            user5.setDeveloper(d3);
            user6.setDeveloper(d4);

            p1.addDeveloper(d1);
            p1.addDeveloper(d2);
            p2.addDeveloper(d3);

            em.persist(d1);
            em.persist(d2);
            em.persist(d3);
            em.persist(d4);
            em.persist(user);
            em.persist(admin);
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            em.persist(user5);
            em.persist(user6);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(ph1);
            em.persist(ph2);
            em.persist(ph3);


            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllPHs(){
        System.out.println(facade.getAllPH());
        assertEquals(3, facade.getAllPH().size());

    }

    @Test
    public void testGetPHById() throws Exception {

        ProjectHours expected = ph1;
        ProjectHoursDTO phDTO = facade.getById(ph1.getId());
        ProjectHours actual = new ProjectHours(phDTO);

        assertEquals(expected, actual);

    }

    @Test
    public void testGetDevFromPH() {
        Developer expected = d1;
        DeveloperDTO devDTO = facade.getDevFromPH(ph1.getId());
        Developer actual = new Developer(devDTO);

        assertEquals(expected, actual);


    }


    @Test
    public void testCreateProjectHours(){

        ProjectHours expected = ph4;
        ProjectHoursDTO phDTO = facade.createProjectHours(new ProjectHoursDTO(ph4), d4.getId(), p2.getId());
        ProjectHours actual = new ProjectHours(phDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetProjectHoursOnProject(){

        List<ProjectHoursDTO> actual = facade.getAllPHFromProject(p1.getId());
        System.out.println(actual);
        assert(actual.contains(new ProjectHoursDTO(ph1)));
        assert(actual.contains(new ProjectHoursDTO(ph2)));
    }

    @Test
    public void testGetProjectHoursOnProjectandDev(){

        List<ProjectHoursDTO> actual = facade.getAllPHFromProjectandDev(p1.getId(), d1.getId());
        System.out.println(actual);
        assert(actual.contains(new ProjectHoursDTO(ph1)));
    }

    @Test
    public void testCalculateAllHoursSpentOnProject() {
        double actual = facade.calculateAllHoursSpentOnProject(p1.getId());
        double expected = 8;
        assertEquals(expected, actual);

    }

    @Test
    public void testCalculateFullPriceOfProject() {
        double actual = facade.calculateFullPriceOfProject(p1.getId());
        double expected = 2950;
        assertEquals(expected, actual);

    }

    @Test
    public void testCalculateFullPriceOfOnePH() {
        double actual = facade.calculateFullPriceOfOnePH(ph1.getId());
        double expected = 1750;
        assertEquals(expected, actual);

    }

    @Test
    public void testGetProjectStatistics() {
        MixProjectDTO actual = facade.getProjectStatistics(p1.getId());
        MixProjectDTO expected = new MixProjectDTO(8, 2950);
        assertEquals(expected, actual);

    }

    @Test
    public void testGetPHStats() {
        PHMixDTO actual = facade.getPHStats(ph1.getId());
        PHMixDTO expected = new PHMixDTO(1750);
        assertEquals(expected, actual);
    }

    @Test
    void testUpdatePH() throws EntityNotFoundException {

        ph3.setHoursSpent(7.0);
        ph3.setDescription("I am Updated");
        ph3.setUserStory(4);

        ProjectHours expected = ph3;
        ProjectHoursDTO phDTO = new ProjectHoursDTO(ph3);
        ProjectHoursDTO actualPHDTO = facade.updatePH(phDTO, p2.getId(), d3.getId());
        ProjectHours actual = new ProjectHours(actualPHDTO);

        assertEquals(expected, actual);

    }

    @Test
    void testDeletePH() throws EntityNotFoundException {

        ProjectHoursDTO phDTO = facade.deletePH(ph3.getId());
        ProjectHours ph = new ProjectHours(phDTO);
        int expected = 2;
        int actual = facade.getAllPH().size();
        assertEquals(expected, actual);
        assertEquals(ph,ph3);
    }





}
