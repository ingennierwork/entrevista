package com.avla.entrevista.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoDTO {
    private Long number;
    private Integer citycode;
    private String contrycode;
}
