package com.avla.entrevista.entity.usuario;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "telefono")
public class Telefono implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private Integer citycode;

    @Column(nullable = false)
    private String contrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;


}
