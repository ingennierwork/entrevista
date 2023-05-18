package com.avla.entrevista.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSignUp {

    private String uuid;
    private String name;
    private String email;
    private String password;
    private String fechaCreacion;
    private String ultimoLogin;
    private String token;
    private Boolean estaActivo;

}
