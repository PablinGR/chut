/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Arbitros;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Campeonatos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ryuku
 */
public class ArbitrosJpaController implements Serializable {

    public ArbitrosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Arbitros arbitros) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Campeonatos idcampeonato = arbitros.getIdcampeonato();
            if (idcampeonato != null) {
                idcampeonato = em.getReference(idcampeonato.getClass(), idcampeonato.getIdcampeonato());
                arbitros.setIdcampeonato(idcampeonato);
            }
            em.persist(arbitros);
            if (idcampeonato != null) {
                idcampeonato.getArbitrosCollection().add(arbitros);
                idcampeonato = em.merge(idcampeonato);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArbitros(arbitros.getCedulaarbitro()) != null) {
                throw new PreexistingEntityException("Arbitros " + arbitros + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Arbitros arbitros) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Arbitros persistentArbitros = em.find(Arbitros.class, arbitros.getCedulaarbitro());
            Campeonatos idcampeonatoOld = persistentArbitros.getIdcampeonato();
            Campeonatos idcampeonatoNew = arbitros.getIdcampeonato();
            if (idcampeonatoNew != null) {
                idcampeonatoNew = em.getReference(idcampeonatoNew.getClass(), idcampeonatoNew.getIdcampeonato());
                arbitros.setIdcampeonato(idcampeonatoNew);
            }
            arbitros = em.merge(arbitros);
            if (idcampeonatoOld != null && !idcampeonatoOld.equals(idcampeonatoNew)) {
                idcampeonatoOld.getArbitrosCollection().remove(arbitros);
                idcampeonatoOld = em.merge(idcampeonatoOld);
            }
            if (idcampeonatoNew != null && !idcampeonatoNew.equals(idcampeonatoOld)) {
                idcampeonatoNew.getArbitrosCollection().add(arbitros);
                idcampeonatoNew = em.merge(idcampeonatoNew);
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
                String id = arbitros.getCedulaarbitro();
                if (findArbitros(id) == null) {
                    throw new NonexistentEntityException("The arbitros with id " + id + " no longer exists.");
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
            Arbitros arbitros;
            try {
                arbitros = em.getReference(Arbitros.class, id);
                arbitros.getCedulaarbitro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arbitros with id " + id + " no longer exists.", enfe);
            }
            Campeonatos idcampeonato = arbitros.getIdcampeonato();
            if (idcampeonato != null) {
                idcampeonato.getArbitrosCollection().remove(arbitros);
                idcampeonato = em.merge(idcampeonato);
            }
            em.remove(arbitros);
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

    public List<Arbitros> findArbitrosEntities() {
        return findArbitrosEntities(true, -1, -1);
    }

    public List<Arbitros> findArbitrosEntities(int maxResults, int firstResult) {
        return findArbitrosEntities(false, maxResults, firstResult);
    }

    private List<Arbitros> findArbitrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arbitros.class));
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

    public Arbitros findArbitros(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arbitros.class, id);
        } finally {
            em.close();
        }
    }

    public int getArbitrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arbitros> rt = cq.from(Arbitros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
