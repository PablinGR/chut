/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DP;

import DP.exceptions.NonexistentEntityException;
import DP.exceptions.PreexistingEntityException;
import DP.exceptions.RollbackFailureException;
import MD.Canchas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MD.Partidos;
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
public class CanchasJpaController implements Serializable {

    public CanchasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Canchas canchas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (canchas.getPropietariosCollection() == null) {
            canchas.setPropietariosCollection(new ArrayList<Propietarios>());
        }
        if (canchas.getPartidosCollection() == null) {
            canchas.setPartidosCollection(new ArrayList<Partidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Partidos idpartido = canchas.getIdpartido();
            if (idpartido != null) {
                idpartido = em.getReference(idpartido.getClass(), idpartido.getIdpartido());
                canchas.setIdpartido(idpartido);
            }
            Collection<Propietarios> attachedPropietariosCollection = new ArrayList<Propietarios>();
            for (Propietarios propietariosCollectionPropietariosToAttach : canchas.getPropietariosCollection()) {
                propietariosCollectionPropietariosToAttach = em.getReference(propietariosCollectionPropietariosToAttach.getClass(), propietariosCollectionPropietariosToAttach.getCedulapropietarios());
                attachedPropietariosCollection.add(propietariosCollectionPropietariosToAttach);
            }
            canchas.setPropietariosCollection(attachedPropietariosCollection);
            Collection<Partidos> attachedPartidosCollection = new ArrayList<Partidos>();
            for (Partidos partidosCollectionPartidosToAttach : canchas.getPartidosCollection()) {
                partidosCollectionPartidosToAttach = em.getReference(partidosCollectionPartidosToAttach.getClass(), partidosCollectionPartidosToAttach.getIdpartido());
                attachedPartidosCollection.add(partidosCollectionPartidosToAttach);
            }
            canchas.setPartidosCollection(attachedPartidosCollection);
            em.persist(canchas);
            if (idpartido != null) {
                idpartido.getCanchasCollection().add(canchas);
                idpartido = em.merge(idpartido);
            }
            for (Propietarios propietariosCollectionPropietarios : canchas.getPropietariosCollection()) {
                propietariosCollectionPropietarios.getCanchasCollection().add(canchas);
                propietariosCollectionPropietarios = em.merge(propietariosCollectionPropietarios);
            }
            for (Partidos partidosCollectionPartidos : canchas.getPartidosCollection()) {
                Canchas oldIdcanchaOfPartidosCollectionPartidos = partidosCollectionPartidos.getIdcancha();
                partidosCollectionPartidos.setIdcancha(canchas);
                partidosCollectionPartidos = em.merge(partidosCollectionPartidos);
                if (oldIdcanchaOfPartidosCollectionPartidos != null) {
                    oldIdcanchaOfPartidosCollectionPartidos.getPartidosCollection().remove(partidosCollectionPartidos);
                    oldIdcanchaOfPartidosCollectionPartidos = em.merge(oldIdcanchaOfPartidosCollectionPartidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCanchas(canchas.getIdcancha()) != null) {
                throw new PreexistingEntityException("Canchas " + canchas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Canchas canchas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Canchas persistentCanchas = em.find(Canchas.class, canchas.getIdcancha());
            Partidos idpartidoOld = persistentCanchas.getIdpartido();
            Partidos idpartidoNew = canchas.getIdpartido();
            Collection<Propietarios> propietariosCollectionOld = persistentCanchas.getPropietariosCollection();
            Collection<Propietarios> propietariosCollectionNew = canchas.getPropietariosCollection();
            Collection<Partidos> partidosCollectionOld = persistentCanchas.getPartidosCollection();
            Collection<Partidos> partidosCollectionNew = canchas.getPartidosCollection();
            if (idpartidoNew != null) {
                idpartidoNew = em.getReference(idpartidoNew.getClass(), idpartidoNew.getIdpartido());
                canchas.setIdpartido(idpartidoNew);
            }
            Collection<Propietarios> attachedPropietariosCollectionNew = new ArrayList<Propietarios>();
            for (Propietarios propietariosCollectionNewPropietariosToAttach : propietariosCollectionNew) {
                propietariosCollectionNewPropietariosToAttach = em.getReference(propietariosCollectionNewPropietariosToAttach.getClass(), propietariosCollectionNewPropietariosToAttach.getCedulapropietarios());
                attachedPropietariosCollectionNew.add(propietariosCollectionNewPropietariosToAttach);
            }
            propietariosCollectionNew = attachedPropietariosCollectionNew;
            canchas.setPropietariosCollection(propietariosCollectionNew);
            Collection<Partidos> attachedPartidosCollectionNew = new ArrayList<Partidos>();
            for (Partidos partidosCollectionNewPartidosToAttach : partidosCollectionNew) {
                partidosCollectionNewPartidosToAttach = em.getReference(partidosCollectionNewPartidosToAttach.getClass(), partidosCollectionNewPartidosToAttach.getIdpartido());
                attachedPartidosCollectionNew.add(partidosCollectionNewPartidosToAttach);
            }
            partidosCollectionNew = attachedPartidosCollectionNew;
            canchas.setPartidosCollection(partidosCollectionNew);
            canchas = em.merge(canchas);
            if (idpartidoOld != null && !idpartidoOld.equals(idpartidoNew)) {
                idpartidoOld.getCanchasCollection().remove(canchas);
                idpartidoOld = em.merge(idpartidoOld);
            }
            if (idpartidoNew != null && !idpartidoNew.equals(idpartidoOld)) {
                idpartidoNew.getCanchasCollection().add(canchas);
                idpartidoNew = em.merge(idpartidoNew);
            }
            for (Propietarios propietariosCollectionOldPropietarios : propietariosCollectionOld) {
                if (!propietariosCollectionNew.contains(propietariosCollectionOldPropietarios)) {
                    propietariosCollectionOldPropietarios.getCanchasCollection().remove(canchas);
                    propietariosCollectionOldPropietarios = em.merge(propietariosCollectionOldPropietarios);
                }
            }
            for (Propietarios propietariosCollectionNewPropietarios : propietariosCollectionNew) {
                if (!propietariosCollectionOld.contains(propietariosCollectionNewPropietarios)) {
                    propietariosCollectionNewPropietarios.getCanchasCollection().add(canchas);
                    propietariosCollectionNewPropietarios = em.merge(propietariosCollectionNewPropietarios);
                }
            }
            for (Partidos partidosCollectionOldPartidos : partidosCollectionOld) {
                if (!partidosCollectionNew.contains(partidosCollectionOldPartidos)) {
                    partidosCollectionOldPartidos.setIdcancha(null);
                    partidosCollectionOldPartidos = em.merge(partidosCollectionOldPartidos);
                }
            }
            for (Partidos partidosCollectionNewPartidos : partidosCollectionNew) {
                if (!partidosCollectionOld.contains(partidosCollectionNewPartidos)) {
                    Canchas oldIdcanchaOfPartidosCollectionNewPartidos = partidosCollectionNewPartidos.getIdcancha();
                    partidosCollectionNewPartidos.setIdcancha(canchas);
                    partidosCollectionNewPartidos = em.merge(partidosCollectionNewPartidos);
                    if (oldIdcanchaOfPartidosCollectionNewPartidos != null && !oldIdcanchaOfPartidosCollectionNewPartidos.equals(canchas)) {
                        oldIdcanchaOfPartidosCollectionNewPartidos.getPartidosCollection().remove(partidosCollectionNewPartidos);
                        oldIdcanchaOfPartidosCollectionNewPartidos = em.merge(oldIdcanchaOfPartidosCollectionNewPartidos);
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
                Integer id = canchas.getIdcancha();
                if (findCanchas(id) == null) {
                    throw new NonexistentEntityException("The canchas with id " + id + " no longer exists.");
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
            Canchas canchas;
            try {
                canchas = em.getReference(Canchas.class, id);
                canchas.getIdcancha();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canchas with id " + id + " no longer exists.", enfe);
            }
            Partidos idpartido = canchas.getIdpartido();
            if (idpartido != null) {
                idpartido.getCanchasCollection().remove(canchas);
                idpartido = em.merge(idpartido);
            }
            Collection<Propietarios> propietariosCollection = canchas.getPropietariosCollection();
            for (Propietarios propietariosCollectionPropietarios : propietariosCollection) {
                propietariosCollectionPropietarios.getCanchasCollection().remove(canchas);
                propietariosCollectionPropietarios = em.merge(propietariosCollectionPropietarios);
            }
            Collection<Partidos> partidosCollection = canchas.getPartidosCollection();
            for (Partidos partidosCollectionPartidos : partidosCollection) {
                partidosCollectionPartidos.setIdcancha(null);
                partidosCollectionPartidos = em.merge(partidosCollectionPartidos);
            }
            em.remove(canchas);
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

    public List<Canchas> findCanchasEntities() {
        return findCanchasEntities(true, -1, -1);
    }

    public List<Canchas> findCanchasEntities(int maxResults, int firstResult) {
        return findCanchasEntities(false, maxResults, firstResult);
    }

    private List<Canchas> findCanchasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Canchas.class));
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

    public Canchas findCanchas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Canchas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanchasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Canchas> rt = cq.from(Canchas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
