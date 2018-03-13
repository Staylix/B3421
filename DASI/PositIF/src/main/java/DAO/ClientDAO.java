/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Client;
import dao.JpaUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author ggentil
 */
public class ClientDAO {
    public static void Persister (Client C) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(C);
    }
}
