package com.test.oath2.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partner_token")
public class PartnerToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;
  @Column(name = "partner_id")
  Integer partnerId;
  @Column(name = "access_token")
  String accessToken;
  @Column(name = "expires_at")
  Timestamp expiresAt;
  @Column(name = "createdAt")
  Timestamp createdAt;

}
