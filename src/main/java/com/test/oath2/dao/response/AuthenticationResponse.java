package com.test.oath2.dao.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthenticationResponse {
  private String accessToken;
  private String tokenType;
  private Instant expiresIn;

}
