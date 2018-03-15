/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author ggentil
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium implements Serializable {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMedium;
    private String nom;
    private String bio;
    
    public String getNom() {
        return nom;
    }
    
    
    @OneToMany(mappedBy="medium")
    private List<Voyance> histoMedium;
    
    @ManyToMany 
    private List<Employe> incarnePar;

    public List<Voyance> getHistoMedium() {
        return histoMedium;
    }

    public List<Employe> getIncarnePar() {
        return incarnePar;
    }
    
    

    public Medium() {
        histoMedium=new ArrayList<Voyance>();
        incarnePar=new ArrayList<Employe>();
    }

    public Medium(String nom, String bio) {
        this.nom = nom;
        this.bio = bio;
        histoMedium=new ArrayList<Voyance>();
        incarnePar=new ArrayList<Employe>();
    }
    
    
    
    
    
    
}
