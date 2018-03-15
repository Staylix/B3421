/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Client;
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
    
     public static Employe trouverEmploye(String mail){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select e from Employe e where e.mail = :mail");
        query.setParameter("mail",mail);
        List<Employe> resultat = (List<Employe>) query.getResultList();
        if(!resultat.isEmpty()){
            return resultat.get(0);
        }
        else{
            return null;
        }
    }
     
     public static String statsEmployes(){
        String s="Total de voyances realisees par employe (Histogramme)";
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select e from Employe e");
        List<Employe> resultat = (List<Employe>) query.getResultList();
        if(!resultat.isEmpty()){
            int totalVoyance=0;
            for (Employe e : resultat) {
                int nbrVoyances=e.getHistoEmploye().size();
                s+=e.getNom()+" "+e.getPrenom()+":"+nbrVoyances+"\n";
                totalVoyance+=nbrVoyances;
            }
            s+="Repartition des voyances entre employes (\"Camembert\")";
            for (Employe e : resultat) {
                int pctVoyances=100*e.getHistoEmploye().size()/totalVoyance;
                s+=e.getNom()+" "+e.getPrenom()+":"+pctVoyances+"\n";
            }
            return s;
        }
        else{
            return "Aucun employe dans la base.";
        }
    }
}
