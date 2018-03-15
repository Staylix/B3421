/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Employe;
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
    
    public static List<Medium> getTarologues(boolean taro){
        if(!taro){
            return null;
        }
        else{
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumTarologue m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if(!resultat.isEmpty()){
                return resultat;
            }
            else{
                return null;
            }
        }
    }
    public static List<Medium> getAstrologues(boolean astro){
        if(!astro){
            return null;
        }
        else{
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumAstrologue m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if(!resultat.isEmpty()){
                return resultat;
            }
            else{
                return null;
            }
        }
    }
    public static List<Medium> getVoyants(boolean voy){
        if(!voy){
            return null;
        }
        else{
            EntityManager em = JpaUtil.obtenirEntityManager();
            Query query = em.createQuery("select m from MediumVoyant m");
            List<Medium> resultat = (List<Medium>) query.getResultList();
            if(!resultat.isEmpty()){
                return resultat;
            }
            else{
                return null;
            }
        }
    }
    
    public static String statsMedium(){
        String s="Total de voyances demandees par medium (Histogramme)";
        EntityManager em = JpaUtil.obtenirEntityManager();
        System.out.println("je beugue");
        Query query = em.createQuery("select m from MediumAstrologue m");
        List<Medium> astro = (List<Medium>) query.getResultList();
        query = em.createQuery("select m from MediumTarologue m");
        List<Medium> taro= (List<Medium>) query.getResultList();
        query = em.createQuery("select m from MediumVoyant m");
        List<Medium> voyant=(List<Medium>) query.getResultList();
        astro.addAll(taro);
        astro.addAll(voyant);
        if(!astro.isEmpty()){
            for (Medium m : astro) {
                s+=m.getNom()+":"+m.getHistoMedium().size()+"\n";
            }
            return s;
        }
        else{
            return "Aucun medium dans la base.";
        }
       
    }
    
}
