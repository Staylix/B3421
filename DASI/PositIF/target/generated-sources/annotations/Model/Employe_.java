package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Medium;
import model.Voyance;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-14T17:00:52")
@StaticMetamodel(Employe.class)
public class Employe_ { 

    public static volatile SetAttribute<Employe, Medium> incarne;
    public static volatile ListAttribute<Employe, Voyance> histoEmploye;
    public static volatile SingularAttribute<Employe, String> nom;
    public static volatile SingularAttribute<Employe, String> prenom;
    public static volatile SingularAttribute<Employe, Long> idEmploye;

}