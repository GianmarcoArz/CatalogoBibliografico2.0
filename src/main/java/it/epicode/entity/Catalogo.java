package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "Trova_per_ISBN_Catalogo", query = "SELECT c FROM Catalogo c WHERE c.codiceISBN = :isbn")
@NamedQuery(name = "Trova_per_Anno_Catalogo", query = "SELECT c FROM Catalogo c WHERE c.annoPubblicazione = :anno")
@NamedQuery(name = "Trova_per_Titolo_Catalogo", query = "SELECT c FROM Catalogo c WHERE c.titolo LIKE :titolo")
@Table(name = "catalogo")


public abstract class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String codiceISBN;

    private String titolo;
    private int annoPubblicazione;
    private int numeroPagine;

}
