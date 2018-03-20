/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author ggentil
 */
@Entity
public class Employe implements Serializable, Comparable<Employe> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEmploye;
    
    @OneToMany(mappedBy="employe",cascade=CascadeType.ALL)
    private List<Voyance> histoEmploye;
    private Client clientEnCours;
    private String nom;
    private String prenom;
    private boolean disponible;
    private String mail;
    private String numero;
    @Version
    private Integer version;
    
    public static final int DEBUT=8;
    public static final int FIN=24;
    @ManyToMany(mappedBy="incarnePar")
    private List<Medium> incarne;
    
    public List<Voyance> getHistoEmploye() {
        return histoEmploye;
    }
    
    public Employe(String nom, String prenom,String mail,String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.disponible = true;
        this.mail=mail;
        this.numero=numero;
        histoEmploye=new ArrayList<Voyance>();
        incarne=new ArrayList<Medium>();
        clientEnCours=null;
    } 

    public void setClientEnCours(Client clientEnCours) {
        this.clientEnCours = clientEnCours;
    }

    public Long getIdEmploye() {
        return idEmploye;
    }
    
    
    public String getMail() {
        return mail;
    }
    
    

    public boolean isDisponible() {
        return disponible;
    }

    public List<Medium> getIncarne() {
        return incarne;
    }
    
    public Employe() {
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    @Override
    public int compareTo(Employe e){
        return this.getHistoEmploye().size()-e.getHistoEmploye().size();
    } 
    
}
