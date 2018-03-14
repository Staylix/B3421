/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
    @OneToMany(mappedBy="employe")
    
    private List<Voyance> histoEmploye;
   
    @ManyToMany(mappedBy="incarnePar")
    private Set<Medium> incarne;
    
    public List<Voyance> getHistoEmploye() {
        return histoEmploye;
    }
    
    public Employe(Long idEmploye, String nom, String prenom, boolean disponible) {
        this.idEmploye = idEmploye;
        this.nom = nom;
        this.prenom = prenom;
        this.disponible = disponible;
    } 

    public Employe() {
    }
    
    
}
