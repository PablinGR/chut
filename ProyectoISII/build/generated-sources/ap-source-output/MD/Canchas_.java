package MD;

import MD.Partidos;
import MD.Propietarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-20T13:18:18")
@StaticMetamodel(Canchas.class)
public class Canchas_ { 

    public static volatile SingularAttribute<Canchas, String> sectorcancha;
    public static volatile CollectionAttribute<Canchas, Partidos> partidosCollection;
    public static volatile CollectionAttribute<Canchas, Propietarios> propietariosCollection;
    public static volatile SingularAttribute<Canchas, Integer> idcancha;
    public static volatile SingularAttribute<Canchas, Partidos> idpartido;
    public static volatile SingularAttribute<Canchas, String> descripcioncancha;

}