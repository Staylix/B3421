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
public class MediumAstrologue extends Medium{
    private String ecole;
    private String promotion;

    public MediumAstrologue() {
    }

    public MediumAstrologue(String nom, String bio, String ecole, String promotion) {
        super(nom,bio);
        this.ecole = ecole;
        this.promotion = promotion;
    }
    
    
}
