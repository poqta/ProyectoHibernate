
package ni.edu.uam.proyectohibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ni.edu.uam.proyectohibernate.database.JPAUtil;
import ni.edu.uam.proyectohibernate.model.Categoria;

import java.util.List;

public class CategoriaDAO {

    public void guardar(Categoria categoria) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(categoria);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    public List<Categoria> listar() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Categoria c ORDER BY c.id",
                    Categoria.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public boolean modificar(Categoria categoria) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Categoria categoriaBD = em.find(
                    Categoria.class, categoria.getId()
            );

            if (categoriaBD == null) {
                tx.rollback();
                return false;
            }

            categoriaBD.setNombre(categoria.getNombre());
            categoriaBD.setDescripcion(categoria.getDescripcion());

            tx.commit();
            return true;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    public boolean eliminar(long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Categoria categoriaBD = em.find(Categoria.class, id);

            if (categoriaBD == null) {
                tx.rollback();
                return false;
            }

            Long cantidadLibros = em.createQuery(
                    "SELECT COUNT(l) FROM Libro l WHERE l.categoria.id = :id",
                    Long.class
            ).setParameter("id", id).getSingleResult();

            if (cantidadLibros > 0) {
                tx.rollback();
                return false;
            }

            em.remove(categoriaBD);
            tx.commit();
            return true;

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

}
