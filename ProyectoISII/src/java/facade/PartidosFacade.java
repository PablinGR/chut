/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Partidos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Partidos_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Canchas;
import MD.Equipos;
import MD.Reservas;
import MD.Canchas;
import MD.Reservas;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class PartidosFacade extends AbstractFacade<Partidos> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartidosFacade() {
        super(Partidos.class);
    }

    public boolean isCanchasCollectionEmpty(Partidos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Partidos> partidos = cq.from(Partidos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(partidos, entity), cb.isNotEmpty(partidos.get(Partidos_.canchasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Canchas> findCanchasCollection(Partidos entity) {
        return this.getMergedEntity(entity).getCanchasCollection();
    }

    public boolean isIdequipoEmpty(Partidos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Partidos> partidos = cq.from(Partidos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(partidos, entity), cb.isNotNull(partidos.get(Partidos_.idequipo)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Equipos findIdequipo(Partidos entity) {
        return this.getMergedEntity(entity).getIdequipo();
    }

    public boolean isIdreservaEmpty(Partidos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Partidos> partidos = cq.from(Partidos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(partidos, entity), cb.isNotNull(partidos.get(Partidos_.idreserva)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Reservas findIdreserva(Partidos entity) {
        return this.getMergedEntity(entity).getIdreserva();
    }

    public boolean isIdcanchaEmpty(Partidos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Partidos> partidos = cq.from(Partidos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(partidos, entity), cb.isNotNull(partidos.get(Partidos_.idcancha)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Canchas findIdcancha(Partidos entity) {
        return this.getMergedEntity(entity).getIdcancha();
    }

    public boolean isReservasCollectionEmpty(Partidos entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Partidos> partidos = cq.from(Partidos.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(partidos, entity), cb.isNotEmpty(partidos.get(Partidos_.reservasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Reservas> findReservasCollection(Partidos entity) {
        return this.getMergedEntity(entity).getReservasCollection();
    }
    
}
