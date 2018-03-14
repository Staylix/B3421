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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d=sdf.parse("1997-12-07");
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Client client = new Client("Mr", "gri", "Gregoire", d, "Part Dieux", "0667170327", "gregoire@gmail.com");
        Service.inscrireClient(client);
        client = new Client("Mr", "nli", "blou", d, "Part Dieux", "0667170327", "grou@gmail.com");
        Service.inscrireClient(client);
        JpaUtil.destroy();    
    }
    
}
