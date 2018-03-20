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
public class MediumVoyant extends Medium{
    private String support;

    public MediumVoyant() {
    }

    public MediumVoyant(String nom, String bio, String support) {
        super(nom,bio);
        this.support = support;
    }
    
    
}
