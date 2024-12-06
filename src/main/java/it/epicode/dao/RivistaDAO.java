package it.epicode.dao;

import it.epicode.entity.Rivista;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RivistaDAO {
    private EntityManager em;

    public void salva(Rivista oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Rivista trovaPerISBN(String isbn) {
        return em.createNamedQuery("Trova_per_ISBN_Rivista", Rivista.class)
                 .setParameter("isbn", isbn)
                 .getSingleResult();
    }

    public List<Rivista> trovaPerAnno(int anno) {
        return em.createNamedQuery("Trova_per_Anno_Rivista", Rivista.class)
                 .setParameter("anno", anno)
                 .getResultList();
    }

    public List<Rivista> trovaPerTitolo(String titolo) {
        return em.createNamedQuery("Trova_per_Titolo_Rivista", Rivista.class)
                 .setParameter("titolo", "%" + titolo + "%")
                 .getResultList();
    }

    public void eliminaPerISBN(String isbn) {
        em.getTransaction().begin();
        Rivista rivista = trovaPerISBN(isbn);
        if (rivista != null) {
            em.remove(rivista);
        }
        em.getTransaction().commit();
    }
}