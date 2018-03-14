/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import model.Voyance;

/**
 *
 * @author ggentil
 */
public class VoyanceDAO {
    public static void persister (Voyance v) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(v);
    }
    public static void update (Voyance v) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(v);
    }
}
