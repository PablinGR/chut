package MD;

import MD.Canchas;
import MD.Equipos;
import MD.Reservas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-05T13:07:57")
@StaticMetamodel(Partidos.class)
public class Partidos_ { 

    public static volatile SingularAttribute<Partidos, Date> fechapartido;
    public static volatile SingularAttribute<Partidos, Canchas> idcancha;
    public static volatile SingularAttribute<Partidos, Integer> idpartido;
    public static volatile CollectionAttribute<Partidos, Canchas> canchasCollection;
    public static volatile SingularAttribute<Partidos, Reservas> idreserva;
    public static volatile CollectionAttribute<Partidos, Reservas> reservasCollection;
    public static volatile SingularAttribute<Partidos, Equipos> idequipo;

}