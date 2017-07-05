package MD;

import MD.Arbitros;
import MD.Patrocinadores;
import MD.Reservas;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-05T09:03:57")
@StaticMetamodel(Campeonatos.class)
public class Campeonatos_ { 

    public static volatile SingularAttribute<Campeonatos, String> mailcampeonato;
    public static volatile SingularAttribute<Campeonatos, Float> premiocampeonato;
    public static volatile CollectionAttribute<Campeonatos, Reservas> reservasCollection;
    public static volatile SingularAttribute<Campeonatos, Long> celularcampeonato;
    public static volatile CollectionAttribute<Campeonatos, Arbitros> arbitrosCollection;
    public static volatile SingularAttribute<Campeonatos, Patrocinadores> rucpatrocinador;
    public static volatile SingularAttribute<Campeonatos, String> descripcioncampeonato;
    public static volatile SingularAttribute<Campeonatos, Integer> idcampeonato;

}