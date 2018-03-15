/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ClientDAO;
import dao.EmployeDAO;
import dao.VoyanceDAO;
import dao.JpaUtil;
import dao.MediumDAO;
import ihm.Main;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 *
 * @author ggentil
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
        AstroTest astro = new AstroTest("ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx");
        try {
            List<String> s = astro.getProfil(client.getIdentite().getPrenom(), client.getIdentite().getDateNaissance());
            client.setProfileAstrologique(new ClientProfilAstrologique(s.get(0), s.get(1), s.get(2), s.get(3)));
        } catch (IOException e) {
            System.err.println("Impossible to create the astrologic profile");
        }
        JpaUtil.creerEntityManager();
        try {
            JpaUtil.ouvrirTransaction();
            ClientDAO.persister(client);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            Service.envoyerMailSucces(client);
            return true;//On suppose que les mails sont toujours différents
        } catch (Exception e) {
            System.err.println("Transaction failed");
            JpaUtil.annulerTransaction();
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

    public List<Medium> determinerMediums(boolean astro, boolean taro, boolean voy) {
        List<Medium> tarologues = MediumDAO.getTarologues(taro);
        List<Medium> astrologues = MediumDAO.getAstrologues(astro);
        List<Medium> voyants = MediumDAO.getVoyants(voy);
        List<Medium> resultat = new ArrayList<Medium>();
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
        System.out.println("Expediteur : contact@positif.if\nPour : " + mail + "\nSujet : Bienvenue chez Posit'IF\n");
        System.out.println("Corps :\nBonjour " + nom + ",\nNous vous confirmons votre inscription au service Posit'IF. Votre numero de client est : " + id + ".");
    }

    public static void envoyerMailEchec(Client client) {
        String mail = client.getCoordonnees().getAdresseElectronique();
        String nom = client.getIdentite().getPrenom();
        Long id = client.getIdClient();
        System.out.println("Expediteur : contact@positif.if\nPour : " + mail + "\nSujet : Bienvenue chez Posit'IF\n");
        System.out.println("Corps :\nBonjour " + nom + ",\nVotre inscription au service Posit'IF a malencontreusement echoue... Merci de recommencer ulterieurement.");
    }

    public static void envoyerNotif(Employe employe, Client client, Medium medium) {
        String numero = employe.getNumero();
        Long idClient = client.getIdClient();
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

    public static void initialisation() {

        SimpleDateFormat dateDeNaissance = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateDeNaissance.parse("1997-12-07");
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Client client = new Client("Mr", "Gentil", "Gregoire", d, "Part Dieux", "0667170327", "gregoire@gmail.com");
        Service.inscrireClient(client);
        client = new Client("Mme", "El Bayed", "Safia", d, "Part Dieux", "0667170327", "sa.fsoufa22@gmail.com");
        Service.inscrireClient(client);
        Medium medium = new MediumAstrologue("Mme Irma", "Specialiste des grandes conversations", "INSA DE LYON", "60ème");
        Service.inscrireMedium(medium);
        medium = new MediumTarologue("Mme Sara", "Specialiste des grandes conversations", "Tarot de Marseille");
        Service.inscrireMedium(medium);
        medium = new MediumVoyant("Gwanael", "Comprenez votre entourage", "Boule de cristal");
        Service.inscrireMedium(medium);
        Employe employe = new Employe("glou@gmail.com", "Michel", "Jean", "0667154875");
        Service.inscrireEmploye(employe);
        employe = new Employe("irine@gmail.com", "Irina", "Shayk", "0667154875");
        Service.inscrireEmploye(employe);
        Voyance voyance = Service.commencerVoyance(client, employe, medium);
        Service.finirVoyance(voyance, 3, 4, 4);
        Service.cloturerVoyance(voyance, "reussie");
        System.out.print(Service.calculerStatistiques());
    }

    public static List<Voyance> consulterHisto(Client c) {
        return c.getHistoClient();
    }

    public static boolean demanderVoyance(Client client, Medium medium) {
        Date d = new Date();
        if (d.getHours() <= Employe.debut || d.getHours() >= Employe.fin) {
            return false;
        } else {
            Employe employe = null;
            List<Employe> possibilites = medium.getIncarnePar();
            if (!possibilites.isEmpty()) {
                employe = possibilites.get(0);
                for (Employe e : possibilites) {
                    if (e.isDisponible() && e.getHistoEmploye().size() < employe.getHistoEmploye().size()) {
                        employe = e;
                    }
                }
                if (!employe.isDisponible()) {
                    employe = null;
                }
            }
            if (employe != null) {
                Service.envoyerNotif(employe, client, medium);
                return true;
            } else {
                return false;
            }
        }
    }
}
