/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ggentil
 */
@Entity
public class Voyance implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idVoyance;
    @Temporal(TemporalType.DATE)
    private Date heureDebut;
    @Temporal(TemporalType.DATE)
    private Date heureFin;
    String commentaire;
    
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Client client;

    public Voyance(Employe employe, Medium medium, Client client, Date debut) {
        this.employe = employe;
        this.medium = medium;
        this.client = client;
        this.heureDebut=debut;
    }

    public Voyance() {
    }

    public Employe getEmploye() {
        return employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public Client getClient() {
        return client;
    }
    
    
    
}
