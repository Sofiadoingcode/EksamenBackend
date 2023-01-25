package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ProjectDTO;
import entities.*;
import facades.ProjectFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProjectResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static ProjectFacade facade;

    Role user, admin;
    User user1, user2, user3, user4, user5, user6;
    Project p1, p2, p3, p4, p5;
    Developer d1, d2, d3, d4;
    ProjectHours ph1, ph2, ph3;


    public ProjectResourceTest() {
    }

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

        facade = ProjectFacade.getProjectFacade(emf);

    }


    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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

    @Test
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetAllProjects() {
        List<ProjectDTO> projectDTOS;

        projectDTOS = given()
                .contentType("application/json")
                .when()
                .get("/projects")
                .then()
                .extract().body().jsonPath().getList("", ProjectDTO.class);


        assertThat(projectDTOS, containsInAnyOrder(new ProjectDTO(p1), new ProjectDTO(p2), new ProjectDTO(p3), new ProjectDTO(p4)));

    }

    @Test
    public void testCreateProject() {
        ProjectDTO projectDTO = new ProjectDTO(p5);
        String requestBody = GSON.toJson(projectDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/projects")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Project5"));

    }

    @Test
    public void testAddDeveloperToProject() {
        p3.addDeveloper(d4);
        ProjectDTO projectDTO = new ProjectDTO(p3);
        String requestBody = GSON.toJson(projectDTO);

        given()
                .header("Content-type", ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/projects/connectdev/"+d4.getId() + "/" + p3.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(p3.getId().intValue()))
                .body("name", equalTo("Project3"));
    }

    @Test
    public void testGetProjectsOnDev() throws Exception {
        List<ProjectDTO> projectDTOS;

        projectDTOS = given()
                .contentType("application/json")
                .when()
                .get("/projects/dev/" + d1.getId())
                .then()
                .extract().body().jsonPath().getList("", ProjectDTO.class);

        assertThat(projectDTOS, containsInAnyOrder(new ProjectDTO(p1)));
    }


}
