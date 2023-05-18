package com.avla.entrevista.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    @NotBlank
    private String name;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo electrónico es inválido")
    private String email;
    private String password;
    private List<TelefonoDTO> phones;

}
