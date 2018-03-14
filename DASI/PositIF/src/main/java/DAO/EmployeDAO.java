/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import model.Employe;

/**
 *
 * @author ggentil
 */
public class EmployeDAO {
    public static void persister (Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }
    public static void update (Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(e);
    }
}
