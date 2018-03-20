package ihm;

import service.Service;
import dao.JpaUtil;

/**
 * Cette classe sert à lancer l'application.
 *
 * @author B3421
 */
public class Main {


    public static void main(String[] args) {
        JpaUtil.init();
        Service.initialisation();
        JpaUtil.destroy();
    }
    
}
