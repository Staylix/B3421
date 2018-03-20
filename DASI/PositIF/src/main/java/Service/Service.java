package service;

import dao.ClientDAO;
import dao.EmployeDAO;
import dao.VoyanceDAO;
import dao.JpaUtil;
import dao.MediumDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import model.Client;
import model.Employe;
import model.Medium;
import model.Voyance;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClientProfilAstrologique;
import model.MediumAstrologue;
import model.MediumTarologue;
import model.MediumVoyant;
import util.AstroTest;

/**
 * Cette classe fournit des méthodes statiques necessaires afin d'accéder 
 * aux services définis par les cas d'usage du cahier de charges.
 *
 * @author B3421
 */
public class Service {

    public static Client connecterClient(String mail) {
        JpaUtil.creerEntityManager();
        Client client = ClientDAO.trouverClient(mail);
        JpaUtil.fermerEntityManager();
        return client;
    }

    public static Employe connecterEmploye(String mail) {
        JpaUtil.creerEntityManager();
        Employe employe = EmployeDAO.trouverEmploye(mail);
        JpaUtil.fermerEntityManager();
        return employe;
    }

    public static boolean inscrireClient(Client client) {
        JpaUtil.creerEntityManager();
        Client existant = ClientDAO.trouverClient(client.getCoordonnees().getAdresseElectronique());
        if (existant == null) {
            try {
                AstroTest astro = new AstroTest("ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx");
                try {
                    List<String> s = astro.getProfil(client.getIdentite().getPrenom(), client.getIdentite().getDateNaissance());
                    client.setProfileAstrologique(new ClientProfilAstrologique(s.get(0), s.get(1), s.get(2), s.get(3)));
                } catch (IOException e) {
                    System.err.println("Impossible to create the astrologic profile");
                }
                JpaUtil.ouvrirTransaction();
                ClientDAO.persister(client);
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                Service.envoyerMailSucces(client);
                return true;
            } catch (Exception e) {
                System.err.println("Transaction failed");
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                Service.envoyerMailEchec(client);
                return false;
            }
        } else {
            JpaUtil.fermerEntityManager();
            Service.envoyerMailEchec(client);
            return false;
        }
    }

    public static void inscrireMedium(Medium medium) {
        JpaUtil.creerEntityManager();

        try {
            JpaUtil.ouvrirTransaction();
            MediumDAO.persister(medium);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static void inscrireEmploye(Employe employe) {
        JpaUtil.creerEntityManager();
        try {
            JpaUtil.ouvrirTransaction();
            EmployeDAO.persister(employe);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static List<Medium> determinerMediums(boolean astro, boolean taro, boolean voy) {
        JpaUtil.creerEntityManager();
        List<Medium> tarologues = MediumDAO.getTarologues(taro);
        List<Medium> astrologues = MediumDAO.getAstrologues(astro);
        List<Medium> voyants = MediumDAO.getVoyants(voy);
        JpaUtil.fermerEntityManager();
        List<Medium> resultat = new ArrayList<>();
        if (tarologues != null) {
            resultat.addAll(tarologues);

        }
        if (astrologues != null) {
            resultat.addAll(astrologues);

        }
        if (voyants != null) {
            resultat.addAll(voyants);
        }
        
        return resultat;
    }

    public static Voyance commencerVoyance(Client client, Employe employe, Medium medium) {
        Voyance voyance = new Voyance(employe, medium, client);
        SimpleDateFormat heure = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        voyance.setHeureDebut(d);

        JpaUtil.creerEntityManager();

        try {
            JpaUtil.ouvrirTransaction();
            VoyanceDAO.persister(voyance);
            JpaUtil.validerTransaction();
        } catch (Exception exception) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return voyance;
    }

    public static void finirVoyance(Voyance voyance, int amour, int sante, int travail) { // Voyance contenant les notes
        Date d = new Date();
        voyance.setHeureFin(d);

        JpaUtil.creerEntityManager();
        voyance.setAmour(amour);
        voyance.setSante(sante);
        voyance.setTravail(travail);
        try {
            JpaUtil.ouvrirTransaction();
            VoyanceDAO.update(voyance);
            JpaUtil.validerTransaction();
        } catch (Exception exception) {
            System.err.println("Transaction failed");
            exception.printStackTrace(System.err);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static void cloturerVoyance(Voyance voyance, String commentaire) {
        voyance.setCommentaire(commentaire);
        Employe e = voyance.getEmploye();
        Client c = voyance.getClient();
        Medium m = voyance.getMedium();
        e.getHistoEmploye().add(voyance);
        e.setDisponible(true);
        c.getHistoClient().add(voyance);
        m.getHistoMedium().add(voyance);
        e = Service.connecterEmploye(e.getMail());
        m = Service.identifierMedium(m.getNom());
        c = Service.connecterClient(c.getCoordonnees().getAdresseElectronique());
        e.getHistoEmploye().add(voyance);
        e.setDisponible(true);
        c.getHistoClient().add(voyance);
        m.getHistoMedium().add(voyance);
        JpaUtil.creerEntityManager();

        try {
            JpaUtil.ouvrirTransaction();
            VoyanceDAO.update(voyance);
            ClientDAO.update(c);
            MediumDAO.update(m);
            EmployeDAO.update(e);
            JpaUtil.validerTransaction();
        } catch (Exception exception) {
            System.err.println("Transaction failed");
            exception.printStackTrace(System.err);
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static List<String> realiserVoyance(Client client, int amour, int sante, int travail) {
        AstroTest astro = new AstroTest("ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx");
        List<String> predictions;
        try {
            predictions = astro.getPredictions(client.getProfileAstrologique().getCouleur(), client.getProfileAstrologique().getAnimalTotem(), amour, sante, travail);
            return predictions;
        } catch (IOException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void envoyerMailSucces(Client client) {
        String mail = client.getCoordonnees().getAdresseElectronique();
        String nom = client.getIdentite().getPrenom();
        Long id = client.getIdClient();
        System.out.println("Expediteur : contact@positif.if\nPour : " + mail + "\nSujet : Bienvenue chez Posit'IF");
        System.out.println("Corps :\nBonjour " + nom + ",\nNous vous confirmons votre inscription au service Posit'IF. Votre numero de client est : " + id + ".\n");
    }

    public static void envoyerMailEchec(Client client) {
        String mail = client.getCoordonnees().getAdresseElectronique();
        String nom = client.getIdentite().getPrenom();
        Long id = client.getIdClient();
        System.out.println("Expediteur : contact@positif.if\nPour : " + mail + "\nSujet : Bienvenue chez Posit'IF");
        System.out.println("Corps :\nBonjour " + nom + ",\nVotre inscription au service Posit'IF a malencontreusement echoue... Merci de recommencer ulterieurement.\n");
    }

    public static void envoyerNotif(Employe employe, Client client, Medium medium) {
        String numero = employe.getNumero();
        String mail = client.getCoordonnees().getAdresseElectronique();
        Client persistent = Service.connecterClient(mail);
        Long idClient = persistent.getIdClient();
        String nom = client.getIdentite().getNom();
        String prenom = client.getIdentite().getPrenom();
        String nomMedium = medium.getNom();
        System.out.println("Simulation de SMS à l'employé dont le numéro est : " + numero);
        System.out.println("Voyance demandée pour le client " + prenom + " " + nom + " (#" + idClient + "), Medium : " + nomMedium);
    }

    public static String calculerStatistiques() {
        JpaUtil.creerEntityManager();
        String resultat = MediumDAO.statsMedium() + EmployeDAO.statsEmployes();
        JpaUtil.fermerEntityManager();
        return resultat;
    }

    public static List<Voyance> consulterHisto(Client c) {
        return c.getHistoClient();
    }
    
    //Sert uniquement pour la démo
    public static void afficherVoyance(Voyance v) {
        System.out.println("Voyance "+v.getIdVoyance()+ " faite pour " + v.getClient().getIdClient()+ " par l'employe " +v.getEmploye().getIdEmploye()+" de " +v.getHeureFin()+" a " +v.getHeureDebut());
    }

    public static Employe demanderVoyance(Client client, Medium medium) {
        boolean validee = false;
        Date d = new Date();
        if (d.getHours() <= Employe.DEBUT || d.getHours() >= Employe.FIN) {
            return null;
        } else {
            Employe employe = null;
            List<Employe> possibilites = medium.getIncarnePar();
            List<Employe> resultat = new ArrayList<>();
            if (!possibilites.isEmpty()) {
                for (Employe e : possibilites) {
                    if (e.isDisponible()) {
                        resultat.add(e);
                  }
                }
                Collections.sort(resultat);
            }
            int taille = resultat.size();
            if (taille != 0) {
                employe = resultat.get(0);
                JpaUtil.creerEntityManager();
                int i = 1;
                while (!validee && i < taille) {
                    try {
                        employe.setDisponible(false);
                        employe.setClientEnCours(client);
                        JpaUtil.ouvrirTransaction();
                        EmployeDAO.update(employe);
                        JpaUtil.validerTransaction();
                        JpaUtil.fermerEntityManager();
                        Service.envoyerNotif(employe, client, medium);
                        validee = true;

                    } catch (Exception e) {
                        if (e.getClass().getName().equals("javax.persistence.RollbackException")) {
                            if (i == taille - 1) {
                                System.err.println("Transaction failed");
                                JpaUtil.annulerTransaction();
                                JpaUtil.fermerEntityManager();
                                validee = true;
                            } else {
                                employe.setDisponible(true);
                                employe.setClientEnCours(null);
                                employe = resultat.get(i);
                                i++;

                            }
                        } else {
                            System.err.println("Transaction failed");
                            JpaUtil.annulerTransaction();
                            JpaUtil.fermerEntityManager();
                            validee = true;
                        }

                    } 

                }
            }
            return employe;
        }
    }

    public static Medium identifierMedium(String nom) {
        JpaUtil.creerEntityManager();
        Medium medium = MediumDAO.trouverMedium(nom);
        JpaUtil.fermerEntityManager();
        return medium;
    }

    public static void possibiliteDincarner(Medium medium, Employe employe) {
        medium.getIncarnePar().add(employe);
        employe.getIncarne().add(medium);
        employe = Service.connecterEmploye(employe.getMail());
        medium = Service.identifierMedium(medium.getNom());
        medium.getIncarnePar().add(employe);
        employe.getIncarne().add(medium);
        JpaUtil.creerEntityManager();
        try {
            JpaUtil.ouvrirTransaction();
            MediumDAO.update(medium);
            EmployeDAO.update(employe);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    public static void initialisation() {
        Medium gwanael = new MediumVoyant("Gwanael", "Specialise des grandes conversations au dela de toutes les frontieres", "Boule de cristal");
        Service.inscrireMedium(gwanael);
        Medium jDalmarre = new MediumVoyant("J. Dalmarre", "Votre avenir est devant vous: regardons le ensemble!", "Marc de cafe");
        Service.inscrireMedium(jDalmarre);
        Medium irma = new MediumTarologue("Mme Irma", "Comprenez votre entourage grace a mes cartes! Resultats rapides.", "Tarot de Marseille");
        Service.inscrireMedium(irma);
        Medium lisa = new MediumTarologue("Mme Lsa Maria NGUYINIA", "Mes cartes specialises pour la region de Bretagne reponderont a toutes vos questions personnelles.", "Tarot de Broceliande");
        Service.inscrireMedium(lisa);
        Medium sara = new MediumAstrologue("Mme Sara", "Basee a Champagny-sur-Marne, Mme Sara vous revelera votre avenir pour eclairer votre passe.", "Ecole Normale Supérieure d'Astrologie (ENS-Astro)", "2006");
        Service.inscrireMedium(sara);
        Medium mounia = new MediumAstrologue("Mme Mounia Mounia", "Avenir, avenir, que nous reserves-tu? N'attendez-plus, demandez a me consulter!", "Institut des Nouveaux Savoirs Astrologiques", "2010");
        Service.inscrireMedium(mounia);

        Employe olivier = new Employe("WOSTPHOL", "Olivier", "owostphol@gmail.com", "0860680312");
        Service.inscrireEmploye(olivier);
        olivier = Service.connecterEmploye(olivier.getMail());
        Employe vincent = new Employe("GIYRAUD", "Vincent", "vincent.giyraud@yahoo.com", "0518940604");
        Service.inscrireEmploye(vincent);
        vincent = Service.connecterEmploye(vincent.getMail());
        Employe benjamin = new Employe("KLOEN", "Benjamin", "benjamin.kloen@yahoo.com", "0220989602");
        Service.inscrireEmploye(benjamin);
        benjamin = Service.connecterEmploye(benjamin.getMail());
        Employe adrien = new Employe("UNLU", "Adrien", "adrien.unlu@laposte.net", "0367699654");
        Service.inscrireEmploye(adrien);
        adrien = Service.connecterEmploye(adrien.getMail());
        Employe romain = new Employe("MIE", "Romain", "romain.mie@free.fr", "0307363387");
        Service.inscrireEmploye(romain);
        romain = Service.connecterEmploye(romain.getMail());

        Service.possibiliteDincarner(irma, olivier);
        Service.possibiliteDincarner(irma, vincent);
        Service.possibiliteDincarner(irma, benjamin);
        Service.possibiliteDincarner(lisa, olivier);
        Service.possibiliteDincarner(lisa, vincent);
        Service.possibiliteDincarner(lisa, benjamin);
        Service.possibiliteDincarner(mounia, olivier);
        Service.possibiliteDincarner(mounia, vincent);
        Service.possibiliteDincarner(mounia, romain);
        Service.possibiliteDincarner(sara, benjamin);
        Service.possibiliteDincarner(sara, adrien);
        Service.possibiliteDincarner(sara, romain);
        Service.possibiliteDincarner(jDalmarre, adrien);
        Service.possibiliteDincarner(jDalmarre, romain);
        Service.possibiliteDincarner(jDalmarre, olivier);
        Service.possibiliteDincarner(gwanael, adrien);
        Service.possibiliteDincarner(gwanael, romain);
        Service.possibiliteDincarner(gwanael, vincent);
    }
    
    //Sert uniquement pour la démo
    public static void afficherMedium(Medium m){
        System.out.println("Medium : "+ m.getNom());
    }
    //Sert uniquement pour la démo
    public static void simulation() {

        SimpleDateFormat dateDeNaissance = new SimpleDateFormat("yyyy-MM-dd"); // Format pour les dates de naissances
        try {
            //Inscriptions et connexions  des clients
            Client matias = new Client("M.", "BORROTI MATIAS DANTAS", "Raphaël", dateDeNaissance.parse("1976-07-10"), "8 Rue Arago, Villeurbanne", "0328178508", "rborrotimatiasdantas4171@free.fr");
            Service.inscrireClient(matias);
            matias = Service.connecterClient(matias.getCoordonnees().getAdresseElectronique());
            Client david = new Client("M.", "ABDIULLINA", "David Alexander", dateDeNaissance.parse("1975-01-07"), "8 Rue Wilhelmine, Villeurbanne", "0590232772", "david-alexander.abdiullina@laposte.net");
            Service.inscrireClient(david);
            david = Service.connecterClient(david.getCoordonnees().getAdresseElectronique());
            Client alice = new Client("Mme", "VOYRET", "Alice", dateDeNaissance.parse("1988-08-13"), "1 Rue d'Alsace, Villeurbanne", "0486856520", "alicehotmail.com");
            Service.inscrireClient(alice);
            alice = Service.connecterClient(alice.getCoordonnees().getAdresseElectronique());
            //Echec car email existant dans la BD
            Client moez = new Client("M.", "WOAGNER", "Moez", dateDeNaissance.parse("1984-08-16"), "6 Rue Camille Koechlin, Villeurbanne", "0832205629", "alicehotmail.com");
            Service.inscrireClient(moez);
            Client julien = new Client("M.", "RINERD", "Julien", dateDeNaissance.parse("1989-05-16"), "4 Rue de la Jeunesse, Villeurbanne", "0727252485", "jrinerd5241@yahoo.com");
            Service.inscrireClient(julien);
            julien = Service.connecterClient(julien.getCoordonnees().getAdresseElectronique());

            //Récupération des médiums
            Medium irma = Service.identifierMedium("Mme Irma");
            Medium gwanael = Service.identifierMedium("Gwanael");
            Medium mounia = Service.identifierMedium("Mme Mounia Mounia");
            Medium sara = Service.identifierMedium("Mme Sara");
            Medium lisa = Service.identifierMedium("Mme Lisa Maria NGUYINIA");
            Medium jDalmarre = Service.identifierMedium("J. Dalmarre");

            //Récupération des employés
            Employe olivier = Service.connecterEmploye("owostphol@gmail.com");
            Employe vincent = Service.connecterEmploye("vincent.giyraud@yahoo.com");
            Employe benjamin = Service.connecterEmploye("benjamin.kloen@yahoo.com");
            Employe adrien = Service.connecterEmploye("adrien.unlu@laposte.net");
            Employe romain = Service.connecterEmploye("romain.mie@free.fr");
            
            //Listing des mediums 
            System.out.println("Les tarologues sont disponibles sont : ");
            List<Medium> tarologues=Service.determinerMediums(false, true, false);
            for(int i=0;i<tarologues.size();i++){
                Service.afficherMedium(tarologues.get(i));
            }
            System.out.println("\nLes astrologues sont disponibles sont : ");
            List<Medium> astrologues=Service.determinerMediums(true, false, false);
            for(int i=0;i<astrologues.size();i++){
                Service.afficherMedium(astrologues.get(i));
            }
            System.out.println("\nLes voyants sont disponibles sont : ");
            List<Medium> voyants=Service.determinerMediums(false, false, true);
            for(int i=0;i<voyants.size();i++){
                Service.afficherMedium(voyants.get(i));
            }
            System.out.println("\nLes voyants et tarologues sont disponibles sont : ");
            List<Medium> mix=Service.determinerMediums(false, true, true);
            for(int i=0;i<mix.size();i++){
                Service.afficherMedium(mix.get(i));
            }
            System.out.println("\nLes mediums sont disponibles sont : ");
            List<Medium> mediums=Service.determinerMediums(true, true, true);
            for(int i=0;i<mediums.size();i++){
                Service.afficherMedium(mediums.get(i));
            }
            System.out.println("");
            
            //Réalisation des voyances
            Employe pretPourMatias = Service.demanderVoyance(matias, irma);
            if (pretPourMatias != null) {
                Voyance voyance1 = Service.commencerVoyance(matias, pretPourMatias, irma);
                List<String> prediction = Service.realiserVoyance(matias, 3, 4, 4);
                System.out.println("\nPredictions pour matteo par " + pretPourMatias.getPrenom());
                System.out.println(prediction.get(0));
                System.out.println(prediction.get(1));
                System.out.println(prediction.get(2) + "\n");
                Service.finirVoyance(voyance1, 3, 4, 4);
                Service.cloturerVoyance(voyance1, "reussie1");
            } else {
                System.out.println("Aucun medium disponible");
            }
            
            pretPourMatias = Service.demanderVoyance(matias, mounia);
            if (pretPourMatias != null) {
                Voyance voyance1 = Service.commencerVoyance(matias, pretPourMatias, mounia);
                List<String> prediction = Service.realiserVoyance(matias, 1, 2, 2);
                System.out.println("\nPredictions pour matias par " + pretPourMatias.getPrenom());
                System.out.println(prediction.get(0));
                System.out.println(prediction.get(1));
                System.out.println(prediction.get(2) + "\n");
                Service.finirVoyance(voyance1, 3, 4, 4);
                Service.cloturerVoyance(voyance1, "reussie2");
            } else {
                System.out.println("Aucun medium disponible");
            }
            
            pretPourMatias = Service.demanderVoyance(matias, jDalmarre);
            if (pretPourMatias != null) {
                Voyance voyance1 = Service.commencerVoyance(matias, pretPourMatias,jDalmarre);
                List<String> prediction = Service.realiserVoyance(matias, 2, 4, 1);
                System.out.println("\nPredictions pour matias par " + pretPourMatias.getPrenom());
                System.out.println(prediction.get(0));
                System.out.println(prediction.get(1));
                System.out.println(prediction.get(2) + "\n");
                Service.finirVoyance(voyance1, 3, 4, 4);
                Service.cloturerVoyance(voyance1, "reussie3");
            } else {
                System.out.println("Aucun medium disponible");
            }
            
            Employe pretPourDavid = Service.demanderVoyance(david, sara);
            if (pretPourDavid != null) {
                Voyance voyance1 = Service.commencerVoyance(david, pretPourDavid, sara);
                List<String> prediction = Service.realiserVoyance(david, 3, 4, 4);
                System.out.println("\nPredictions pour matteo par " + pretPourDavid.getPrenom());
                System.out.println(prediction.get(0));
                System.out.println(prediction.get(1));
                System.out.println(prediction.get(2) + "\n");
                Service.finirVoyance(voyance1, 3, 4, 4);
                Service.cloturerVoyance(voyance1, "reussie4");
            } else {
                System.out.println("Aucun medium disponible");
            }
            
            //Historique
            System.out.println("Historique de matias");
            List<Voyance> historique=Service.consulterHisto(matias);
            for(int i=0;i<historique.size();i++){
                Service.afficherVoyance(historique.get(i));
            }
            System.out.println("");
            System.out.println("Historique de david");
            historique=Service.consulterHisto(david);
            for(int i=0;i<historique.size();i++){
                Service.afficherVoyance(historique.get(i));
            }
            System.out.println("");
            
            
            //Calcul des stats
            System.out.println(Service.calculerStatistiques());

        } catch (ParseException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Sert uniquement pour la démo
    public static void testerConcurrence(String s) {

        SimpleDateFormat dateDeNaissance = new SimpleDateFormat("yyyy-MM-dd"); // Format pour les dates de naissances

        if (s.equals("a")) {
            Medium gwanael = new MediumVoyant("Gwanael", "Specialise des grandes conversations au dela de toutes les frontieres", "Boule de cristal");
            Service.inscrireMedium(gwanael);
            Medium jDalmarre = new MediumVoyant("J. Dalmarre", "Votre avenir est devant vous: regardons le ensemble!", "Marc de cafe");
            Service.inscrireMedium(jDalmarre);
            Medium irma = new MediumTarologue("Mme Irma", "Comprenez votre entourage grace a mes cartes! Resultats rapides.", "Tarot de Marseille");
            Service.inscrireMedium(irma);
            Medium lisa = new MediumTarologue("Mme Lsa Maria NGUYINIA", "Mes cartes specialises pour la region de Bretagne reponderont a toutes vos questions personnelles.", "Tarot de Broceliande");
            Service.inscrireMedium(lisa);
            Medium sara = new MediumAstrologue("Mme Sara", "Basee a Champagny-sur-Marne, Mme Sara vous revelera votre avenir pour eclairer votre passe.", "Ecole Normale Supérieure d'Astrologie (ENS-Astro)", "2006");
            Service.inscrireMedium(sara);
            Medium mounia = new MediumAstrologue("Mme Mounia Mounia", "Avenir, avenir, que nous reserves-tu? N'attendez-plus, demandez a me consulter!", "Institut des Nouveaux Savoirs Astrologiques", "2010");
            Service.inscrireMedium(mounia);

            Employe olivier = new Employe("WOSTPHOL", "Olivier", "owostphol@gmail.com", "0860680312");
            Service.inscrireEmploye(olivier);
            olivier = Service.connecterEmploye(olivier.getMail());
            Employe vincent = new Employe("GIYRAUD", "Vincent", "vincent.giyraud@yahoo.com", "0518940604");
            Service.inscrireEmploye(vincent);
            vincent = Service.connecterEmploye(vincent.getMail());
            Employe benjamin = new Employe("KLOEN", "Benjamin", "benjamin.kloen@yahoo.com", "0220989602");
            Service.inscrireEmploye(benjamin);
            benjamin = Service.connecterEmploye(benjamin.getMail());
            Employe adrien = new Employe("UNLU", "Adrien", "adrien.unlu@laposte.net", "0367699654");
            Service.inscrireEmploye(adrien);
            adrien = Service.connecterEmploye(adrien.getMail());
            Employe romain = new Employe("MIE", "Romain", "romain.mie@free.fr", "0307363387");
            Service.inscrireEmploye(romain);
            romain = Service.connecterEmploye(romain.getMail());

            Service.possibiliteDincarner(irma, olivier);
            Service.possibiliteDincarner(irma, vincent);
            Service.possibiliteDincarner(irma, benjamin);
            Service.possibiliteDincarner(lisa, olivier);
            Service.possibiliteDincarner(lisa, vincent);
            Service.possibiliteDincarner(lisa, benjamin);
            Service.possibiliteDincarner(mounia, olivier);
            Service.possibiliteDincarner(mounia, vincent);
            Service.possibiliteDincarner(mounia, romain);
            Service.possibiliteDincarner(sara, benjamin);
            Service.possibiliteDincarner(sara, adrien);
            Service.possibiliteDincarner(sara, romain);
            Service.possibiliteDincarner(jDalmarre, adrien);
            Service.possibiliteDincarner(jDalmarre, romain);
            Service.possibiliteDincarner(jDalmarre, olivier);
            Service.possibiliteDincarner(gwanael, adrien);
            Service.possibiliteDincarner(gwanael, romain);
            Service.possibiliteDincarner(gwanael, vincent);

            Client matteo;
            try {
                matteo = new Client("M.", "HONRY", "Matteo", dateDeNaissance.parse("1996-02-17"), "9 Impasse Guillet, Villeurbanne", "0482381862", "matteo.honry@yahoo.com");
                Service.inscrireClient(matteo);

                Employe pret1 = Service.demanderVoyance(matteo, irma);
                if (pret1 != null) {
                    Voyance voyance1 = Service.commencerVoyance(matteo, pret1, irma);
                    List<String> prediction = Service.realiserVoyance(matteo, 3, 4, 4);
                    System.out.println("\nPredictions pour matteo par " + pret1.getPrenom());
                    System.out.println(prediction.get(0));
                    System.out.println(prediction.get(1));
                    System.out.println(prediction.get(2) + "\n");
                    Service.finirVoyance(voyance1, 3, 4, 4);
                    Service.cloturerVoyance(voyance1, "reussie1");
                } else {
                    System.out.println("Aucun medium disponible1");
                }
            } catch (ParseException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (s.equals("b")) {
            try {
                Medium irma = Service.identifierMedium("Mme Irma");
                Client kevin = new Client("M.", "CECCANI", "Kevin", dateDeNaissance.parse("1982-02-16"), "20 Rue Decomberousse, Villeurbanne", "0664426037", "kevin.ceccani@hotmail.com");
                Service.inscrireClient(kevin);
                Employe pret1 = Service.demanderVoyance(kevin, irma);
                if (pret1 != null) {
                    Voyance voyance1 = Service.commencerVoyance(kevin, pret1, irma);
                    List<String> prediction = Service.realiserVoyance(kevin, 3, 4, 4);
                    System.out.println("\nPredictions pour kevin par " + pret1.getPrenom());
                    System.out.println(prediction.get(0));
                    System.out.println(prediction.get(1));
                    System.out.println(prediction.get(2) + "\n");
                    Service.finirVoyance(voyance1, 3, 4, 4);
                    Service.cloturerVoyance(voyance1, "reussie2");
                } else {
                    System.out.println("Aucun medium disponible1");
                }
            } catch (ParseException ex) {
                Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
