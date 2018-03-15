/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import service.Service;
import dao.JpaUtil;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Client;
import model.Employe;
import model.Medium;
import model.MediumAstrologue;
import model.Voyance;

/**
 *
 * @author ggentil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.init();
        Service.initialisation();
        JpaUtil.destroy();
    }
    
}
