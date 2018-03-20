package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Medium;
import model.Voyance;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T11:59:48")
@StaticMetamodel(Employe.class)
public class Employe_ { 

    public static volatile ListAttribute<Employe, Medium> incarne;
    public static volatile SingularAttribute<Employe, String> mail;
    public static volatile SingularAttribute<Employe, String> numero;
    public static volatile ListAttribute<Employe, Voyance> histoEmploye;
    public static volatile SingularAttribute<Employe, String> nom;
    public static volatile SingularAttribute<Employe, String> prenom;
    public static volatile SingularAttribute<Employe, Integer> version;
    public static volatile SingularAttribute<Employe, Long> idEmploye;
    public static volatile SingularAttribute<Employe, Boolean> disponible;

}