package com.avla.entrevista.controller;


import com.avla.entrevista.dto.UsuarioDTO;
import com.avla.entrevista.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    private final AuthenticationService authenticationService;

    public UsuarioController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<?> infoUsuario(@RequestParam String email){
        System.out.println("email: " + email);
        return ResponseEntity.ok(authenticationService.infoUsuario(email));
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody UsuarioDTO usuarioDTO){
        boolean b = authenticationService.updateUsuario(usuarioDTO);
        if (b)
            return ResponseEntity.ok("usuario actualizado correctamente");
        return new ResponseEntity<>("error al actualizar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody String email){
        return ResponseEntity.ok(authenticationService.deleteUsuario(email));
    }
}
