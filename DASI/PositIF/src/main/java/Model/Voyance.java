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
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureDebut;
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureFin;
    String commentaire;
    int amour;
    int sante;
    int travail;
    
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Client client;

    public Voyance(Employe employe, Medium medium, Client client) {
        this.employe = employe;
        this.medium = medium;
        this.client = client;
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

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setAmour(int amour) {
        this.amour = amour;
    }

    public void setSante(int sante) {
        this.sante = sante;
    }

    public void setTravail(int travail) {
        this.travail = travail;
    }

    public Long getIdVoyance() {
        return idVoyance;
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public int getAmour() {
        return amour;
    }

    public int getSante() {
        return sante;
    }

    public int getTravail() {
        return travail;
    }

     
    
    
    
}
