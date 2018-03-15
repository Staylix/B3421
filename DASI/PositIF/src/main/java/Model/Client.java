/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 *
 * @author ggentil
 */
@Entity
public class Client implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClient;
    @Embedded
    private ClientIdentite identite;
    @Embedded
    private ClientCoordonnees coordonnees;
    @Embedded
    private ClientProfilAstrologique profileAstrologique;

    @OneToMany(mappedBy="client")
    private List<Voyance> histoClient;
    
    public Client() {
    }    
    
    public Client(String civilite, String nom, String prenom, Date dateNaissance, String adresse, String numero, String adresseElectronique) {
        identite = new ClientIdentite(civilite, nom, prenom, dateNaissance);
        coordonnees = new ClientCoordonnees(adresse, numero, adresseElectronique); 
        histoClient=new ArrayList<Voyance>();
    }

    public ClientIdentite getIdentite() {
        return identite;
    }
    
    public void setProfileAstrologique(ClientProfilAstrologique profileAstrologique) {
        this.profileAstrologique = profileAstrologique;
    }

    public ClientProfilAstrologique getProfileAstrologique() {
        return profileAstrologique;
    }

    public List<Voyance> getHistoClient() {
        return histoClient;
    }

    public ClientCoordonnees getCoordonnees() {
        return coordonnees;
    }

    public Long getIdClient() {
        return idClient;
    }
    
    
    
    
}
