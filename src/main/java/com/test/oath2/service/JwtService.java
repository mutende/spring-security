package com.test.oath2.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {

  public String extractClientId(String token){
    return extractClaim(token, Claims::getSubject);
  }


  public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
    final Claims claims = extractAllClaims(token);
    log.info("Claims {}",claims.toString());
    return claimResolver.apply(claims);
  }
  private static final String SECRET_KEY = "5ad43e3b3630932dd9c781999780a5f85396dab1b8794200d0e74f120d7b9aef";
  private Claims extractAllClaims(String token){
    return (Claims) Jwts.parser().verifyWith(getSignInKey()).build().parse(token).getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String generateToken( Map<String, Object> claims, UserDetails userDetails){
   return Jwts.builder().subject(userDetails.getUsername()).claims(claims)
       .issuedAt(Date.from(Instant.now()))
       .expiration(Date.from(Instant.now().plus(Duration.ofHours(1))))
       .signWith(getSignInKey(), Jwts.SIG.HS256)
       .compact();
  }

  public Date extractExpirationTime(String token){
    return extractClaim(token, Claims::getExpiration);
  }
  public boolean isTokenValid(String token, UserDetails userDetails){
    String clientId = extractClientId(token);
    Date expirationTime = extractExpirationTime(token);
    log.info("Expiration Time: {}", expirationTime);
    log.info("User Details: {}", userDetails);
    boolean isNotExpired = Date.from(Instant.now()).before(expirationTime);
    return clientId.equals(userDetails.getUsername()) && isNotExpired;
  }


}
