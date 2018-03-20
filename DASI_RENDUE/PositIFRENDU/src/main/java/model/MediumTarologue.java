/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Entity;


/**
 *
 * @author ggentil
 */

@Entity
public class MediumTarologue extends Medium{
    private String cartes;

    public MediumTarologue(String nom, String bio,String cartes) {
        super(nom,bio);
        this.cartes = cartes;
    }

    public MediumTarologue() {
    }
    
    
}


