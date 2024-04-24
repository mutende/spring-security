package com.test.oath2.dao.request;

import lombok.Data;

@Data
public class RegisterRequest {

  private String clientId;
  private String clientSecret;
  private Integer partnerId;
  private short isActive;

}
