/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ggentil
 */
@Embeddable
public class ClientIdentite implements Serializable {
    private String civilite;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    public ClientIdentite() {
    }
    
    

    public ClientIdentite(String civilite, String Nom, String Prenom, Date dateNaissance) {
        this.civilite = civilite;
        this.nom = Nom;
        this.prenom = Prenom;
        this.dateNaissance = dateNaissance;
    }
    
}
