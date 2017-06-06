/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chut.facade;

import com.chut.entidades.Jugadores;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.chut.entidades.Jugadores_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.chut.entidades.Equipos;

/**
 *
 * @author pablingr
 */
@Stateless
public class JugadoresFacade extends AbstractFacade<Jugadores> {

    @PersistenceContext(unitName = "prueba2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JugadoresFacade() {
        super(Jugadores.class);
    }

    public boolean isIdequipoEmpty(Jugadores entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Jugadores> jugadores = cq.from(Jugadores.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(jugadores, entity), cb.isNotNull(jugadores.get(Jugadores_.idequipo)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Equipos findIdequipo(Jugadores entity) {
        return this.getMergedEntity(entity).getIdequipo();
    }
    
}
