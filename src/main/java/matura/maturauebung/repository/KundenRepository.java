package matura.maturauebung.repository;

import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundenRepository extends JpaRepository<Kunde, Long> {

    Kunde findByName(String name);
}
