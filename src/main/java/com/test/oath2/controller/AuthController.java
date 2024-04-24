package com.test.oath2.controller;


import com.test.oath2.dao.request.AuthenticationRequest;
import com.test.oath2.dao.request.RegisterRequest;
import com.test.oath2.dao.response.AuthenticationResponse;
import com.test.oath2.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;
  @PostMapping("auth/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
  }

  @PostMapping ("auth/token")
  public ResponseEntity<AuthenticationResponse> getToken(@RequestBody AuthenticationRequest request){
    return  ResponseEntity.ok(authenticationService.getToken(request));
  }

  @PostMapping ("resource")
  public ResponseEntity<?> privateResource(){
    return  ResponseEntity.ok("Secured Resource");
  }

}
