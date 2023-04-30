package matura.maturauebung.repository;

import matura.maturauebung.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findFirstByEmailAndPassword(String e, String p);
    Admin findFirstByTokenAndExpireingTimeGreaterThan(String token, long exp);
}
