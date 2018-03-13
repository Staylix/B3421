/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.ClientDAO;
import Model.Client;
import dao.JpaUtil;
import java.util.Date;

/**
 *
 * @author ggentil
 */
public class Service {
    
    
    public static void inscrireClient(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numero, String adresseElectronique) {
        Client client = new Client(civilite, nom, prenom, dateNaissance, adresse, numero, adresseElectronique);
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDAO.Persister(client);
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    };
}
