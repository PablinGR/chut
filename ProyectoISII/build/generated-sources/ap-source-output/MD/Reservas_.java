package MD;

import MD.Campeonatos;
import MD.Horarios;
import MD.Partidos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-20T13:18:18")
@StaticMetamodel(Reservas.class)
public class Reservas_ { 

    public static volatile CollectionAttribute<Reservas, Partidos> partidosCollection;
    public static volatile SingularAttribute<Reservas, String> capitanreserva;
    public static volatile SingularAttribute<Reservas, Partidos> idpartido;
    public static volatile SingularAttribute<Reservas, Integer> idreserva;
    public static volatile SingularAttribute<Reservas, Horarios> idhorario;
    public static volatile SingularAttribute<Reservas, Campeonatos> idcampeonato;

}