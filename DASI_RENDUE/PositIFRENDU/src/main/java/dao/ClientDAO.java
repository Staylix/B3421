package dao;

import java.util.List;
import model.Client;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Cette classe fournit des méthodes statiques necessaires afin d'accéder à la
 * couche de persistence pour persister, modifier ou trouver un client .
 *
 * @author B3421
 */
public class ClientDAO {

    public static void persister(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }

    public static Client trouverClient(String mail) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select c from Client c where c.coordonnees.adresseElectronique = :mail");
        query.setParameter("mail", mail);
        List<Client> resultat = (List<Client>) query.getResultList();
        if (!resultat.isEmpty()) {
            return resultat.get(0);
        } else {
            return null;
        }
    }

    public static void update(Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }

}
