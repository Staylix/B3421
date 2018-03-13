/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author ggentil
 */
@Entity
public class Client {
    @Id @GeneratedValue
    Long idClient;
    @Embedded
    ClientIdentite identite;
    @Embedded
    ClientCoordonnees coordonnees;
    @Embedded
    ClientProfilAstrologique profileAstrologique = null;

    public Client() {
    }    
    
    public Client(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numero, String adresseElectronique) {
        identite = new ClientIdentite(civilite, nom, prenom, dateNaissance);
        coordonnees = new ClientCoordonnees(adresse, numero, adresseElectronique);
    }
    
    
}
