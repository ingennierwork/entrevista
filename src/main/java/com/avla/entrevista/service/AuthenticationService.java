package com.avla.entrevista.service;

import com.avla.entrevista.dao.TokenRepository;
import com.avla.entrevista.dao.UserRepository;
import com.avla.entrevista.dto.*;
import com.avla.entrevista.entity.token.Token;
import com.avla.entrevista.entity.token.TokenType;
import com.avla.entrevista.entity.usuario.Telefono;
import com.avla.entrevista.entity.usuario.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.userRepository         = userRepository;
        this.jwtService             = jwtService;
        this.passwordEncoder        = passwordEncoder;
        this.tokenRepository        = tokenRepository;
        this.authenticationManager  = authenticationManager;
    }

    public ResponseSignUp signUpUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = mapToUsuario(usuarioDTO);
        Usuario save = userRepository.save(usuario);
        String token = jwtService.generateToken(usuario);
        saveUserToken(save,token);
        return ResponseSignUp.builder()
                .token(token)
                .name(save.getName())
                .email(save.getEmail())
                .password(save.getPassword())
                .fechaCreacion(save.getCreated().toString())
                .uuid(save.getUser_id().toString())
                .estaActivo(save.isActive())
                .ultimoLogin(save.getLastLogin().toString())
                .build();
    }


    public LoginResponse logInUsusario(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Usuario usuario = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        usuario.setLastLogin(LocalDateTime.now());
        String token = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);
        revokeAllUserTokens(usuario);

        saveUserToken(usuario, token);

        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(usuario.getUser_id());
        Usuario save = userRepository.save(usuario);
        return LoginResponse.builder()
                .id(save.getUser_id().toString())
                .name(save.getName())
                .isActive(save.isActive())
                .created(save.getCreated().toString())
                .email(save.getEmail())
                .password(save.getPassword())
                .lastLogin(save.getLastLogin().toString())
                //.phones(save.getTelefonos())
                .token(allValidTokenByUser.get(0).getToken())
                .build();
    }

    private void revokeAllUserTokens(Usuario user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(user.getUser_id());
        if (allValidTokenByUser.isEmpty())
            return;
        allValidTokenByUser.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(allValidTokenByUser);
    }
    private Usuario mapToUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setName(usuarioDTO.getName());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setActive(true);
        usuario.setCreated(LocalDateTime.now());
        usuario.setLastLogin(LocalDateTime.now());

        List<Telefono> telefonos = new ArrayList<>();
        telofonoDtoToTelefono(usuarioDTO, usuario, telefonos);

        usuario.setTelefonos(telefonos);
        return usuario;

    }


    private void saveUserToken(Usuario usuario,
                               String jwtToken) {
        Token token = Token.builder()
                .user(usuario)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public boolean updateUsuario(UsuarioDTO request) {
        Usuario usuario = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Usuario usuarioActualizado = updateUsuario(request, usuario);
        Usuario save = userRepository.save(usuarioActualizado);
        return save.getUser_id().equals(usuario.getUser_id());
    }

    public Object deleteUsuario(String email) {
        return null;
    }

    private Usuario updateUsuario(UsuarioDTO request,
                                  Usuario usuario){

        usuario.setName(request.getName() != null ? request.getName() : usuario.getName());
        usuario.setEmail(request.getEmail() != null ? request.getEmail() : usuario.getEmail());
        usuario.setPassword(request.getPassword() != null ?  passwordEncoder.encode(request.getPassword()): usuario.getPassword());
        List<Telefono> telefonos = usuario.getTelefonos();
        telofonoDtoToTelefono(request, usuario, telefonos);

        return usuario;

    }

    private void telofonoDtoToTelefono(UsuarioDTO request,
                                       Usuario usuario,
                                       List<Telefono> telefonos) {
        for (TelefonoDTO telefonoDTO : request.getPhones()) {
            Telefono telefono = new Telefono();
            telefono.setNumber(telefonoDTO.getNumber());
            telefono.setCitycode(telefonoDTO.getCitycode());
            telefono.setContrycode(telefonoDTO.getContrycode());
            telefono.setUsuario(usuario);
            telefonos.add(telefono);
        }
    }

    public UsuarioDTO infoUsuario(String email) {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Telefono> telefonos = usuario.getTelefonos();
        List<TelefonoDTO> telefonoDTOS = new ArrayList<>();
        telefonos.forEach(telefono -> {
            TelefonoDTO telefonoDTO = new TelefonoDTO();
            telefonoDTO.setNumber(telefono.getNumber());
            telefonoDTO.setCitycode(telefono.getCitycode());
            telefonoDTO.setContrycode(telefono.getContrycode());
            telefonoDTOS.add(telefonoDTO);
        });
        return UsuarioDTO.builder()
                .name(usuario.getName())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .phones(telefonoDTOS)
                .build();

    }
}
