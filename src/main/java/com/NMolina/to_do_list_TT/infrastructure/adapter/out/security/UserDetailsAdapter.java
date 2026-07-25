package com.NMolina.to_do_list_TT.infrastructure.adapter.out.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.persistence.entity.UsuarioEntity;

public class UserDetailsAdapter implements UserDetails {

    private final Long userId;
    private final String username;
    private final String passwordHash;
    private final boolean enabled;
    private final String roleCode;

    public UserDetailsAdapter(UsuarioEntity usuario) {
        this.userId = usuario.getId();
        this.username = usuario.getUsername();
        this.passwordHash = usuario.getPasswordHash();
        this.enabled = usuario.isActivo();
        // Se resuelve aquí, DENTRO de la transacción del service que construye este
        // adapter
        this.roleCode = usuario.getRol().getCodigo();
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleCode));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
}