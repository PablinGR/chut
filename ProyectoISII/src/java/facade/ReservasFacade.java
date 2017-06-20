/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Reservas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Reservas_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Partidos;
import MD.Campeonatos;
import MD.Horarios;
import MD.Partidos;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class ReservasFacade extends AbstractFacade<Reservas> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservasFacade() {
        super(Reservas.class);
    }

    public boolean isPartidosCollectionEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotEmpty(reservas.get(Reservas_.partidosCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Partidos> findPartidosCollection(Reservas entity) {
        return this.getMergedEntity(entity).getPartidosCollection();
    }

    public boolean isIdcampeonatoEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotNull(reservas.get(Reservas_.idcampeonato)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Campeonatos findIdcampeonato(Reservas entity) {
        return this.getMergedEntity(entity).getIdcampeonato();
    }

    public boolean isIdhorarioEmpty(Reservas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Reservas> reservas = cq.from(Reservas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(reservas, entity), cb.isNotNull(reservas.get(Reservas_.idhorario)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Horarios findIdhorario(Reservas entity) {
        return this.getMergedEntity(entity).getIdhorario();
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
    
}
