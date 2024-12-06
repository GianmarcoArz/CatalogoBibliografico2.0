package it.epicode.dao;

import it.epicode.entity.Libro;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LibroDAO {
    private EntityManager em;

    public void salva(Libro oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }
    public List<Libro> trovaTutti() {
        return em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
    }

    public Libro trovaPerISBN(String isbn) {
        return em.createNamedQuery("Trova_per_ISBN_Libro", Libro.class)
                 .setParameter("isbn", isbn)
                 .getSingleResult();
    }

    public List<Libro> trovaPerAnno(int anno) {
        return em.createNamedQuery("Trova_per_Anno_Libro", Libro.class)
                 .setParameter("anno", anno)
                 .getResultList();
    }

    public List<Libro> trovaPerAutore(String autore) {
        return em.createNamedQuery("Trova_per_Autore_Libro", Libro.class)
                 .setParameter("autore", autore)
                 .getResultList();
    }

    public List<Libro> trovaPerTitolo(String titolo) {
        return em.createNamedQuery("Trova_per_Titolo_Libro", Libro.class)
                 .setParameter("titolo", "%" + titolo + "%")
                 .getResultList();
    }

    public void eliminaPerISBN(String isbn) {
        em.getTransaction().begin();
        Libro libro = trovaPerISBN(isbn);
        if (libro != null) {
            em.remove(libro);
        }
        em.getTransaction().commit();
    }
}