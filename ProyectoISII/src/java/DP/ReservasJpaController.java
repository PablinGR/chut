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
import MD.Campeonatos;
import MD.Horarios;
import MD.Partidos;
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
public class ReservasJpaController implements Serializable {

    public ReservasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservas reservas) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (reservas.getPartidosCollection() == null) {
            reservas.setPartidosCollection(new ArrayList<Partidos>());
        }
        List<String> illegalOrphanMessages = null;
        Partidos idpartidoOrphanCheck = reservas.getIdpartido();
        if (idpartidoOrphanCheck != null) {
            Reservas oldIdreservaOfIdpartido = idpartidoOrphanCheck.getIdreserva();
            if (oldIdreservaOfIdpartido != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Partidos " + idpartidoOrphanCheck + " already has an item of type Reservas whose idpartido column cannot be null. Please make another selection for the idpartido field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Campeonatos idcampeonato = reservas.getIdcampeonato();
            if (idcampeonato != null) {
                idcampeonato = em.getReference(idcampeonato.getClass(), idcampeonato.getIdcampeonato());
                reservas.setIdcampeonato(idcampeonato);
            }
            Horarios idhorario = reservas.getIdhorario();
            if (idhorario != null) {
                idhorario = em.getReference(idhorario.getClass(), idhorario.getIdhorario());
                reservas.setIdhorario(idhorario);
            }
            Partidos idpartido = reservas.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                reservas.setIdpartido(idpartido);
            }
            Collection<Partidos> attachedPartidosCollection = new ArrayList<Partidos>();
            for (Partidos partidosCollectionPartidosToAttach : reservas.getPartidosCollection()) {
                partidosCollectionPartidosToAttach = em.getReference(partidosCollectionPartidosToAttach.getClass(), partidosCollectionPartidosToAttach.getIdpartido());
                attachedPartidosCollection.add(partidosCollectionPartidosToAttach);
            }
            reservas.setPartidosCollection(attachedPartidosCollection);
            em.persist(reservas);
            if (idcampeonato != null) {
                idcampeonato.getReservasCollection().add(reservas);
                idcampeonato = em.merge(idcampeonato);
            }
            if (idhorario != null) {
                idhorario.getReservasCollection().add(reservas);
                idhorario = em.merge(idhorario);
            }
            if (idpartido != null) {
                idpartido.setIdreserva(reservas);
                idpartido = em.merge(idpartido);
            }
            for (Partidos partidosCollectionPartidos : reservas.getPartidosCollection()) {
                Reservas oldIdreservaOfPartidosCollectionPartidos = partidosCollectionPartidos.getIdreserva();
                partidosCollectionPartidos.setIdreserva(reservas);
                partidosCollectionPartidos = em.merge(partidosCollectionPartidos);
                if (oldIdreservaOfPartidosCollectionPartidos != null) {
                    oldIdreservaOfPartidosCollectionPartidos.getPartidosCollection().remove(partidosCollectionPartidos);
                    oldIdreservaOfPartidosCollectionPartidos = em.merge(oldIdreservaOfPartidosCollectionPartidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findReservas(reservas.getIdreserva()) != null) {
                throw new PreexistingEntityException("Reservas " + reservas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservas reservas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reservas persistentReservas = em.find(Reservas.class, reservas.getIdreserva());
            Campeonatos idcampeonatoOld = persistentReservas.getIdcampeonato();
            Campeonatos idcampeonatoNew = reservas.getIdcampeonato();
            Horarios idhorarioOld = persistentReservas.getIdhorario();
            Horarios idhorarioNew = reservas.getIdhorario();
            Partidos idpartidoOld = persistentReservas.getIdpartido();
            Partidos idpartidoNew = reservas.getIdpartido();
            Collection<Partidos> partidosCollectionOld = persistentReservas.getPartidosCollection();
            Collection<Partidos> partidosCollectionNew = reservas.getPartidosCollection();
            List<String> illegalOrphanMessages = null;
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                Reservas oldIdreservaOfIdpartido = idpartidoNew.getIdreserva();
                if (oldIdreservaOfIdpartido != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Partidos " + idpartidoNew + " already has an item of type Reservas whose idpartido column cannot be null. Please make another selection for the idpartido field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcampeonatoNew != null) {
                idcampeonatoNew = em.getReference(idcampeonatoNew.getClass(), idcampeonatoNew.getIdcampeonato());
                reservas.setIdcampeonato(idcampeonatoNew);
            }
            if (idhorarioNew != null) {
                idhorarioNew = em.getReference(idhorarioNew.getClass(), idhorarioNew.getIdhorario());
                reservas.setIdhorario(idhorarioNew);
            }
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                reservas.setIdpartido(idpartidoNew);
            }
            Collection<Partidos> attachedPartidosCollectionNew = new ArrayList<Partidos>();
            for (Partidos partidosCollectionNewPartidosToAttach : partidosCollectionNew) {
                partidosCollectionNewPartidosToAttach = em.getReference(partidosCollectionNewPartidosToAttach.getClass(), partidosCollectionNewPartidosToAttach.getIdpartido());
                attachedPartidosCollectionNew.add(partidosCollectionNewPartidosToAttach);
            }
            partidosCollectionNew = attachedPartidosCollectionNew;
            reservas.setPartidosCollection(partidosCollectionNew);
            reservas = em.merge(reservas);
            if (idcampeonatoOld != null && !idcampeonatoOld.equals(idcampeonatoNew)) {
                idcampeonatoOld.getReservasCollection().remove(reservas);
                idcampeonatoOld = em.merge(idcampeonatoOld);
            }
            if (idcampeonatoNew != null && !idcampeonatoNew.equals(idcampeonatoOld)) {
                idcampeonatoNew.getReservasCollection().add(reservas);
                idcampeonatoNew = em.merge(idcampeonatoNew);
            }
            if (idhorarioOld != null && !idhorarioOld.equals(idhorarioNew)) {
                idhorarioOld.getReservasCollection().remove(reservas);
                idhorarioOld = em.merge(idhorarioOld);
            }
            if (idhorarioNew != null && !idhorarioNew.equals(idhorarioOld)) {
                idhorarioNew.getReservasCollection().add(reservas);
                idhorarioNew = em.merge(idhorarioNew);
            }
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.setIdreserva(null);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.setIdreserva(reservas);
                idpartidoNew = em.merge(idpartidoNew);
            }
            for (Partidos partidosCollectionOldPartidos : partidosCollectionOld) {
                if (!partidosCollectionNew.contains(partidosCollectionOldPartidos)) {
                    partidosCollectionOldPartidos.setIdreserva(null);
                    partidosCollectionOldPartidos = em.merge(partidosCollectionOldPartidos);
                }
            }
            for (Partidos partidosCollectionNewPartidos : partidosCollectionNew) {
                if (!partidosCollectionOld.contains(partidosCollectionNewPartidos)) {
                    Reservas oldIdreservaOfPartidosCollectionNewPartidos = partidosCollectionNewPartidos.getIdreserva();
                    partidosCollectionNewPartidos.setIdreserva(reservas);
                    partidosCollectionNewPartidos = em.merge(partidosCollectionNewPartidos);
                    if (oldIdreservaOfPartidosCollectionNewPartidos != null && !oldIdreservaOfPartidosCollectionNewPartidos.equals(reservas)) {
                        oldIdreservaOfPartidosCollectionNewPartidos.getPartidosCollection().remove(partidosCollectionNewPartidos);
                        oldIdreservaOfPartidosCollectionNewPartidos = em.merge(oldIdreservaOfPartidosCollectionNewPartidos);
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
                Integer id = reservas.getIdreserva();
                if (findReservas(id) == null) {
                    throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reservas reservas;
            try {
                reservas = em.getReference(Reservas.class, id);
                reservas.getIdreserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservas with id " + id + " no longer exists.", enfe);
            }
            Campeonatos idcampeonato = reservas.getIdcampeonato();
            if (idcampeonato != null) {
                idcampeonato.getReservasCollection().remove(reservas);
                idcampeonato = em.merge(idcampeonato);
            }
            Horarios idhorario = reservas.getIdhorario();
            if (idhorario != null) {
                idhorario.getReservasCollection().remove(reservas);
                idhorario = em.merge(idhorario);
            }
            Partidos idpartido = reservas.getIdpartido();
            if (idpartido != null) {
                idpartido.setIdreserva(null);
                idpartido = em.merge(idpartido);
            }
            Collection<Partidos> partidosCollection = reservas.getPartidosCollection();
            for (Partidos partidosCollectionPartidos : partidosCollection) {
                partidosCollectionPartidos.setIdreserva(null);
                partidosCollectionPartidos = em.merge(partidosCollectionPartidos);
            }
            em.remove(reservas);
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

    public List<Reservas> findReservasEntities() {
        return findReservasEntities(true, -1, -1);
    }

    public List<Reservas> findReservasEntities(int maxResults, int firstResult) {
        return findReservasEntities(false, maxResults, firstResult);
    }

    private List<Reservas> findReservasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservas.class));
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

    public Reservas findReservas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservas.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservas> rt = cq.from(Reservas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
