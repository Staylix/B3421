package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Cette classe définie l'objet métier ClientCoordonnees, représentant les
 * coordonnees d'un client.
 * @author B3421
 */
@Embeddable
public class ClientCoordonnees implements Serializable {
     
    private String adresse;
    private String numero;
    private String adresseElectronique;

    public ClientCoordonnees() {
    }
    
    public ClientCoordonnees(String adresse, String numero, String adresseElectronique) {
        this.adresse = adresse;
        this.numero = numero;
        this.adresseElectronique = adresseElectronique;
    }

    public String getAdresseElectronique() {
        return adresseElectronique;
    }

}
