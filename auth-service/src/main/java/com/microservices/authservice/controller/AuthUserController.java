package com.microservices.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.authservice.dto.AuthUserDto;
import com.microservices.authservice.dto.TokenDto;
import com.microservices.authservice.entity.AuthUser;
import com.microservices.authservice.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
  @Autowired
  AuthUserService authUserService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto) {
    TokenDto tokenDto = authUserService.login(dto);
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/token/validate")
  public ResponseEntity<TokenDto> validate(@RequestHeader("Authorization") String token) {
    TokenDto tokenDto = authUserService.validate(token.replace("Bearer ", ""));
    if (tokenDto == null)
      return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping("/create")
  public ResponseEntity<AuthUser> create(@RequestBody AuthUserDto dto) {
    AuthUser authUser = authUserService.save(dto);
    if (authUser == null)
      return ResponseEntity.badRequest().build();

    return ResponseEntity.ok(authUser);
  }
}
