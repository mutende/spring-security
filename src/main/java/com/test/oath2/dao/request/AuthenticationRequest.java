package com.test.oath2.dao.request;


import lombok.Data;

@Data
public class AuthenticationRequest {

  private String clientId;
  private String clientSecret;
}
