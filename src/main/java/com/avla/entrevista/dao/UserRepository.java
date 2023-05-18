package com.avla.entrevista.dao;

import com.avla.entrevista.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

}
