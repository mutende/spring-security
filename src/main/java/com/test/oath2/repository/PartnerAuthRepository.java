package com.test.oath2.repository;

import com.test.oath2.entity.PartnerAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerAuthRepository extends JpaRepository<PartnerAuth, Long> {
  Optional<PartnerAuth> findByClientId(String clientId);
}
