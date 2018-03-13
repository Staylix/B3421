package Model;

import Model.ClientCoordonnees;
import Model.ClientIdentite;
import Model.ClientProfilAstrologique;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-07T17:59:15")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, ClientIdentite> identite;
    public static volatile SingularAttribute<Client, Long> idClient;
    public static volatile SingularAttribute<Client, ClientCoordonnees> coordonnees;
    public static volatile SingularAttribute<Client, ClientProfilAstrologique> profileAstrologique;

}