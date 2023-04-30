package matura.maturauebung.repository;

import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Marke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkenRepository extends JpaRepository<Marke, Long> {
}
