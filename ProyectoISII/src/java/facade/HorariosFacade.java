/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import MD.Horarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import MD.Horarios_;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Reservas;
import java.util.Collection;

/**
 *
 * @author ryuku
 */
@Stateless
public class HorariosFacade extends AbstractFacade<Horarios> {

    @PersistenceContext(unitName = "ProyectoISIIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariosFacade() {
        super(Horarios.class);
    }

    public boolean isReservasCollectionEmpty(Horarios entity) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Horarios> horarios = cq.from(Horarios.class);
        cq.select(cb.literal(1L)).distinct(true).where(cb.equal(horarios, entity), cb.isNotEmpty(horarios.get(Horarios_.reservasCollection)));
        return em.createQuery(cq).getResultList().isEmpty();
    }

    public Collection<Reservas> findReservasCollection(Horarios entity) {
        return this.getMergedEntity(entity).getReservasCollection();
    }
    
}
