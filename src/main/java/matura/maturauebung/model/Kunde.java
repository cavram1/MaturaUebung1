package matura.maturauebung.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kunde")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long kundenID;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String name;

    @Override
    public String toString() {
        return "Kunde{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
