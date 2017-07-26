/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Reservas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Reservas_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Campeonatos;
import MD.Jugadores;
import MD.Partidos;
import MD.Horarios;
import java.util.Collection;

/**
 *
 * @author Diego
 */
@Stateless
public class ReservasFacade extends AbstractFacade<Reservas> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservasFacade() {
        super(Reservas.class);
    }

    public boolean isCampeonatosCollectionEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotEmpty(reservas.get(Reservas_.campeonatosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Campeonatos> findCampeonatosCollection(Reservas entity) {
        return this.getMergedEntity(entity).getCampeonatosCollection();
    }

    public boolean isCedulajugadorEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotNull(reservas.get(Reservas_.cedulajugador)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Jugadores findCedulajugador(Reservas entity) {
        return this.getMergedEntity(entity).getCedulajugador();
    }

    public boolean isIdpartidoEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotNull(reservas.get(Reservas_.idpartido)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Partidos findIdpartido(Reservas entity) {
        return this.getMergedEntity(entity).getIdpartido();
    }

    public boolean isHorariosCollectionEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotEmpty(reservas.get(Reservas_.horariosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Horarios> findHorariosCollection(Reservas entity) {
        return this.getMergedEntity(entity).getHorariosCollection();
    }
    
}
