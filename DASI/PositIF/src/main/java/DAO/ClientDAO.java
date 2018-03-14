/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Client;
import javax.persistence.EntityManager;

/**
 *
 * @author ggentil
 */
public class ClientDAO {
    public static void persister (Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    public static void update (Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }
    
}
