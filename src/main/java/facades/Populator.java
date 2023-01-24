/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Role;
import entities.User;
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

        user1.addRole(user);
        user1.addRole(admin);
        user2.addRole(admin);
        user3.addRole(user);

        em.persist(user);
        em.persist(admin);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);


        em.getTransaction().commit();
        em.close();

    }
    
    public static void main(String[] args) {
        populate();
    }
}
