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
import MD.Equipos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Jugadores;
import java.util.ArrayList;
import java.util.Collection;
import MD.Partidos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ryuku
 */
public class EquiposJpaController implements Serializable {

    public EquiposJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipos equipos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (equipos.getJugadoresCollection() == null) {
            equipos.setJugadoresCollection(new ArrayList<Jugadores>());
        }
        if (equipos.getPartidosCollection() == null) {
            equipos.setPartidosCollection(new ArrayList<Partidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Jugadores> attachedJugadoresCollection = new ArrayList<Jugadores>();
            for (Jugadores jugadoresCollectionJugadoresToAttach : equipos.getJugadoresCollection()) {
                jugadoresCollectionJugadoresToAttach = em.getReference(jugadoresCollectionJugadoresToAttach.getClass(), jugadoresCollectionJugadoresToAttach.getCedulajugador());
                attachedJugadoresCollection.add(jugadoresCollectionJugadoresToAttach);
            }
            equipos.setJugadoresCollection(attachedJugadoresCollection);
            Collection<Partidos> attachedPartidosCollection = new ArrayList<Partidos>();
            for (Partidos partidosCollectionPartidosToAttach : equipos.getPartidosCollection()) {
                partidosCollectionPartidosToAttach = em.getReference(partidosCollectionPartidosToAttach.getClass(), partidosCollectionPartidosToAttach.getIdpartido());
                attachedPartidosCollection.add(partidosCollectionPartidosToAttach);
            }
            equipos.setPartidosCollection(attachedPartidosCollection);
            em.persist(equipos);
            for (Jugadores jugadoresCollectionJugadores : equipos.getJugadoresCollection()) {
                jugadoresCollectionJugadores.getEquiposCollection().add(equipos);
                jugadoresCollectionJugadores = em.merge(jugadoresCollectionJugadores);
            }
            for (Partidos partidosCollectionPartidos : equipos.getPartidosCollection()) {
                Equipos oldIdequipoOfPartidosCollectionPartidos = partidosCollectionPartidos.getIdequipo();
                partidosCollectionPartidos.setIdequipo(equipos);
                partidosCollectionPartidos = em.merge(partidosCollectionPartidos);
                if (oldIdequipoOfPartidosCollectionPartidos != null) {
                    oldIdequipoOfPartidosCollectionPartidos.getPartidosCollection().remove(partidosCollectionPartidos);
                    oldIdequipoOfPartidosCollectionPartidos = em.merge(oldIdequipoOfPartidosCollectionPartidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEquipos(equipos.getIdequipo()) != null) {
                throw new PreexistingEntityException("Equipos " + equipos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipos equipos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipos persistentEquipos = em.find(Equipos.class, equipos.getIdequipo());
            Collection<Jugadores> jugadoresCollectionOld = persistentEquipos.getJugadoresCollection();
            Collection<Jugadores> jugadoresCollectionNew = equipos.getJugadoresCollection();
            Collection<Partidos> partidosCollectionOld = persistentEquipos.getPartidosCollection();
            Collection<Partidos> partidosCollectionNew = equipos.getPartidosCollection();
            List<String> illegalOrphanMessages = null;
            for (Partidos partidosCollectionOldPartidos : partidosCollectionOld) {
                if (!partidosCollectionNew.contains(partidosCollectionOldPartidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidos " + partidosCollectionOldPartidos + " since its idequipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugadores> attachedJugadoresCollectionNew = new ArrayList<Jugadores>();
            for (Jugadores jugadoresCollectionNewJugadoresToAttach : jugadoresCollectionNew) {
                jugadoresCollectionNewJugadoresToAttach = em.getReference(jugadoresCollectionNewJugadoresToAttach.getClass(), jugadoresCollectionNewJugadoresToAttach.getCedulajugador());
                attachedJugadoresCollectionNew.add(jugadoresCollectionNewJugadoresToAttach);
            }
            jugadoresCollectionNew = attachedJugadoresCollectionNew;
            equipos.setJugadoresCollection(jugadoresCollectionNew);
            Collection<Partidos> attachedPartidosCollectionNew = new ArrayList<Partidos>();
            for (Partidos partidosCollectionNewPartidosToAttach : partidosCollectionNew) {
                partidosCollectionNewPartidosToAttach = em.getReference(partidosCollectionNewPartidosToAttach.getClass(), partidosCollectionNewPartidosToAttach.getIdpartido());
                attachedPartidosCollectionNew.add(partidosCollectionNewPartidosToAttach);
            }
            partidosCollectionNew = attachedPartidosCollectionNew;
            equipos.setPartidosCollection(partidosCollectionNew);
            equipos = em.merge(equipos);
            for (Jugadores jugadoresCollectionOldJugadores : jugadoresCollectionOld) {
                if (!jugadoresCollectionNew.contains(jugadoresCollectionOldJugadores)) {
                    jugadoresCollectionOldJugadores.getEquiposCollection().remove(equipos);
                    jugadoresCollectionOldJugadores = em.merge(jugadoresCollectionOldJugadores);
                }
            }
            for (Jugadores jugadoresCollectionNewJugadores : jugadoresCollectionNew) {
                if (!jugadoresCollectionOld.contains(jugadoresCollectionNewJugadores)) {
                    jugadoresCollectionNewJugadores.getEquiposCollection().add(equipos);
                    jugadoresCollectionNewJugadores = em.merge(jugadoresCollectionNewJugadores);
                }
            }
            for (Partidos partidosCollectionNewPartidos : partidosCollectionNew) {
                if (!partidosCollectionOld.contains(partidosCollectionNewPartidos)) {
                    Equipos oldIdequipoOfPartidosCollectionNewPartidos = partidosCollectionNewPartidos.getIdequipo();
                    partidosCollectionNewPartidos.setIdequipo(equipos);
                    partidosCollectionNewPartidos = em.merge(partidosCollectionNewPartidos);
                    if (oldIdequipoOfPartidosCollectionNewPartidos != null && !oldIdequipoOfPartidosCollectionNewPartidos.equals(equipos)) {
                        oldIdequipoOfPartidosCollectionNewPartidos.getPartidosCollection().remove(partidosCollectionNewPartidos);
                        oldIdequipoOfPartidosCollectionNewPartidos = em.merge(oldIdequipoOfPartidosCollectionNewPartidos);
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
                Integer id = equipos.getIdequipo();
                if (findEquipos(id) == null) {
                    throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.");
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
            Equipos equipos;
            try {
                equipos = em.getReference(Equipos.class, id);
                equipos.getIdequipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Partidos> partidosCollectionOrphanCheck = equipos.getPartidosCollection();
            for (Partidos partidosCollectionOrphanCheckPartidos : partidosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipos (" + equipos + ") cannot be destroyed since the Partidos " + partidosCollectionOrphanCheckPartidos + " in its partidosCollection field has a non-nullable idequipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Jugadores> jugadoresCollection = equipos.getJugadoresCollection();
            for (Jugadores jugadoresCollectionJugadores : jugadoresCollection) {
                jugadoresCollectionJugadores.getEquiposCollection().remove(equipos);
                jugadoresCollectionJugadores = em.merge(jugadoresCollectionJugadores);
            }
            em.remove(equipos);
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

    public List<Equipos> findEquiposEntities() {
        return findEquiposEntities(true, -1, -1);
    }

    public List<Equipos> findEquiposEntities(int maxResults, int firstResult) {
        return findEquiposEntities(false, maxResults, firstResult);
    }

    private List<Equipos> findEquiposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipos.class));
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

    public Equipos findEquipos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquiposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipos> rt = cq.from(Equipos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
