/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chut.facade;

import com.chut.entidades.Canchas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.chut.entidades.Canchas_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import com.chut.entidades.Propietarios;
import com.chut.entidades.Partidos;
import java.util.Collection;

/**
 *
 * @author pablingr
 */
@Stateless
public class CanchasFacade extends AbstractFacade<Canchas> {

    @PersistenceContext(unitName = "prueba2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CanchasFacade() {
        super(Canchas.class);
    }

    public boolean isPropietariosCollectionEmpty(Canchas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Canchas> canchas = cq.from(Canchas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(canchas, entity), cb.isNotEmpty(canchas.get(Canchas_.propietariosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Propietarios> findPropietariosCollection(Canchas entity) {
        return this.getMergedEntity(entity).getPropietariosCollection();
    }

    public boolean isPartidosCollectionEmpty(Canchas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Canchas> canchas = cq.from(Canchas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(canchas, entity), cb.isNotEmpty(canchas.get(Canchas_.partidosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Partidos> findPartidosCollection(Canchas entity) {
        return this.getMergedEntity(entity).getPartidosCollection();
    }

    @Override
    public Canchas findWithParents(Canchas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Canchas> cq = cb.createQuery(Canchas.class);
        Root<Canchas> canchas = cq.from(Canchas.class);
        canchas.fetch(Canchas_.propietariosCollection, JoinType.LEFT);
        cq.select(canchas).where(cb.equal(canchas, entity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return entity;
        }
    }
    
}
