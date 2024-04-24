package com.test.oath2.repository;

import com.test.oath2.entity.PartnerToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerTokenRepository extends JpaRepository<PartnerToken, Long> {
  Optional<PartnerToken> findByPartnerId(Integer partnerId);
}
