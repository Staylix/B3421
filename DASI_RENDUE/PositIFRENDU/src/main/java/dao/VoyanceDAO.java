package dao;

import javax.persistence.EntityManager;
import model.Voyance;

/**
 * Cette classe fournit des méthodes statiques necessaires afin d'accéder à la
 * couche de persistence pour persister ou trouver ue voyance.
 *
 * @author B3421
 */
public class VoyanceDAO {

    public static void persister(Voyance v) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(v);
    }

    public static void update(Voyance v) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(v);
    }
}
