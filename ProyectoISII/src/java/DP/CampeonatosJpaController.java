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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Patrocinadores;
import java.util.ArrayList;
import java.util.Collection;
import MD.Reservas;
import MD.Arbitros;
import MD.Campeonatos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ryuku
 */
public class CampeonatosJpaController implements Serializable {

    public CampeonatosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campeonatos campeonatos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (campeonatos.getPatrocinadoresCollection() == null) {
            campeonatos.setPatrocinadoresCollection(new ArrayList<Patrocinadores>());
        }
        if (campeonatos.getReservasCollection() == null) {
            campeonatos.setReservasCollection(new ArrayList<Reservas>());
        }
        if (campeonatos.getArbitrosCollection() == null) {
            campeonatos.setArbitrosCollection(new ArrayList<Arbitros>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Patrocinadores> attachedPatrocinadoresCollection = new ArrayList<Patrocinadores>();
            for (Patrocinadores patrocinadoresCollectionPatrocinadoresToAttach : campeonatos.getPatrocinadoresCollection()) {
                patrocinadoresCollectionPatrocinadoresToAttach = em.getReference(patrocinadoresCollectionPatrocinadoresToAttach.getClass(), patrocinadoresCollectionPatrocinadoresToAttach.getRucpatrocinador());
                attachedPatrocinadoresCollection.add(patrocinadoresCollectionPatrocinadoresToAttach);
            }
            campeonatos.setPatrocinadoresCollection(attachedPatrocinadoresCollection);
            Collection<Reservas> attachedReservasCollection = new ArrayList<Reservas>();
            for (Reservas reservasCollectionReservasToAttach : campeonatos.getReservasCollection()) {
                reservasCollectionReservasToAttach = em.getReference(reservasCollectionReservasToAttach.getClass(), reservasCollectionReservasToAttach.getIdreserva());
                attachedReservasCollection.add(reservasCollectionReservasToAttach);
            }
            campeonatos.setReservasCollection(attachedReservasCollection);
            Collection<Arbitros> attachedArbitrosCollection = new ArrayList<Arbitros>();
            for (Arbitros arbitrosCollectionArbitrosToAttach : campeonatos.getArbitrosCollection()) {
                arbitrosCollectionArbitrosToAttach = em.getReference(arbitrosCollectionArbitrosToAttach.getClass(), arbitrosCollectionArbitrosToAttach.getCedulaarbitro());
                attachedArbitrosCollection.add(arbitrosCollectionArbitrosToAttach);
            }
            campeonatos.setArbitrosCollection(attachedArbitrosCollection);
            em.persist(campeonatos);
            for (Patrocinadores patrocinadoresCollectionPatrocinadores : campeonatos.getPatrocinadoresCollection()) {
                patrocinadoresCollectionPatrocinadores.getCampeonatosCollection().add(campeonatos);
                patrocinadoresCollectionPatrocinadores = em.merge(patrocinadoresCollectionPatrocinadores);
            }
            for (Reservas reservasCollectionReservas : campeonatos.getReservasCollection()) {
                Campeonatos oldIdcampeonatoOfReservasCollectionReservas = reservasCollectionReservas.getIdcampeonato();
                reservasCollectionReservas.setIdcampeonato(campeonatos);
                reservasCollectionReservas = em.merge(reservasCollectionReservas);
                if (oldIdcampeonatoOfReservasCollectionReservas != null) {
                    oldIdcampeonatoOfReservasCollectionReservas.getReservasCollection().remove(reservasCollectionReservas);
                    oldIdcampeonatoOfReservasCollectionReservas = em.merge(oldIdcampeonatoOfReservasCollectionReservas);
                }
            }
            for (Arbitros arbitrosCollectionArbitros : campeonatos.getArbitrosCollection()) {
                Campeonatos oldIdcampeonatoOfArbitrosCollectionArbitros = arbitrosCollectionArbitros.getIdcampeonato();
                arbitrosCollectionArbitros.setIdcampeonato(campeonatos);
                arbitrosCollectionArbitros = em.merge(arbitrosCollectionArbitros);
                if (oldIdcampeonatoOfArbitrosCollectionArbitros != null) {
                    oldIdcampeonatoOfArbitrosCollectionArbitros.getArbitrosCollection().remove(arbitrosCollectionArbitros);
                    oldIdcampeonatoOfArbitrosCollectionArbitros = em.merge(oldIdcampeonatoOfArbitrosCollectionArbitros);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCampeonatos(campeonatos.getIdcampeonato()) != null) {
                throw new PreexistingEntityException("Campeonatos " + campeonatos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campeonatos campeonatos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Campeonatos persistentCampeonatos = em.find(Campeonatos.class, campeonatos.getIdcampeonato());
            Collection<Patrocinadores> patrocinadoresCollectionOld = persistentCampeonatos.getPatrocinadoresCollection();
            Collection<Patrocinadores> patrocinadoresCollectionNew = campeonatos.getPatrocinadoresCollection();
            Collection<Reservas> reservasCollectionOld = persistentCampeonatos.getReservasCollection();
            Collection<Reservas> reservasCollectionNew = campeonatos.getReservasCollection();
            Collection<Arbitros> arbitrosCollectionOld = persistentCampeonatos.getArbitrosCollection();
            Collection<Arbitros> arbitrosCollectionNew = campeonatos.getArbitrosCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservas reservasCollectionOldReservas : reservasCollectionOld) {
                if (!reservasCollectionNew.contains(reservasCollectionOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservas " + reservasCollectionOldReservas + " since its idcampeonato field is not nullable.");
                }
            }
            for (Arbitros arbitrosCollectionOldArbitros : arbitrosCollectionOld) {
                if (!arbitrosCollectionNew.contains(arbitrosCollectionOldArbitros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Arbitros " + arbitrosCollectionOldArbitros + " since its idcampeonato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Patrocinadores> attachedPatrocinadoresCollectionNew = new ArrayList<Patrocinadores>();
            for (Patrocinadores patrocinadoresCollectionNewPatrocinadoresToAttach : patrocinadoresCollectionNew) {
                patrocinadoresCollectionNewPatrocinadoresToAttach = em.getReference(patrocinadoresCollectionNewPatrocinadoresToAttach.getClass(), patrocinadoresCollectionNewPatrocinadoresToAttach.getRucpatrocinador());
                attachedPatrocinadoresCollectionNew.add(patrocinadoresCollectionNewPatrocinadoresToAttach);
            }
            patrocinadoresCollectionNew = attachedPatrocinadoresCollectionNew;
            campeonatos.setPatrocinadoresCollection(patrocinadoresCollectionNew);
            Collection<Reservas> attachedReservasCollectionNew = new ArrayList<Reservas>();
            for (Reservas reservasCollectionNewReservasToAttach : reservasCollectionNew) {
                reservasCollectionNewReservasToAttach = em.getReference(reservasCollectionNewReservasToAttach.getClass(), reservasCollectionNewReservasToAttach.getIdreserva());
                attachedReservasCollectionNew.add(reservasCollectionNewReservasToAttach);
            }
            reservasCollectionNew = attachedReservasCollectionNew;
            campeonatos.setReservasCollection(reservasCollectionNew);
            Collection<Arbitros> attachedArbitrosCollectionNew = new ArrayList<Arbitros>();
            for (Arbitros arbitrosCollectionNewArbitrosToAttach : arbitrosCollectionNew) {
                arbitrosCollectionNewArbitrosToAttach = em.getReference(arbitrosCollectionNewArbitrosToAttach.getClass(), arbitrosCollectionNewArbitrosToAttach.getCedulaarbitro());
                attachedArbitrosCollectionNew.add(arbitrosCollectionNewArbitrosToAttach);
            }
            arbitrosCollectionNew = attachedArbitrosCollectionNew;
            campeonatos.setArbitrosCollection(arbitrosCollectionNew);
            campeonatos = em.merge(campeonatos);
            for (Patrocinadores patrocinadoresCollectionOldPatrocinadores : patrocinadoresCollectionOld) {
                if (!patrocinadoresCollectionNew.contains(patrocinadoresCollectionOldPatrocinadores)) {
                    patrocinadoresCollectionOldPatrocinadores.getCampeonatosCollection().remove(campeonatos);
                    patrocinadoresCollectionOldPatrocinadores = em.merge(patrocinadoresCollectionOldPatrocinadores);
                }
            }
            for (Patrocinadores patrocinadoresCollectionNewPatrocinadores : patrocinadoresCollectionNew) {
                if (!patrocinadoresCollectionOld.contains(patrocinadoresCollectionNewPatrocinadores)) {
                    patrocinadoresCollectionNewPatrocinadores.getCampeonatosCollection().add(campeonatos);
                    patrocinadoresCollectionNewPatrocinadores = em.merge(patrocinadoresCollectionNewPatrocinadores);
                }
            }
            for (Reservas reservasCollectionNewReservas : reservasCollectionNew) {
                if (!reservasCollectionOld.contains(reservasCollectionNewReservas)) {
                    Campeonatos oldIdcampeonatoOfReservasCollectionNewReservas = reservasCollectionNewReservas.getIdcampeonato();
                    reservasCollectionNewReservas.setIdcampeonato(campeonatos);
                    reservasCollectionNewReservas = em.merge(reservasCollectionNewReservas);
                    if (oldIdcampeonatoOfReservasCollectionNewReservas != null && !oldIdcampeonatoOfReservasCollectionNewReservas.equals(campeonatos)) {
                        oldIdcampeonatoOfReservasCollectionNewReservas.getReservasCollection().remove(reservasCollectionNewReservas);
                        oldIdcampeonatoOfReservasCollectionNewReservas = em.merge(oldIdcampeonatoOfReservasCollectionNewReservas);
                    }
                }
            }
            for (Arbitros arbitrosCollectionNewArbitros : arbitrosCollectionNew) {
                if (!arbitrosCollectionOld.contains(arbitrosCollectionNewArbitros)) {
                    Campeonatos oldIdcampeonatoOfArbitrosCollectionNewArbitros = arbitrosCollectionNewArbitros.getIdcampeonato();
                    arbitrosCollectionNewArbitros.setIdcampeonato(campeonatos);
                    arbitrosCollectionNewArbitros = em.merge(arbitrosCollectionNewArbitros);
                    if (oldIdcampeonatoOfArbitrosCollectionNewArbitros != null && !oldIdcampeonatoOfArbitrosCollectionNewArbitros.equals(campeonatos)) {
                        oldIdcampeonatoOfArbitrosCollectionNewArbitros.getArbitrosCollection().remove(arbitrosCollectionNewArbitros);
                        oldIdcampeonatoOfArbitrosCollectionNewArbitros = em.merge(oldIdcampeonatoOfArbitrosCollectionNewArbitros);
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
                Integer id = campeonatos.getIdcampeonato();
                if (findCampeonatos(id) == null) {
                    throw new NonexistentEntityException("The campeonatos with id " + id + " no longer exists.");
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
            Campeonatos campeonatos;
            try {
                campeonatos = em.getReference(Campeonatos.class, id);
                campeonatos.getIdcampeonato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campeonatos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservas> reservasCollectionOrphanCheck = campeonatos.getReservasCollection();
            for (Reservas reservasCollectionOrphanCheckReservas : reservasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Campeonatos (" + campeonatos + ") cannot be destroyed since the Reservas " + reservasCollectionOrphanCheckReservas + " in its reservasCollection field has a non-nullable idcampeonato field.");
            }
            Collection<Arbitros> arbitrosCollectionOrphanCheck = campeonatos.getArbitrosCollection();
            for (Arbitros arbitrosCollectionOrphanCheckArbitros : arbitrosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Campeonatos (" + campeonatos + ") cannot be destroyed since the Arbitros " + arbitrosCollectionOrphanCheckArbitros + " in its arbitrosCollection field has a non-nullable idcampeonato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Patrocinadores> patrocinadoresCollection = campeonatos.getPatrocinadoresCollection();
            for (Patrocinadores patrocinadoresCollectionPatrocinadores : patrocinadoresCollection) {
                patrocinadoresCollectionPatrocinadores.getCampeonatosCollection().remove(campeonatos);
                patrocinadoresCollectionPatrocinadores = em.merge(patrocinadoresCollectionPatrocinadores);
            }
            em.remove(campeonatos);
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

    public List<Campeonatos> findCampeonatosEntities() {
        return findCampeonatosEntities(true, -1, -1);
    }

    public List<Campeonatos> findCampeonatosEntities(int maxResults, int firstResult) {
        return findCampeonatosEntities(false, maxResults, firstResult);
    }

    private List<Campeonatos> findCampeonatosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campeonatos.class));
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

    public Campeonatos findCampeonatos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campeonatos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampeonatosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campeonatos> rt = cq.from(Campeonatos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
