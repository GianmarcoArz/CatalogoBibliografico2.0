package it.epicode.dao;

import it.epicode.entity.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PrestitoDAO {
    private EntityManager em;

    public void salva(Prestito oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public Prestito trovaPerId(Long id) {
        return em.find(Prestito.class, id);
    }

    public List<Prestito> trovaTutti() {
        return em.createNamedQuery("Trova_tutto_Prestito", Prestito.class).getResultList();
    }

    public void aggiorna(Prestito oggetto) {
        em.getTransaction().begin();
        em.merge(oggetto);
        em.getTransaction().commit();
    }

    public void elimina(Prestito oggetto) {
        em.getTransaction().begin();
        em.remove(oggetto);
        em.getTransaction().commit();
    }

    public List<Prestito> trovaPerNumeroTessera(int numeroTessera) {
        return em.createNamedQuery("Trova_per_NumeroTessera_Prestito", Prestito.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
    }

    public List<Prestito> trovaPrestitiScaduti() {
        return em.createNamedQuery("Trova_Prestiti_Scaduti", Prestito.class).getResultList();
    }
}