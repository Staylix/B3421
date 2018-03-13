/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.persistence.Embeddable;

/**
 *
 * @author ggentil
 */
@Embeddable
public class ClientProfilAstrologique {
    String signeZodiaque;
    String signeChinois;
    String couleur;
    String animalTotem;

    public ClientProfilAstrologique() {
    }

    
    
    public ClientProfilAstrologique(String signeZodiaque, String signeChinois, String couleur, String animalTotem) {
        this.signeZodiaque = signeZodiaque;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }
    
}
