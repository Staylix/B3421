package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Employe;

/**
 * Cette classe fournit des méthodes statiques necessaires afin d'accéder à la
 * couche de persistence pour persister, modifier ou trouver un employe . Elle
 * permet également de calculer les statistiques relatives à tous les employés.
 *
 * @author B3421
 */
public class EmployeDAO {

    public static void persister(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }

    public static void update(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(e);
    }

    public static Employe trouverEmploye(String mail) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select e from Employe e where e.mail = :mail");
        query.setParameter("mail", mail);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        if (!resultat.isEmpty()) {
            return resultat.get(0);
        } else {
            return null;
        }
    }

    public static String statsEmployes() {
        String s = "Total de voyances realisees par employe (Histogramme)\n";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select e from Employe e");
        List<Employe> resultat = (List<Employe>) query.getResultList();
        if (!resultat.isEmpty()) {
            int totalVoyance = 0;
            for (Employe e : resultat) {
                int nbrVoyances = e.getHistoEmploye().size();
                s += e.getNom() + " " + e.getPrenom() + ":" + nbrVoyances + "\n";
                totalVoyance += nbrVoyances;
            }
            s += "\nRepartition des voyances entre employes (\"Camembert\")\n";
            if (totalVoyance != 0) {
                for (Employe e : resultat) {

                    int pctVoyances = 100 * e.getHistoEmploye().size() / totalVoyance;
                    s += e.getNom() + " " + e.getPrenom() + ":" + pctVoyances + "\n";

                }
                return s + "\n";
            } else {
                return "Aucune voyance n'a ete faite.";
            }
        } else {
            return "Aucun employe dans la base.";
        }
    }
}
