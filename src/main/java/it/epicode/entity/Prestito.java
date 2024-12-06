package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = "Trova_Prestiti_Scaduti", query = "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL OR p.dataRestituzionePrevista < CURRENT_DATE"),
        @NamedQuery(name = "Trova_per_NumeroTessera_Prestito", query = "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera")
})
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elemento_catalogo_isbn")
    private Catalogo elementoPrestato;

    private Date dataInizioPrestito;
    private Date dataRestituzionePrevista;
    private Date dataRestituzioneEffettiva;

}
