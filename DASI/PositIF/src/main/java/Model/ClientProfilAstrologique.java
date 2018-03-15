/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author ggentil
 */
@Embeddable
public class ClientProfilAstrologique implements Serializable {
    private String signeZodiaque;
    private String signeChinois;
    private String couleur;
    private String animalTotem;

    public ClientProfilAstrologique() {
    }
  
    public ClientProfilAstrologique(String signeZodiaque, String signeChinois, String couleur, String animalTotem) {
        this.signeZodiaque = signeZodiaque;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }
    
    
    
}
