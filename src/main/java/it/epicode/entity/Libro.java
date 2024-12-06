package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "libri")
@NamedQuery(name = "Trova_per_ISBN_Libro", query = "SELECT l FROM Libro l WHERE l.codiceISBN = :isbn")
@NamedQuery(name = "Trova_per_Anno_Libro", query = "SELECT l FROM Libro l WHERE l.annoPubblicazione = :anno")
@NamedQuery(name = "Trova_per_Autore_Libro", query = "SELECT l FROM Libro l WHERE l.autore = :autore")
@NamedQuery(name = "Trova_per_Titolo_Libro", query = "SELECT l FROM Libro l WHERE l.titolo LIKE :titolo")
public class Libro extends Catalogo {

    private String autore;
    private String genere;


}
