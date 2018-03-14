/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDAO;
import dao.EmployeDAO;
import dao.VoyanceDAO;
import dao.JpaUtil;
import dao.MediumDAO;
import model.Client;
import model.Employe;
import model.Medium;
import model.Voyance;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ggentil
 */
public class Service {
    
    
    public static void inscrireClient (Client client) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDAO.persister(client);
        try {
            JpaUtil.validerTransaction();
        }
        catch (Exception e) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
    }
    
    public void determinerMediums(boolean astrologue, boolean tarotlogue, boolean voyant) {
        List<Medium> torologues;
        
        
    }
    
    public static void creerVoyance (Voyance voyance) {
        Employe e = voyance.getEmploye();
        Client c =voyance.getClient();
        Medium m = voyance.getMedium();
        e.getHistoEmploye().add(voyance);
        c.getHistoClient().add(voyance);
        m.getHistoMedium().add(voyance);
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDAO.update(c);
        MediumDAO.update(m);
        EmployeDAO.update(e);
        VoyanceDAO.persister(voyance);
        
        try {
            JpaUtil.validerTransaction();
        }
        catch (Exception exception) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
    }
   
}
