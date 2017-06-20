/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.IllegalOrphanException;
import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Horarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Reservas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ryuku
 */
public class HorariosJpaController implements Serializable {

    public HorariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Horarios horarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (horarios.getReservasCollection() == null) {
            horarios.setReservasCollection(new ArrayList<Reservas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Reservas> attachedReservasCollection = new ArrayList<Reservas>();
            for (Reservas reservasCollectionReservasToAttach : horarios.getReservasCollection()) {
                reservasCollectionReservasToAttach = em.getReference(reservasCollectionReservasToAttach.getClass(), reservasCollectionReservasToAttach.getIdreserva());
                attachedReservasCollection.add(reservasCollectionReservasToAttach);
            }
            horarios.setReservasCollection(attachedReservasCollection);
            em.persist(horarios);
            for (Reservas reservasCollectionReservas : horarios.getReservasCollection()) {
                Horarios oldIdhorarioOfReservasCollectionReservas = reservasCollectionReservas.getIdhorario();
                reservasCollectionReservas.setIdhorario(horarios);
                reservasCollectionReservas = em.merge(reservasCollectionReservas);
                if (oldIdhorarioOfReservasCollectionReservas != null) {
                    oldIdhorarioOfReservasCollectionReservas.getReservasCollection().remove(reservasCollectionReservas);
                    oldIdhorarioOfReservasCollectionReservas = em.merge(oldIdhorarioOfReservasCollectionReservas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHorarios(horarios.getIdhorario()) != null) {
                throw new PreexistingEntityException("Horarios " + horarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Horarios horarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Horarios persistentHorarios = em.find(Horarios.class, horarios.getIdhorario());
            Collection<Reservas> reservasCollectionOld = persistentHorarios.getReservasCollection();
            Collection<Reservas> reservasCollectionNew = horarios.getReservasCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservas reservasCollectionOldReservas : reservasCollectionOld) {
                if (!reservasCollectionNew.contains(reservasCollectionOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservas " + reservasCollectionOldReservas + " since its idhorario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Reservas> attachedReservasCollectionNew = new ArrayList<Reservas>();
            for (Reservas reservasCollectionNewReservasToAttach : reservasCollectionNew) {
                reservasCollectionNewReservasToAttach = em.getReference(reservasCollectionNewReservasToAttach.getClass(), reservasCollectionNewReservasToAttach.getIdreserva());
                attachedReservasCollectionNew.add(reservasCollectionNewReservasToAttach);
            }
            reservasCollectionNew = attachedReservasCollectionNew;
            horarios.setReservasCollection(reservasCollectionNew);
            horarios = em.merge(horarios);
            for (Reservas reservasCollectionNewReservas : reservasCollectionNew) {
                if (!reservasCollectionOld.contains(reservasCollectionNewReservas)) {
                    Horarios oldIdhorarioOfReservasCollectionNewReservas = reservasCollectionNewReservas.getIdhorario();
                    reservasCollectionNewReservas.setIdhorario(horarios);
                    reservasCollectionNewReservas = em.merge(reservasCollectionNewReservas);
                    if (oldIdhorarioOfReservasCollectionNewReservas != null && !oldIdhorarioOfReservasCollectionNewReservas.equals(horarios)) {
                        oldIdhorarioOfReservasCollectionNewReservas.getReservasCollection().remove(reservasCollectionNewReservas);
                        oldIdhorarioOfReservasCollectionNewReservas = em.merge(oldIdhorarioOfReservasCollectionNewReservas);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = horarios.getIdhorario();
                if (findHorarios(id) == null) {
                    throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Horarios horarios;
            try {
                horarios = em.getReference(Horarios.class, id);
                horarios.getIdhorario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservas> reservasCollectionOrphanCheck = horarios.getReservasCollection();
            for (Reservas reservasCollectionOrphanCheckReservas : reservasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Horarios (" + horarios + ") cannot be destroyed since the Reservas " + reservasCollectionOrphanCheckReservas + " in its reservasCollection field has a non-nullable idhorario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(horarios);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Horarios> findHorariosEntities() {
        return findHorariosEntities(true, -1, -1);
    }

    public List<Horarios> findHorariosEntities(int maxResults, int firstResult) {
        return findHorariosEntities(false, maxResults, firstResult);
    }

    private List<Horarios> findHorariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Horarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Horarios findHorarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Horarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Horarios> rt = cq.from(Horarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
