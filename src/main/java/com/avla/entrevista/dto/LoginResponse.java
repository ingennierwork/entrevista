package com.avla.entrevista.dto;

import com.avla.entrevista.entity.usuario.Telefono;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String id;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<Telefono> phones;
}
