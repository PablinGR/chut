/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Patrocinadores;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Patrocinadores_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import MD.Campeonatos;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class PatrocinadoresFacade extends AbstractFacade<Patrocinadores> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PatrocinadoresFacade() {
        super(Patrocinadores.class);
    }

    public boolean isCampeonatosCollectionEmpty(Patrocinadores entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Patrocinadores> patrocinadores = cq.from(Patrocinadores.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(patrocinadores, entity), cb.isNotEmpty(patrocinadores.get(Patrocinadores_.campeonatosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Campeonatos> findCampeonatosCollection(Patrocinadores entity) {
        return this.getMergedEntity(entity).getCampeonatosCollection();
    }

    @Override
    public Patrocinadores findWithParents(Patrocinadores entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Patrocinadores> cq = cb.createQuery(Patrocinadores.class);
        Root<Patrocinadores> patrocinadores = cq.from(Patrocinadores.class);
        patrocinadores.fetch(Patrocinadores_.campeonatosCollection, JoinType.LEFT);
        cq.select(patrocinadores).where(cb.equal(patrocinadores, entity));
        try {
            return em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return entity;
        }
    }
    
}
