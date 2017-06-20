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
import MD.Equipos;
import MD.Jugadores;
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
public class JugadoresJpaController implements Serializable {

    public JugadoresJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugadores jugadores) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (jugadores.getEquiposCollection() == null) {
            jugadores.setEquiposCollection(new ArrayList<Equipos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Equipos> attachedEquiposCollection = new ArrayList<Equipos>();
            for (Equipos equiposCollectionEquiposToAttach : jugadores.getEquiposCollection()) {
                equiposCollectionEquiposToAttach = em.getReference(equiposCollectionEquiposToAttach.getClass(), equiposCollectionEquiposToAttach.getIdequipo());
                attachedEquiposCollection.add(equiposCollectionEquiposToAttach);
            }
            jugadores.setEquiposCollection(attachedEquiposCollection);
            em.persist(jugadores);
            for (Equipos equiposCollectionEquipos : jugadores.getEquiposCollection()) {
                equiposCollectionEquipos.getJugadoresCollection().add(jugadores);
                equiposCollectionEquipos = em.merge(equiposCollectionEquipos);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findJugadores(jugadores.getCedulajugador()) != null) {
                throw new PreexistingEntityException("Jugadores " + jugadores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugadores jugadores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Jugadores persistentJugadores = em.find(Jugadores.class, jugadores.getCedulajugador());
            Collection<Equipos> equiposCollectionOld = persistentJugadores.getEquiposCollection();
            Collection<Equipos> equiposCollectionNew = jugadores.getEquiposCollection();
            Collection<Equipos> attachedEquiposCollectionNew = new ArrayList<Equipos>();
            for (Equipos equiposCollectionNewEquiposToAttach : equiposCollectionNew) {
                equiposCollectionNewEquiposToAttach = em.getReference(equiposCollectionNewEquiposToAttach.getClass(), equiposCollectionNewEquiposToAttach.getIdequipo());
                attachedEquiposCollectionNew.add(equiposCollectionNewEquiposToAttach);
            }
            equiposCollectionNew = attachedEquiposCollectionNew;
            jugadores.setEquiposCollection(equiposCollectionNew);
            jugadores = em.merge(jugadores);
            for (Equipos equiposCollectionOldEquipos : equiposCollectionOld) {
                if (!equiposCollectionNew.contains(equiposCollectionOldEquipos)) {
                    equiposCollectionOldEquipos.getJugadoresCollection().remove(jugadores);
                    equiposCollectionOldEquipos = em.merge(equiposCollectionOldEquipos);
                }
            }
            for (Equipos equiposCollectionNewEquipos : equiposCollectionNew) {
                if (!equiposCollectionOld.contains(equiposCollectionNewEquipos)) {
                    equiposCollectionNewEquipos.getJugadoresCollection().add(jugadores);
                    equiposCollectionNewEquipos = em.merge(equiposCollectionNewEquipos);
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
                String id = jugadores.getCedulajugador();
                if (findJugadores(id) == null) {
                    throw new NonexistentEntityException("The jugadores with id " + id + " no longer exists.");
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
            Jugadores jugadores;
            try {
                jugadores = em.getReference(Jugadores.class, id);
                jugadores.getCedulajugador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugadores with id " + id + " no longer exists.", enfe);
            }
            Collection<Equipos> equiposCollection = jugadores.getEquiposCollection();
            for (Equipos equiposCollectionEquipos : equiposCollection) {
                equiposCollectionEquipos.getJugadoresCollection().remove(jugadores);
                equiposCollectionEquipos = em.merge(equiposCollectionEquipos);
            }
            em.remove(jugadores);
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

    public List<Jugadores> findJugadoresEntities() {
        return findJugadoresEntities(true, -1, -1);
    }

    public List<Jugadores> findJugadoresEntities(int maxResults, int firstResult) {
        return findJugadoresEntities(false, maxResults, firstResult);
    }

    private List<Jugadores> findJugadoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugadores.class));
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

    public Jugadores findJugadores(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugadores.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugadores> rt = cq.from(Jugadores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
