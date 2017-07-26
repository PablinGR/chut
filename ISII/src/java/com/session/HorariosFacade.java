/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Horarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Horarios_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Reservas;

/**
 *
 * @author Diego
 */
@Stateless
public class HorariosFacade extends AbstractFacade<Horarios> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariosFacade() {
        super(Horarios.class);
    }

    public boolean isIdreservaEmpty(Horarios entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Horarios> horarios = cq.from(Horarios.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(horarios, entity), cb.isNotNull(horarios.get(Horarios_.idreserva)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Reservas findIdreserva(Horarios entity) {
        return this.getMergedEntity(entity).getIdreserva();
    }
    
}
