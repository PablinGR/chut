package MD;

import MD.Canchas;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-05T09:03:57")
@StaticMetamodel(Propietarios.class)
public class Propietarios_ { 

    public static volatile SingularAttribute<Propietarios, String> cedulapropietarios;
    public static volatile CollectionAttribute<Propietarios, Canchas> canchasCollection;
    public static volatile SingularAttribute<Propietarios, Long> celularpropietarios;
    public static volatile SingularAttribute<Propietarios, String> mailpropietarios;
    public static volatile SingularAttribute<Propietarios, String> nombrepropietarios;

}