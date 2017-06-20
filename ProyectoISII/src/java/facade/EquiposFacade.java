/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Equipos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Equipos_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import MD.Jugadores;
import MD.Partidos;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class EquiposFacade extends AbstractFacade<Equipos> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquiposFacade() {
        super(Equipos.class);
    }

    public boolean isJugadoresCollectionEmpty(Equipos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Equipos> equipos = cq.from(Equipos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(equipos, entity), cb.isNotEmpty(equipos.get(Equipos_.jugadoresCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Jugadores> findJugadoresCollection(Equipos entity) {
        return this.getMergedEntity(entity).getJugadoresCollection();
    }

    public boolean isPartidosCollectionEmpty(Equipos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Equipos> equipos = cq.from(Equipos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(equipos, entity), cb.isNotEmpty(equipos.get(Equipos_.partidosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Partidos> findPartidosCollection(Equipos entity) {
        return this.getMergedEntity(entity).getPartidosCollection();
    }

    @Override
    public Equipos findWithParents(Equipos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Equipos> cq = cb.createQuery(Equipos.class);
        Root<Equipos> equipos = cq.from(Equipos.class);
        equipos.fetch(Equipos_.jugadoresCollection, JoinType.LEFT);
        cq.select(equipos).where(cb.equal(equipos, entity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return entity;
        }
    }
    
}
