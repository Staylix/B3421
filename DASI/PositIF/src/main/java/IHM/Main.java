package ihm;

import service.Service;
import dao.JpaUtil;

/**
 * Cette classe sert à lancer l'application.
 *
 * @author B3421
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.init();
        //Service.testerConcurrence(args[0]);
        Service.initialisation();
        Service.simulation();
        JpaUtil.destroy();
    }
    
}
