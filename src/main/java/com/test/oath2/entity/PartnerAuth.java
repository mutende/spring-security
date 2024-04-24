package com.test.oath2.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patner_auth")
public class PartnerAuth implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  @Column(name = "client_id")
  String clientId;

  @Column(name = "partner_id")
  Integer partnerId;

  @Column(name = "client_secret")
  String clientSecret;
  @Column(name = "is_active", columnDefinition = "tinyint default 1")
  short isActive;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return clientSecret;
  }

  @Override
  public String getUsername() {
    return clientId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActive == 1;
  }
}
