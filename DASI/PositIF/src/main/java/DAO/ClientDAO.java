/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Client;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ggentil
 */
public class ClientDAO {
    public static void persister (Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(c);
    }
    
    public static Client trouverClient(String mail){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select c from Client c where c.coordonnees.adresseElectronique = :mail");
        query.setParameter("mail",mail);
        List<Client> resultat = (List<Client>) query.getResultList();
        if(!resultat.isEmpty()){
            return resultat.get(0);
        }
        else{
            return null;
        }
    }
    public static void update (Client c) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(c);
    }
    
}
