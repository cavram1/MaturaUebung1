package matura.maturauebung.repository;

import matura.maturauebung.model.Admin;
import matura.maturauebung.model.Modell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModellRepository extends JpaRepository<Modell, Long> {


}
