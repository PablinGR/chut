/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Canchas;
import MD.Propietarios;
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
public class PropietariosJpaController implements Serializable {

    public PropietariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propietarios propietarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (propietarios.getCanchasCollection() == null) {
            propietarios.setCanchasCollection(new ArrayList<Canchas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Canchas> attachedCanchasCollection = new ArrayList<Canchas>();
            for (Canchas canchasCollectionCanchasToAttach : propietarios.getCanchasCollection()) {
                canchasCollectionCanchasToAttach = em.getReference(canchasCollectionCanchasToAttach.getClass(), canchasCollectionCanchasToAttach.getIdcancha());
                attachedCanchasCollection.add(canchasCollectionCanchasToAttach);
            }
            propietarios.setCanchasCollection(attachedCanchasCollection);
            em.persist(propietarios);
            for (Canchas canchasCollectionCanchas : propietarios.getCanchasCollection()) {
                canchasCollectionCanchas.getPropietariosCollection().add(propietarios);
                canchasCollectionCanchas = em.merge(canchasCollectionCanchas);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPropietarios(propietarios.getCedulapropietarios()) != null) {
                throw new PreexistingEntityException("Propietarios " + propietarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propietarios propietarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propietarios persistentPropietarios = em.find(Propietarios.class, propietarios.getCedulapropietarios());
            Collection<Canchas> canchasCollectionOld = persistentPropietarios.getCanchasCollection();
            Collection<Canchas> canchasCollectionNew = propietarios.getCanchasCollection();
            Collection<Canchas> attachedCanchasCollectionNew = new ArrayList<Canchas>();
            for (Canchas canchasCollectionNewCanchasToAttach : canchasCollectionNew) {
                canchasCollectionNewCanchasToAttach = em.getReference(canchasCollectionNewCanchasToAttach.getClass(), canchasCollectionNewCanchasToAttach.getIdcancha());
                attachedCanchasCollectionNew.add(canchasCollectionNewCanchasToAttach);
            }
            canchasCollectionNew = attachedCanchasCollectionNew;
            propietarios.setCanchasCollection(canchasCollectionNew);
            propietarios = em.merge(propietarios);
            for (Canchas canchasCollectionOldCanchas : canchasCollectionOld) {
                if (!canchasCollectionNew.contains(canchasCollectionOldCanchas)) {
                    canchasCollectionOldCanchas.getPropietariosCollection().remove(propietarios);
                    canchasCollectionOldCanchas = em.merge(canchasCollectionOldCanchas);
                }
            }
            for (Canchas canchasCollectionNewCanchas : canchasCollectionNew) {
                if (!canchasCollectionOld.contains(canchasCollectionNewCanchas)) {
                    canchasCollectionNewCanchas.getPropietariosCollection().add(propietarios);
                    canchasCollectionNewCanchas = em.merge(canchasCollectionNewCanchas);
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
                String id = propietarios.getCedulapropietarios();
                if (findPropietarios(id) == null) {
                    throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Propietarios propietarios;
            try {
                propietarios = em.getReference(Propietarios.class, id);
                propietarios.getCedulapropietarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propietarios with id " + id + " no longer exists.", enfe);
            }
            Collection<Canchas> canchasCollection = propietarios.getCanchasCollection();
            for (Canchas canchasCollectionCanchas : canchasCollection) {
                canchasCollectionCanchas.getPropietariosCollection().remove(propietarios);
                canchasCollectionCanchas = em.merge(canchasCollectionCanchas);
            }
            em.remove(propietarios);
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

    public List<Propietarios> findPropietariosEntities() {
        return findPropietariosEntities(true, -1, -1);
    }

    public List<Propietarios> findPropietariosEntities(int maxResults, int firstResult) {
        return findPropietariosEntities(false, maxResults, firstResult);
    }

    private List<Propietarios> findPropietariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propietarios.class));
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

    public Propietarios findPropietarios(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propietarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropietariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propietarios> rt = cq.from(Propietarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
