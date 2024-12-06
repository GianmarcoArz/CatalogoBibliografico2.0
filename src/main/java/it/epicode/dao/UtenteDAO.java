package it.epicode.dao;

import it.epicode.entity.Utente;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UtenteDAO {
    private EntityManager em;

    public void salva(Utente oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Utente trovaPerId(Long id) {
        return em.find(Utente.class, id);
    }

    public List<Utente> trovaTutti() {
        return em.createNamedQuery("Trova_tutto_Utente", Utente.class).getResultList();
    }

    public void aggiorna(Utente oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void elimina(Utente oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }
}