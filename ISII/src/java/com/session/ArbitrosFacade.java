/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Arbitros;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Arbitros_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Campeonatos;

/**
 *
 * @author Diego
 */
@Stateless
public class ArbitrosFacade extends AbstractFacade<Arbitros> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArbitrosFacade() {
        super(Arbitros.class);
    }

    public boolean isIdcampeonatoEmpty(Arbitros entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Arbitros> arbitros = cq.from(Arbitros.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(arbitros, entity), cb.isNotNull(arbitros.get(Arbitros_.idcampeonato)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Campeonatos findIdcampeonato(Arbitros entity) {
        return this.getMergedEntity(entity).getIdcampeonato();
    }
    
}
