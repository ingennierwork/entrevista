package com.avla.entrevista.service;

import com.avla.entrevista.dao.UserRepository;
import com.avla.entrevista.dto.ResponseSignUp;
import com.avla.entrevista.dto.UsuarioDTO;
import com.avla.entrevista.entity.token.Token;
import com.avla.entrevista.entity.usuario.Usuario;
import com.avla.entrevista.dao.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpUsuarioTest() {
        // Configuramos los mocks
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setName("Test");
        usuarioDTO.setEmail("test@test.com");
        usuarioDTO.setPassword("1234");
        usuarioDTO.setPhones(new ArrayList<>());
        Usuario usuario = new Usuario();
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setUser_id(UUID.randomUUID());
        usuario.setCreated(LocalDateTime.now());
        usuario.setLastLogin(LocalDateTime.now());

        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);

        String token = "test-token";
        when(jwtService.generateToken(any(Usuario.class))).thenReturn(token);

        Token savedToken = new Token();
        savedToken.setToken(token);
        when(tokenRepository.save(any(Token.class))).thenReturn(savedToken);

        // Ejecutamos el método a probar
        ResponseSignUp savedUsuarioDTO = authenticationService.signUpUsuario(usuarioDTO);

        // Verificamos el resultado
        assertEquals("1",usuarioDTO.getName(), savedUsuarioDTO.getName());
        assertEquals("2",usuarioDTO.getEmail(), savedUsuarioDTO.getEmail());

        assertEquals("3",usuario.getUser_id().toString(), savedUsuarioDTO.getUuid());
        assertEquals("4",usuario.getCreated().toString(), savedUsuarioDTO.getFechaCreacion());
        assertEquals("5",usuario.getLastLogin().toString(), savedUsuarioDTO.getUltimoLogin());
        assertEquals("6",true, savedUsuarioDTO.getEstaActivo());
        assertEquals("7",token, savedUsuarioDTO.getToken());
    }
}
