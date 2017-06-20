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
import MD.Campeonatos;
import MD.Patrocinadores;
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
public class PatrocinadoresJpaController implements Serializable {

    public PatrocinadoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patrocinadores patrocinadores) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (patrocinadores.getCampeonatosCollection() == null) {
            patrocinadores.setCampeonatosCollection(new ArrayList<Campeonatos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Campeonatos> attachedCampeonatosCollection = new ArrayList<Campeonatos>();
            for (Campeonatos campeonatosCollectionCampeonatosToAttach : patrocinadores.getCampeonatosCollection()) {
                campeonatosCollectionCampeonatosToAttach = em.getReference(campeonatosCollectionCampeonatosToAttach.getClass(), campeonatosCollectionCampeonatosToAttach.getIdcampeonato());
                attachedCampeonatosCollection.add(campeonatosCollectionCampeonatosToAttach);
            }
            patrocinadores.setCampeonatosCollection(attachedCampeonatosCollection);
            em.persist(patrocinadores);
            for (Campeonatos campeonatosCollectionCampeonatos : patrocinadores.getCampeonatosCollection()) {
                campeonatosCollectionCampeonatos.getPatrocinadoresCollection().add(patrocinadores);
                campeonatosCollectionCampeonatos = em.merge(campeonatosCollectionCampeonatos);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPatrocinadores(patrocinadores.getRucpatrocinador()) != null) {
                throw new PreexistingEntityException("Patrocinadores " + patrocinadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patrocinadores patrocinadores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Patrocinadores persistentPatrocinadores = em.find(Patrocinadores.class, patrocinadores.getRucpatrocinador());
            Collection<Campeonatos> campeonatosCollectionOld = persistentPatrocinadores.getCampeonatosCollection();
            Collection<Campeonatos> campeonatosCollectionNew = patrocinadores.getCampeonatosCollection();
            Collection<Campeonatos> attachedCampeonatosCollectionNew = new ArrayList<Campeonatos>();
            for (Campeonatos campeonatosCollectionNewCampeonatosToAttach : campeonatosCollectionNew) {
                campeonatosCollectionNewCampeonatosToAttach = em.getReference(campeonatosCollectionNewCampeonatosToAttach.getClass(), campeonatosCollectionNewCampeonatosToAttach.getIdcampeonato());
                attachedCampeonatosCollectionNew.add(campeonatosCollectionNewCampeonatosToAttach);
            }
            campeonatosCollectionNew = attachedCampeonatosCollectionNew;
            patrocinadores.setCampeonatosCollection(campeonatosCollectionNew);
            patrocinadores = em.merge(patrocinadores);
            for (Campeonatos campeonatosCollectionOldCampeonatos : campeonatosCollectionOld) {
                if (!campeonatosCollectionNew.contains(campeonatosCollectionOldCampeonatos)) {
                    campeonatosCollectionOldCampeonatos.getPatrocinadoresCollection().remove(patrocinadores);
                    campeonatosCollectionOldCampeonatos = em.merge(campeonatosCollectionOldCampeonatos);
                }
            }
            for (Campeonatos campeonatosCollectionNewCampeonatos : campeonatosCollectionNew) {
                if (!campeonatosCollectionOld.contains(campeonatosCollectionNewCampeonatos)) {
                    campeonatosCollectionNewCampeonatos.getPatrocinadoresCollection().add(patrocinadores);
                    campeonatosCollectionNewCampeonatos = em.merge(campeonatosCollectionNewCampeonatos);
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
                String id = patrocinadores.getRucpatrocinador();
                if (findPatrocinadores(id) == null) {
                    throw new NonexistentEntityException("The patrocinadores with id " + id + " no longer exists.");
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
            Patrocinadores patrocinadores;
            try {
                patrocinadores = em.getReference(Patrocinadores.class, id);
                patrocinadores.getRucpatrocinador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patrocinadores with id " + id + " no longer exists.", enfe);
            }
            Collection<Campeonatos> campeonatosCollection = patrocinadores.getCampeonatosCollection();
            for (Campeonatos campeonatosCollectionCampeonatos : campeonatosCollection) {
                campeonatosCollectionCampeonatos.getPatrocinadoresCollection().remove(patrocinadores);
                campeonatosCollectionCampeonatos = em.merge(campeonatosCollectionCampeonatos);
            }
            em.remove(patrocinadores);
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

    public List<Patrocinadores> findPatrocinadoresEntities() {
        return findPatrocinadoresEntities(true, -1, -1);
    }

    public List<Patrocinadores> findPatrocinadoresEntities(int maxResults, int firstResult) {
        return findPatrocinadoresEntities(false, maxResults, firstResult);
    }

    private List<Patrocinadores> findPatrocinadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patrocinadores.class));
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

    public Patrocinadores findPatrocinadores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patrocinadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatrocinadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patrocinadores> rt = cq.from(Patrocinadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
