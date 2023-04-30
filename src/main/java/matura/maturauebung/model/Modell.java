package matura.maturauebung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "modell")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Modell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long modelID;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private float tagesSatzt;

    @OneToMany(mappedBy = "modell", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Verleih> verleihs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn
    private Marke marke;

    @Override
    public String toString() {
        return "Modell{" +
                "name='" + name + '\'' +
                ", tagesSatzt=" + tagesSatzt +
                '}';
    }
}
