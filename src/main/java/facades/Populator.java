/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.*;
import utils.EMF_Creator;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Role user = new Role("user");
        Role admin = new Role("admin");
        User user1 = new User("useradmin", "ua123");
        User user2 = new User("admin", "a123");
        User user3 = new User("user", "u123");
        User user4 = new User("user1", "u123");
        User user5 = new User("user2", "u123");
        User user6 = new User("user3", "u123");

        Project p1 = new Project("Project1", "This is project 1");
        Project p2 = new Project("Project2", "This is project 2");
        Project p3 = new Project("Project3", "This is project 3");
        Project p4 = new Project("Project4", "This is project 4");

        Developer d1 = new Developer("Hans", "23456789", 350.0, user3);
        Developer d2 = new Developer("Yvonne", "23456780", 400.0, user4);
        Developer d3 = new Developer("Lene", "23456781", 700.0, user5);
        Developer d4 = new Developer("Karl", "23456782", 150.0, user6);

        ProjectHours ph1 = new ProjectHours(5, 1L, "Pay this!", d1, p1);
        ProjectHours ph2 = new ProjectHours(3, 5L, "Pay this!", d2, p1);
        ProjectHours ph3 = new ProjectHours(2, 4L, "Pay this!", d3, p2);


        p1.addDeveloper(d1);
        p1.addDeveloper(d2);
        p2.addDeveloper(d3);

        user1.addRole(user);
        user1.addRole(admin);
        user2.addRole(admin);
        user3.addRole(user);
        user4.addRole(user);
        user5.addRole(user);
        user6.addRole(user);


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
        em.close();

    }
    
    public static void main(String[] args) {
        populate();
    }
}
