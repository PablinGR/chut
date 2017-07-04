/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facade;

import MD.Propietarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Propietarios_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import MD.Canchas;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class PropietariosFacade extends AbstractFacade<Propietarios> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
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

    @Override
    public Propietarios findWithParents(Propietarios entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Propietarios> cq = cb.createQuery(Propietarios.class);
        Root<Propietarios> propietarios = cq.from(Propietarios.class);
        propietarios.fetch(Propietarios_.canchasCollection, JoinType.LEFT);
        cq.select(propietarios).where(cb.equal(propietarios, entity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return entity;
        }
    }
    
}
