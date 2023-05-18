package com.avla.entrevista.controller;


import com.avla.entrevista.dto.LoginRequest;
import com.avla.entrevista.dto.UsuarioDTO;
import com.avla.entrevista.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(authenticationService.signUpUsuario(usuarioDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.logInUsusario(loginRequest));

    }



}
