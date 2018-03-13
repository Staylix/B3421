/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ggentil
 */
@Embeddable
public class ClientIdentite {
    String civilite;
    String nom;
    String prenom;
    @Temporal(TemporalType.DATE)
    Date dateNaissance;

    public ClientIdentite() {
    }
    
    

    public ClientIdentite(String civilite, String Nom, String Prenom, Date dateNaissance) {
        this.civilite = civilite;
        this.nom = Nom;
        this.prenom = Prenom;
        this.dateNaissance = dateNaissance;
    }
    
}
