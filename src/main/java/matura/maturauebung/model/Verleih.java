package matura.maturauebung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "verleih")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Verleih {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long verleihID;

    @Column
    private int ladezyklen;

    @Column
    @NonNull
    private LocalDate entliehenVon;

    @Column
    @NonNull
    private LocalDate entliehenRueckgabe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn
    private Modell modell;



}
