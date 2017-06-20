package MD;

import MD.Equipos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-20T13:18:18")
@StaticMetamodel(Jugadores.class)
public class Jugadores_ { 

    public static volatile SingularAttribute<Jugadores, Long> celularjugador;
    public static volatile SingularAttribute<Jugadores, String> mailjugador;
    public static volatile CollectionAttribute<Jugadores, Equipos> equiposCollection;
    public static volatile SingularAttribute<Jugadores, String> cedulajugador;
    public static volatile SingularAttribute<Jugadores, String> nombrejugador;

}