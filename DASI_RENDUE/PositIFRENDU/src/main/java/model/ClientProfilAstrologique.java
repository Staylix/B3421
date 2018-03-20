package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Cette classe définie l'objet métier ClientProfilAstrologique, représentant 
 * le profil astrologique d'un client.
 * @author B3421
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
