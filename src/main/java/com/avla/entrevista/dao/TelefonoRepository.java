package com.avla.entrevista.dao;

import com.avla.entrevista.entity.usuario.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepository extends JpaRepository<Telefono, Long> {
}
