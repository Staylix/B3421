/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEmploye;
    private String nom;
    private String prenom;
    private boolean disponible;
    private String mail;
    private String numero;
    @Version
    private Integer version;
    @OneToMany(mappedBy="employe")
    private List<Voyance> histoEmploye;
    public static final int debut=8;
    public static final int fin=18;
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
        
    } 

    public boolean isDisponible() {
        return disponible;
    }
    
    

    public Employe() {
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

    
    
}
