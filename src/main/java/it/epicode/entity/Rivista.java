package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "riviste")
@NamedQuery(name = "Trova_per_Anno_Rivista", query = "SELECT r FROM Rivista r WHERE r.annoPubblicazione = :anno")
@NamedQuery(name = "Trova_per_Titolo_Rivista", query = "SELECT r FROM Rivista r WHERE r.titolo LIKE :titolo")
@NamedQuery(name = "Trova_per_ISBN_Rivista", query = "SELECT r FROM Rivista r WHERE r.codiceISBN = :isbn")
@NamedQuery(name = "Trova_tutto_Rivista", query = "SELECT r FROM Rivista r")

public class Rivista extends Catalogo {

    @Column(name = "periodicita")
    @Enumerated(EnumType.STRING)
    private periodicita periodicita;

}
