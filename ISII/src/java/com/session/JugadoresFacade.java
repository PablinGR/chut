/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Jugadores;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Jugadores_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Equipos;
import MD.Reservas;
import java.util.Collection;

/**
 *
 * @author Diego
 */
@Stateless
public class JugadoresFacade extends AbstractFacade<Jugadores> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JugadoresFacade() {
        super(Jugadores.class);
    }

    public boolean isEquiposCollectionEmpty(Jugadores entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Jugadores> jugadores = cq.from(Jugadores.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(jugadores, entity), cb.isNotEmpty(jugadores.get(Jugadores_.equiposCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Equipos> findEquiposCollection(Jugadores entity) {
        return this.getMergedEntity(entity).getEquiposCollection();
    }

    public boolean isReservasCollectionEmpty(Jugadores entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Jugadores> jugadores = cq.from(Jugadores.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(jugadores, entity), cb.isNotEmpty(jugadores.get(Jugadores_.reservasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Reservas> findReservasCollection(Jugadores entity) {
        return this.getMergedEntity(entity).getReservasCollection();
    }
    
}
