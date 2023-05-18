package com.avla.entrevista.entity.usuario;

import com.avla.entrevista.entity.token.Token;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID user_id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo electrónico es inválido")
    private String email;

    @Column(nullable = false)
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{2})[a-zA-Z\\d]{8,12}$", message = "La contraseña es inválida")
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(Role.ADMIN.name());
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(simpleGrantedAuthority);
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
