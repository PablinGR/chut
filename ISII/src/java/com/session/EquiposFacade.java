/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Equipos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Equipos_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Partidos;
import MD.Jugadores;
import java.util.Collection;

/**
 *
 * @author Diego
 */
@Stateless
public class EquiposFacade extends AbstractFacade<Equipos> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquiposFacade() {
        super(Equipos.class);
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

    public boolean isCedulajugadorEmpty(Equipos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Equipos> equipos = cq.from(Equipos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(equipos, entity), cb.isNotNull(equipos.get(Equipos_.cedulajugador)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Jugadores findCedulajugador(Equipos entity) {
        return this.getMergedEntity(entity).getCedulajugador();
    }
    
}
