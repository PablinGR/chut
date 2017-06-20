/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Campeonatos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Campeonatos_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Patrocinadores;
import MD.Reservas;
import MD.Arbitros;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class CampeonatosFacade extends AbstractFacade<Campeonatos> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CampeonatosFacade() {
        super(Campeonatos.class);
    }

    public boolean isPatrocinadoresCollectionEmpty(Campeonatos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Campeonatos> campeonatos = cq.from(Campeonatos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(campeonatos, entity), cb.isNotEmpty(campeonatos.get(Campeonatos_.patrocinadoresCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Patrocinadores> findPatrocinadoresCollection(Campeonatos entity) {
        return this.getMergedEntity(entity).getPatrocinadoresCollection();
    }

    public boolean isReservasCollectionEmpty(Campeonatos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Campeonatos> campeonatos = cq.from(Campeonatos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(campeonatos, entity), cb.isNotEmpty(campeonatos.get(Campeonatos_.reservasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Reservas> findReservasCollection(Campeonatos entity) {
        return this.getMergedEntity(entity).getReservasCollection();
    }

    public boolean isArbitrosCollectionEmpty(Campeonatos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Campeonatos> campeonatos = cq.from(Campeonatos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(campeonatos, entity), cb.isNotEmpty(campeonatos.get(Campeonatos_.arbitrosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Arbitros> findArbitrosCollection(Campeonatos entity) {
        return this.getMergedEntity(entity).getArbitrosCollection();
    }
    
}
