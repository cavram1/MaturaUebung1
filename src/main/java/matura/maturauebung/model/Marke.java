package matura.maturauebung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "marke")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Marke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long markeID;

    @Column
    @NonNull
    private String name;

    @OneToMany(mappedBy = "marke", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Modell> modellSet;

    @Override
    public String toString() {
        return ", name='" + name + '\'' +
                '}';
    }
}
