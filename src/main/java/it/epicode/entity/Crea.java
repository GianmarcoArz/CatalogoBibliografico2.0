package it.epicode.entity;

import com.github.javafaker.Faker;
import it.epicode.dao.LibroDAO;
import it.epicode.dao.PrestitoDAO;
import it.epicode.dao.RivistaDAO;
import it.epicode.dao.UtenteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Crea {
    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("it"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();


        LibroDAO libroDAO = new LibroDAO(em);
        RivistaDAO rivistaDAO = new RivistaDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        // Creazione libri
        for (int i = 0; i < 20; i++) {
            Libro l = new Libro();
            l.setCodiceISBN(faker.code().isbn13());
            l.setTitolo(faker.book().title());
            l.setAutore(faker.book().author());
            l.setAnnoPubblicazione(faker.number().numberBetween(1900, 2022));
            l.setNumeroPagine(faker.number().numberBetween(50, 1000));
            l.setGenere(faker.book().genre());
            libroDAO.salva(l);
        }

        // Creazione riviste
        for (int i = 0; i < 20; i++) {
            Rivista r = new Rivista();
            r.setCodiceISBN(faker.code().isbn13());
            r.setTitolo(faker.book().title());
            r.setAnnoPubblicazione(faker.number().numberBetween(1900, 2022));
            r.setNumeroPagine(faker.number().numberBetween(50, 1000));
            r.setPeriodicita(faker.options().option(periodicita.class));
            rivistaDAO.salva(r);
        }

        // Creazione utenti
        for (int i = 0; i < 5; i++) {
            Utente u = new Utente();
            u.setNome(faker.name().firstName());
            u.setCognome(faker.name().lastName());
            u.setDataNascita(faker.date().birthday());
            u.setNumeroTessera(faker.number().numberBetween(1000, 9999));
            utenteDAO.salva(u);
        }

        //creo un libro
        Libro l = new Libro();
        l.setCodiceISBN("1234567890123");
        l.setTitolo("Il signore degli anelli");
        l.setAutore("J.R.R. Tolkien");
        l.setAnnoPubblicazione(1954);
        l.setNumeroPagine(1200);
        l.setGenere("Fantasy");
        libroDAO.salva(l);


        // Creazione prestiti
        List<Libro> libri = libroDAO.trovaTutti();

//crea un prestito scaduto
        Prestito p = new Prestito();
        p.setUtente(utenteDAO.trovaPerId(1L));
        p.setElementoPrestato(libroDAO.trovaPerISBN("1234567890123"));
        p.setDataInizioPrestito(new Date());
        p.setDataRestituzionePrevista(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
        p.setDataRestituzioneEffettiva(null);
        prestitoDAO.salva(p);

        //genero tanti prestiti
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Prestito prestito = new Prestito();
            prestito.setUtente(utenteDAO.trovaPerId(faker.number().numberBetween(1L, 5L)));
            prestito.setElementoPrestato(libri.get(faker.number().numberBetween(0, libri.size())));
            Date dataInizio = new Date();
            prestito.setDataInizioPrestito(dataInizio);
            int giorniAggiuntivi = 15 + random.nextInt(31);
            prestito.setDataRestituzionePrevista(new Date(dataInizio.getTime() + giorniAggiuntivi * 24L * 60 * 60 * 1000));
            int giorniEffettivi = 15 + random.nextInt(31);
            prestito.setDataRestituzioneEffettiva(new Date(dataInizio.getTime() + giorniEffettivi * 24L * 60 * 60 * 1000));
            prestitoDAO.salva(prestito);
        }
        em.close();
    }

}
