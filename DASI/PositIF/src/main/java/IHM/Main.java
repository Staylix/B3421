/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import Service.Service;
import dao.JpaUtil;
import java.util.Date;

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
        Date d = new Date(2016, 12, 7);
        Service.inscrireClient("Mr", "Caca", "Lolo", d, "Part Dieux", "06 71 80 25 47", "lolo.caca@gmail.com");
        JpaUtil.destroy();    }
    
}
