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

}
