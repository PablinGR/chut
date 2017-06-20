/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Canchas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Canchas_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Propietarios;
import MD.Partidos;
import MD.Partidos;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class CanchasFacade extends AbstractFacade<Canchas> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
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

    public boolean isIdpartidoEmpty(Canchas entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Canchas> canchas = cq.from(Canchas.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(canchas, entity), cb.isNotNull(canchas.get(Canchas_.idpartido)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Partidos findIdpartido(Canchas entity) {
        return this.getMergedEntity(entity).getIdpartido();
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
    
}
