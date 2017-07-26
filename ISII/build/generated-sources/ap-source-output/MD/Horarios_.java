package MD;

import MD.Reservas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-26T11:18:57")
@StaticMetamodel(Horarios.class)
public class Horarios_ { 

    public static volatile SingularAttribute<Horarios, Reservas> idreserva;
    public static volatile SingularAttribute<Horarios, Integer> idhorario;
    public static volatile SingularAttribute<Horarios, String> descripcionhorario;
    public static volatile SingularAttribute<Horarios, Date> finhorario;
    public static volatile SingularAttribute<Horarios, Date> iniciohorario;

}