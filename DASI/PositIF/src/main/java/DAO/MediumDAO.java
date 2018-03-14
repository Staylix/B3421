/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import model.Medium;

/**
 *
 * @author ggentil
 */
public class MediumDAO {
    public static void persister (Medium m) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(m);
    }
    public static void update (Medium m) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(m);
    }
}
