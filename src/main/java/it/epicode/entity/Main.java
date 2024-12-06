package it.epicode.entity;

import it.epicode.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    private static EntityManager em = emf.createEntityManager();
    private static LibroDAO libroDAO = new LibroDAO(em);
    private static RivistaDAO rivistaDAO = new RivistaDAO(em);
    private static PrestitoDAO prestitoDAO = new PrestitoDAO(em);
    private static UtenteDAO utenteDAO = new UtenteDAO(em);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Aggiungi elemento del catalogo");
            System.out.println("2. Rimuovi elemento dal catalogo (codice ISBN)");
            System.out.println("3. Ricerca per ISBN");
            System.out.println("4. Ricerca per anno di pubblicazione");
            System.out.println("5. Ricerca per autore");
            System.out.println("6. Ricerca per titolo o parte di esso");
            System.out.println("7. Ricerca elementi in prestito (numero tessera utente)");
            System.out.println("8. Ricerca prestiti scaduti e non restituiti");
            System.out.println("9. Esci");
            System.out.print("Seleziona un'opzione: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma la newline

            switch (choice) {
                case 1:
                    aggiungiElemento(scanner);
                    break;
                case 2:
                    rimuoviElemento(scanner);
                    break;
                case 3:
                    ricercaPerISBN(scanner);
                    break;
                case 4:
                    ricercaPerAnno(scanner);
                    break;
                case 5:
                    ricercaPerAutore(scanner);
                    break;
                case 6:
                    ricercaPerTitolo(scanner);
                    break;
                case 7:
                    ricercaElementiInPrestito(scanner);
                    break;
                case 8:
                    ricercaPrestitiScaduti();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }

    private static void aggiungiElemento(Scanner scanner) {
        System.out.println("Aggiungi un libro o una rivista? (l/r): ");
        String tipo = scanner.nextLine();
        if (tipo.equalsIgnoreCase("l")) {
            Libro libro = new Libro();
            System.out.print("Titolo: ");
            libro.setTitolo(scanner.nextLine());
            System.out.print("Autore: ");
            libro.setAutore(scanner.nextLine());
            System.out.print("Codice ISBN: ");
            libro.setCodiceISBN(scanner.nextLine());
            System.out.print("Genere: ");
            libro.setGenere(scanner.nextLine());
            System.out.print("Anno di pubblicazione: ");
            libro.setAnnoPubblicazione(scanner.nextInt());
            System.out.print("Numero di pagine: ");
            libro.setNumeroPagine(scanner.nextInt());
            scanner.nextLine(); // Consuma la newline
            libroDAO.salva(libro);
        } else if (tipo.equalsIgnoreCase("r")) {
            Rivista rivista = new Rivista();
            System.out.print("Titolo: ");
            rivista.setTitolo(scanner.nextLine());
            System.out.print("Codice ISBN: ");
            rivista.setCodiceISBN(scanner.nextLine());
            System.out.print("Anno di pubblicazione: ");
            rivista.setAnnoPubblicazione(scanner.nextInt());
            System.out.print("Numero di pagine: ");
            rivista.setNumeroPagine(scanner.nextInt());
            scanner.nextLine(); // Consuma la newline
            rivistaDAO.salva(rivista);
        } else {
            System.out.println("Tipo non valido.");
        }
    }

    private static void rimuoviElemento(Scanner scanner) {
        System.out.print("Inserisci il codice ISBN: ");
        String isbn = scanner.nextLine();
        libroDAO.eliminaPerISBN(isbn);
        rivistaDAO.eliminaPerISBN(isbn);
    }

    private static void ricercaPerISBN(Scanner scanner) {
        System.out.print("Inserisci il codice ISBN: ");
        String isbn = scanner.nextLine();
        try {
            Libro libro = libroDAO.trovaPerISBN(isbn);
            System.out.println("Libro trovato: " + libro);
        } catch (Exception e) {
            System.out.println("Nessun libro trovato con questo ISBN.");
        }
        try {
            Rivista rivista = rivistaDAO.trovaPerISBN(isbn);
            System.out.println("Rivista trovata: " + rivista);
        } catch (Exception e) {
            System.out.println("Nessuna rivista trovata con questo ISBN.");
        }
    }

    private static void ricercaPerAnno(Scanner scanner) {
        System.out.print("Inserisci l'anno di pubblicazione: ");
        int anno = scanner.nextInt();
        scanner.nextLine(); // Consuma la newline
        List<Libro> libri = libroDAO.trovaPerAnno(anno);
        List<Rivista> riviste = rivistaDAO.trovaPerAnno(anno);
        System.out.println("Libri trovati: " + libri);
        System.out.println("Riviste trovate: " + riviste);
    }

    private static void ricercaPerAutore(Scanner scanner) {
        System.out.print("Inserisci l'autore: ");
        String autore = scanner.nextLine();
        List<Libro> libri = libroDAO.trovaPerAutore(autore);
        System.out.println("Libri trovati: " + libri);
    }

    private static void ricercaPerTitolo(Scanner scanner) {
        System.out.print("Inserisci il titolo o parte di esso: ");
        String titolo = scanner.nextLine();
        List<Libro> libri = libroDAO.trovaPerTitolo(titolo);
        List<Rivista> riviste = rivistaDAO.trovaPerTitolo(titolo);
        System.out.println("Libri trovati: " + libri);
        System.out.println("Riviste trovate: " + riviste);
    }

    private static void ricercaElementiInPrestito(Scanner scanner) {
        System.out.print("Inserisci il numero di tessera utente: ");
        int numeroTessera = scanner.nextInt();
        List<Prestito> prestiti = prestitoDAO.trovaPerNumeroTessera(numeroTessera);
        System.out.println("Elementi in prestito trovati: " + prestiti);
    }

    private static void ricercaPrestitiScaduti() {
        List<Prestito> prestitiScaduti = prestitoDAO.trovaPrestitiScaduti();
        System.out.println("Prestiti scaduti e non restituiti: " + prestitiScaduti);
    }
}