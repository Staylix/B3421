package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Employe;
import model.Voyance;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-14T17:00:52")
@StaticMetamodel(Medium.class)
public class Medium_ { 

    public static volatile SetAttribute<Medium, Employe> incarnePar;
    public static volatile ListAttribute<Medium, Voyance> histoMedium;
    public static volatile SingularAttribute<Medium, Long> idMedium;

}