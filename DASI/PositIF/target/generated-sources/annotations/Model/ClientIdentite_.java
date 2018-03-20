package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T11:59:48")
@StaticMetamodel(ClientIdentite.class)
public class ClientIdentite_ { 

    public static volatile SingularAttribute<ClientIdentite, Date> dateNaissance;
    public static volatile SingularAttribute<ClientIdentite, String> nom;
    public static volatile SingularAttribute<ClientIdentite, String> prenom;
    public static volatile SingularAttribute<ClientIdentite, String> civilite;

}