package MD;

import MD.Jugadores;
import MD.Partidos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-26T11:18:57")
@StaticMetamodel(Equipos.class)
public class Equipos_ { 

    public static volatile CollectionAttribute<Equipos, Partidos> partidosCollection;
    public static volatile SingularAttribute<Equipos, Jugadores> cedulajugador;
    public static volatile SingularAttribute<Equipos, Integer> idequipo;
    public static volatile SingularAttribute<Equipos, String> descripcionequipo;

}