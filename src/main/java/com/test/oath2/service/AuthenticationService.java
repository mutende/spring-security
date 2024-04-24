package com.test.oath2.service;

import com.test.oath2.dao.request.AuthenticationRequest;
import com.test.oath2.dao.request.RegisterRequest;
import com.test.oath2.dao.response.AuthenticationResponse;
import com.test.oath2.entity.PartnerAuth;
import com.test.oath2.entity.PartnerToken;
import com.test.oath2.repository.PartnerAuthRepository;
import com.test.oath2.repository.PartnerTokenRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final PartnerAuthRepository partnerAuthRepository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final PartnerTokenRepository partnerTokenRepository;

  public AuthenticationResponse getToken(AuthenticationRequest request){
  authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getClientId(), request.getClientSecret())
  );

  log.info("Client Authorised");
  PartnerAuth partner =
      partnerAuthRepository.findByClientId(request.getClientId()).orElseThrow(() ->
          new UsernameNotFoundException("User not found"));

  String token = jwtService.generateToken(null, partner);

  Instant expiryTime = jwtService.extractExpirationTime(token).toInstant();
  updatePartnerToken(partner.getPartnerId(), token, expiryTime);
    return AuthenticationResponse.builder()
      .accessToken(token)
      .tokenType("Bearer")
      .expiresIn(expiryTime)
      .build();

  }

  public String register(RegisterRequest request){
    var partner = PartnerAuth.builder()
        .partnerId(request.getPartnerId())
        .clientId(request.getClientId())
        .clientSecret(encoder.encode(request.getClientSecret()))
        .isActive(request.getIsActive())
        .build();

    partnerAuthRepository.save(partner);
    return "Created Successfully";
  }

  private void updatePartnerToken(Integer partnerId, String token, Instant expiryTime){
    Optional<PartnerToken> partnerToken = partnerTokenRepository.findByPartnerId(partnerId);
    if(partnerToken.isPresent()){
      log.info("Updating partner {} token for}", partnerId);
      var updatedPartnerToken  = partnerToken.get();
      updatedPartnerToken.setAccessToken(token);
      updatedPartnerToken.setExpiresAt(Timestamp.from(expiryTime));
      updatedPartnerToken.setCreatedAt(Timestamp.from(Instant.now()));
      partnerTokenRepository.save(updatedPartnerToken);
    }else {
      log.info("Creating partner {} token", partnerId);
      var newPartnerToken = PartnerToken.builder()
          .accessToken(token)
          .partnerId(partnerId)
          .createdAt(Timestamp.from(Instant.now()))
          .expiresAt(Timestamp.from(expiryTime))
          .build();
      partnerTokenRepository.save(newPartnerToken);
    }
  }

}
