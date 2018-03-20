package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Medium;

/**
 * Cette classe fournit des méthodes statiques necessaires afin d'accéder à la
 * couche de persistence pour persister, modifier ou trouver un medium. Elle
 * permet également de calculer les statistiques relatives à tous les mediums
 * ainsi que de lister les mediums selon leurs spécialités.
 *
 * @author B3421
 */
public class MediumDAO {

    public static void persister(Medium m) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(m);
    }

    public static void update(Medium m) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(m);
    }

    public static List<Medium> getTarologues(boolean taro) {
        if (!taro) {
            return null;
        } else {
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumTarologue m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if (!resultat.isEmpty()) {
                return resultat;
            } else {
                return null;
            }
        }
    }

    public static List<Medium> getAstrologues(boolean astro) {
        if (!astro) {
            return null;
        } else {
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumAstrologue m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if (!resultat.isEmpty()) {
                return resultat;
            } else {
                return null;
            }
        }
    }

    public static List<Medium> getVoyants(boolean voy) {
        if (!voy) {
            return null;
        } else {
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumVoyant m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if (!resultat.isEmpty()) {
                return resultat;
            } else {
                return null;
            }
        }
    }

    public static String statsMedium() {
        String s = "Total de voyances demandees par medium (Histogramme)\n";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select m from MediumAstrologue m");
        List<Medium> astro = (List<Medium>) query.getResultList();
        query = em.createQuery("select m from MediumTarologue m");
        List<Medium> taro = (List<Medium>) query.getResultList();
        query = em.createQuery("select m from MediumVoyant m");
        List<Medium> voyant = (List<Medium>) query.getResultList();
        astro.addAll(taro);
        astro.addAll(voyant);
        if (!astro.isEmpty()) {
            for (Medium m : astro) {
                s += m.getNom() + ":" + m.getHistoMedium().size() + "\n";
            }
            return s + "\n";
        } else {
            return "Aucun medium dans la base.";
        }

    }

    public static Medium trouverMedium(String nom) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select m from MediumVoyant m where m.nom = :nom");
        query.setParameter("nom", nom);
        List<Medium> resultat = (List<Medium>) query.getResultList();
        query = em.createQuery("select m from MediumTarologue m where m.nom = :nom");
        query.setParameter("nom", nom);
        resultat.addAll((List<Medium>) query.getResultList());
        query = em.createQuery("select m from MediumAstrologue m where m.nom = :nom");
        query.setParameter("nom", nom);
        resultat.addAll((List<Medium>) query.getResultList());
        if (!resultat.isEmpty()) {
            return resultat.get(0);
        } else {
            return null;
        }
    }

}
