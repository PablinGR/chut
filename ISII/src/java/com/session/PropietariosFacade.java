/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import MD.Propietarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Propietarios_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Canchas;
import java.util.Collection;

/**
 *
 * @author Diego
 */
@Stateless
public class PropietariosFacade extends AbstractFacade<Propietarios> {

    @PersistenceContext(unitName = "ISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropietariosFacade() {
        super(Propietarios.class);
    }

    public boolean isCanchasCollectionEmpty(Propietarios entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Propietarios> propietarios = cq.from(Propietarios.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(propietarios, entity), cb.isNotEmpty(propietarios.get(Propietarios_.canchasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Canchas> findCanchasCollection(Propietarios entity) {
        return this.getMergedEntity(entity).getCanchasCollection();
    }
    
}
