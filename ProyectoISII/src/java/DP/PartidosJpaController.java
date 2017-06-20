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
import MD.Equipos;
import MD.Reservas;
import MD.Canchas;
import MD.Partidos;
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
public class PartidosJpaController implements Serializable {

    public PartidosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partidos partidos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (partidos.getCanchasCollection() == null) {
            partidos.setCanchasCollection(new ArrayList<Canchas>());
        }
        if (partidos.getReservasCollection() == null) {
            partidos.setReservasCollection(new ArrayList<Reservas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Equipos idequipo = partidos.getIdequipo();
            if (idequipo != null) {
                idequipo = em.getReference(idequipo.getClass(), idequipo.getIdequipo());
                partidos.setIdequipo(idequipo);
            }
            Reservas idreserva = partidos.getIdreserva();
            if (idreserva != null) {
                idreserva = em.getReference(idreserva.getClass(), idreserva.getIdreserva());
                partidos.setIdreserva(idreserva);
            }
            Canchas idcancha = partidos.getIdcancha();
            if (idcancha != null) {
                idcancha = em.getReference(idcancha.getClass(), idcancha.getIdcancha());
                partidos.setIdcancha(idcancha);
            }
            Collection<Canchas> attachedCanchasCollection = new ArrayList<Canchas>();
            for (Canchas canchasCollectionCanchasToAttach : partidos.getCanchasCollection()) {
                canchasCollectionCanchasToAttach = em.getReference(canchasCollectionCanchasToAttach.getClass(), canchasCollectionCanchasToAttach.getIdcancha());
                attachedCanchasCollection.add(canchasCollectionCanchasToAttach);
            }
            partidos.setCanchasCollection(attachedCanchasCollection);
            Collection<Reservas> attachedReservasCollection = new ArrayList<Reservas>();
            for (Reservas reservasCollectionReservasToAttach : partidos.getReservasCollection()) {
                reservasCollectionReservasToAttach = em.getReference(reservasCollectionReservasToAttach.getClass(), reservasCollectionReservasToAttach.getIdreserva());
                attachedReservasCollection.add(reservasCollectionReservasToAttach);
            }
            partidos.setReservasCollection(attachedReservasCollection);
            em.persist(partidos);
            if (idequipo != null) {
                idequipo.getPartidosCollection().add(partidos);
                idequipo = em.merge(idequipo);
            }
            if (idreserva != null) {
                idreserva.getPartidosCollection().add(partidos);
                idreserva = em.merge(idreserva);
            }
            if (idcancha != null) {
                Partidos oldIdpartidoOfIdcancha = idcancha.getIdpartido();
                if (oldIdpartidoOfIdcancha != null) {
                    oldIdpartidoOfIdcancha.setIdcancha(null);
                    oldIdpartidoOfIdcancha = em.merge(oldIdpartidoOfIdcancha);
                }
                idcancha.setIdpartido(partidos);
                idcancha = em.merge(idcancha);
            }
            for (Canchas canchasCollectionCanchas : partidos.getCanchasCollection()) {
                Partidos oldIdpartidoOfCanchasCollectionCanchas = canchasCollectionCanchas.getIdpartido();
                canchasCollectionCanchas.setIdpartido(partidos);
                canchasCollectionCanchas = em.merge(canchasCollectionCanchas);
                if (oldIdpartidoOfCanchasCollectionCanchas != null) {
                    oldIdpartidoOfCanchasCollectionCanchas.getCanchasCollection().remove(canchasCollectionCanchas);
                    oldIdpartidoOfCanchasCollectionCanchas = em.merge(oldIdpartidoOfCanchasCollectionCanchas);
                }
            }
            for (Reservas reservasCollectionReservas : partidos.getReservasCollection()) {
                Partidos oldIdpartidoOfReservasCollectionReservas = reservasCollectionReservas.getIdpartido();
                reservasCollectionReservas.setIdpartido(partidos);
                reservasCollectionReservas = em.merge(reservasCollectionReservas);
                if (oldIdpartidoOfReservasCollectionReservas != null) {
                    oldIdpartidoOfReservasCollectionReservas.getReservasCollection().remove(reservasCollectionReservas);
                    oldIdpartidoOfReservasCollectionReservas = em.merge(oldIdpartidoOfReservasCollectionReservas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPartidos(partidos.getIdpartido()) != null) {
                throw new PreexistingEntityException("Partidos " + partidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partidos partidos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partidos persistentPartidos = em.find(Partidos.class, partidos.getIdpartido());
            Equipos idequipoOld = persistentPartidos.getIdequipo();
            Equipos idequipoNew = partidos.getIdequipo();
            Reservas idreservaOld = persistentPartidos.getIdreserva();
            Reservas idreservaNew = partidos.getIdreserva();
            Canchas idcanchaOld = persistentPartidos.getIdcancha();
            Canchas idcanchaNew = partidos.getIdcancha();
            Collection<Canchas> canchasCollectionOld = persistentPartidos.getCanchasCollection();
            Collection<Canchas> canchasCollectionNew = partidos.getCanchasCollection();
            Collection<Reservas> reservasCollectionOld = persistentPartidos.getReservasCollection();
            Collection<Reservas> reservasCollectionNew = partidos.getReservasCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservas reservasCollectionOldReservas : reservasCollectionOld) {
                if (!reservasCollectionNew.contains(reservasCollectionOldReservas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservas " + reservasCollectionOldReservas + " since its idpartido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idequipoNew != null) {
                idequipoNew = em.getReference(idequipoNew.getClass(), idequipoNew.getIdequipo());
                partidos.setIdequipo(idequipoNew);
            }
            if (idreservaNew != null) {
                idreservaNew = em.getReference(idreservaNew.getClass(), idreservaNew.getIdreserva());
                partidos.setIdreserva(idreservaNew);
            }
            if (idcanchaNew != null) {
                idcanchaNew = em.getReference(idcanchaNew.getClass(), idcanchaNew.getIdcancha());
                partidos.setIdcancha(idcanchaNew);
            }
            Collection<Canchas> attachedCanchasCollectionNew = new ArrayList<Canchas>();
            for (Canchas canchasCollectionNewCanchasToAttach : canchasCollectionNew) {
                canchasCollectionNewCanchasToAttach = em.getReference(canchasCollectionNewCanchasToAttach.getClass(), canchasCollectionNewCanchasToAttach.getIdcancha());
                attachedCanchasCollectionNew.add(canchasCollectionNewCanchasToAttach);
            }
            canchasCollectionNew = attachedCanchasCollectionNew;
            partidos.setCanchasCollection(canchasCollectionNew);
            Collection<Reservas> attachedReservasCollectionNew = new ArrayList<Reservas>();
            for (Reservas reservasCollectionNewReservasToAttach : reservasCollectionNew) {
                reservasCollectionNewReservasToAttach = em.getReference(reservasCollectionNewReservasToAttach.getClass(), reservasCollectionNewReservasToAttach.getIdreserva());
                attachedReservasCollectionNew.add(reservasCollectionNewReservasToAttach);
            }
            reservasCollectionNew = attachedReservasCollectionNew;
            partidos.setReservasCollection(reservasCollectionNew);
            partidos = em.merge(partidos);
            if (idequipoOld != null && !idequipoOld.equals(idequipoNew)) {
                idequipoOld.getPartidosCollection().remove(partidos);
                idequipoOld = em.merge(idequipoOld);
            }
            if (idequipoNew != null && !idequipoNew.equals(idequipoOld)) {
                idequipoNew.getPartidosCollection().add(partidos);
                idequipoNew = em.merge(idequipoNew);
            }
            if (idreservaOld != null && !idreservaOld.equals(idreservaNew)) {
                idreservaOld.getPartidosCollection().remove(partidos);
                idreservaOld = em.merge(idreservaOld);
            }
            if (idreservaNew != null && !idreservaNew.equals(idreservaOld)) {
                idreservaNew.getPartidosCollection().add(partidos);
                idreservaNew = em.merge(idreservaNew);
            }
            if (idcanchaOld != null && !idcanchaOld.equals(idcanchaNew)) {
                idcanchaOld.setIdpartido(null);
                idcanchaOld = em.merge(idcanchaOld);
            }
            if (idcanchaNew != null && !idcanchaNew.equals(idcanchaOld)) {
                Partidos oldIdpartidoOfIdcancha = idcanchaNew.getIdpartido();
                if (oldIdpartidoOfIdcancha != null) {
                    oldIdpartidoOfIdcancha.setIdcancha(null);
                    oldIdpartidoOfIdcancha = em.merge(oldIdpartidoOfIdcancha);
                }
                idcanchaNew.setIdpartido(partidos);
                idcanchaNew = em.merge(idcanchaNew);
            }
            for (Canchas canchasCollectionOldCanchas : canchasCollectionOld) {
                if (!canchasCollectionNew.contains(canchasCollectionOldCanchas)) {
                    canchasCollectionOldCanchas.setIdpartido(null);
                    canchasCollectionOldCanchas = em.merge(canchasCollectionOldCanchas);
                }
            }
            for (Canchas canchasCollectionNewCanchas : canchasCollectionNew) {
                if (!canchasCollectionOld.contains(canchasCollectionNewCanchas)) {
                    Partidos oldIdpartidoOfCanchasCollectionNewCanchas = canchasCollectionNewCanchas.getIdpartido();
                    canchasCollectionNewCanchas.setIdpartido(partidos);
                    canchasCollectionNewCanchas = em.merge(canchasCollectionNewCanchas);
                    if (oldIdpartidoOfCanchasCollectionNewCanchas != null && !oldIdpartidoOfCanchasCollectionNewCanchas.equals(partidos)) {
                        oldIdpartidoOfCanchasCollectionNewCanchas.getCanchasCollection().remove(canchasCollectionNewCanchas);
                        oldIdpartidoOfCanchasCollectionNewCanchas = em.merge(oldIdpartidoOfCanchasCollectionNewCanchas);
                    }
                }
            }
            for (Reservas reservasCollectionNewReservas : reservasCollectionNew) {
                if (!reservasCollectionOld.contains(reservasCollectionNewReservas)) {
                    Partidos oldIdpartidoOfReservasCollectionNewReservas = reservasCollectionNewReservas.getIdpartido();
                    reservasCollectionNewReservas.setIdpartido(partidos);
                    reservasCollectionNewReservas = em.merge(reservasCollectionNewReservas);
                    if (oldIdpartidoOfReservasCollectionNewReservas != null && !oldIdpartidoOfReservasCollectionNewReservas.equals(partidos)) {
                        oldIdpartidoOfReservasCollectionNewReservas.getReservasCollection().remove(reservasCollectionNewReservas);
                        oldIdpartidoOfReservasCollectionNewReservas = em.merge(oldIdpartidoOfReservasCollectionNewReservas);
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
                Integer id = partidos.getIdpartido();
                if (findPartidos(id) == null) {
                    throw new NonexistentEntityException("The partidos with id " + id + " no longer exists.");
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
            Partidos partidos;
            try {
                partidos = em.getReference(Partidos.class, id);
                partidos.getIdpartido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservas> reservasCollectionOrphanCheck = partidos.getReservasCollection();
            for (Reservas reservasCollectionOrphanCheckReservas : reservasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partidos (" + partidos + ") cannot be destroyed since the Reservas " + reservasCollectionOrphanCheckReservas + " in its reservasCollection field has a non-nullable idpartido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Equipos idequipo = partidos.getIdequipo();
            if (idequipo != null) {
                idequipo.getPartidosCollection().remove(partidos);
                idequipo = em.merge(idequipo);
            }
            Reservas idreserva = partidos.getIdreserva();
            if (idreserva != null) {
                idreserva.getPartidosCollection().remove(partidos);
                idreserva = em.merge(idreserva);
            }
            Canchas idcancha = partidos.getIdcancha();
            if (idcancha != null) {
                idcancha.setIdpartido(null);
                idcancha = em.merge(idcancha);
            }
            Collection<Canchas> canchasCollection = partidos.getCanchasCollection();
            for (Canchas canchasCollectionCanchas : canchasCollection) {
                canchasCollectionCanchas.setIdpartido(null);
                canchasCollectionCanchas = em.merge(canchasCollectionCanchas);
            }
            em.remove(partidos);
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

    public List<Partidos> findPartidosEntities() {
        return findPartidosEntities(true, -1, -1);
    }

    public List<Partidos> findPartidosEntities(int maxResults, int firstResult) {
        return findPartidosEntities(false, maxResults, firstResult);
    }

    private List<Partidos> findPartidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partidos.class));
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

    public Partidos findPartidos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partidos> rt = cq.from(Partidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
