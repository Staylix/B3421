package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.ClientCoordonnees;
import model.ClientIdentite;
import model.ClientProfilAstrologique;
import model.Voyance;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-14T17:00:52")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile ListAttribute<Client, Voyance> histoClient;
    public static volatile SingularAttribute<Client, ClientIdentite> identite;
    public static volatile SingularAttribute<Client, Long> idClient;
    public static volatile SingularAttribute<Client, ClientCoordonnees> coordonnees;
    public static volatile SingularAttribute<Client, ClientProfilAstrologique> profileAstrologique;

}