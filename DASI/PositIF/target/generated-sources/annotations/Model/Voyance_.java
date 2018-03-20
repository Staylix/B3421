package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Client;
import model.Employe;
import model.Medium;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T11:59:48")
@StaticMetamodel(Voyance.class)
public class Voyance_ { 

    public static volatile SingularAttribute<Voyance, Integer> sante;
    public static volatile SingularAttribute<Voyance, Employe> employe;
    public static volatile SingularAttribute<Voyance, Integer> travail;
    public static volatile SingularAttribute<Voyance, Date> heureDebut;
    public static volatile SingularAttribute<Voyance, Client> client;
    public static volatile SingularAttribute<Voyance, Integer> amour;
    public static volatile SingularAttribute<Voyance, Long> idVoyance;
    public static volatile SingularAttribute<Voyance, Medium> medium;
    public static volatile SingularAttribute<Voyance, Date> heureFin;
    public static volatile SingularAttribute<Voyance, String> commentaire;

}