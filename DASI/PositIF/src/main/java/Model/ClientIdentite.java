package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cette classe définie l'objet métier ClientIdentite, représentant les
 * informations relatives à l'identité du client.
 * @author B3421
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }
   
}
