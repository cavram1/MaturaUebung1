package matura.maturauebung.repository;

import lombok.NonNull;
import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Modell;
import matura.maturauebung.model.Verleih;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VerleihRepository extends JpaRepository<Verleih, Long> {

    @Query("SELECT m.name, m.marke FROM Verleih v LEFT OUTER JOIN Modell m on v.modell.modelID = m.modelID" +
            " WHERE  (?1 < v.entliehenVon AND (?2 < v.entliehenRueckgabe AND ?2 < v.entliehenVon))" +
            " OR (?1 > v.entliehenRueckgabe AND ?2 > v.entliehenRueckgabe )" +
            " OR v.entliehenVon IS NULL ")
    List<Modell> getFreeModells(LocalDate from, LocalDate to);

    List<Verleih> findAllByEntliehenRueckgabeIsGreaterThan(LocalDate time);


}
