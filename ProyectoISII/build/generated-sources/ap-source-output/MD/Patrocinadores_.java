package MD;

import MD.Campeonatos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-20T13:18:18")
@StaticMetamodel(Patrocinadores.class)
public class Patrocinadores_ { 

    public static volatile SingularAttribute<Patrocinadores, String> descripcionpatrocinador;
    public static volatile SingularAttribute<Patrocinadores, String> rucpatrocinador;
    public static volatile CollectionAttribute<Patrocinadores, Campeonatos> campeonatosCollection;

}