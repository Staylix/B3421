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

}
