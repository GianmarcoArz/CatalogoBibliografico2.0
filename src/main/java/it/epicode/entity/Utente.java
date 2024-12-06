package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity

@NamedQuery(name = "Trova_tutto_Utente", query = "SELECT u FROM Utente u")
@NamedQuery(name = "Trova_per_NumeroTessera_Utente", query = "SELECT u FROM Utente u WHERE u.numeroTessera = :numeroTessera")
@NamedQuery(name = "Trova_per_Nome_Utente", query = "SELECT u FROM Utente u WHERE u.nome LIKE :nome")
@NamedQuery(name = "Trova_per_Cognome_Utente", query = "SELECT u FROM Utente u WHERE u.cognome LIKE :cognome")
@NamedQuery(name = "Trova_per_DataNascita_Utente", query = "SELECT u FROM Utente u WHERE u.dataNascita = :dataNascita")



public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    private String nome;
    private String cognome;
    private Date dataNascita;

    @Column(name = "numero_tessera")
    private int numeroTessera;


    @OneToMany(mappedBy = "utente")
    @ToString.Exclude
    private List<Prestito> prestiti = new ArrayList<>();
}
