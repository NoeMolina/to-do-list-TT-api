package com.NMolina.to_do_list_TT.infrastructure.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.NMolina.to_do_list_TT.infrastructure.adapter.out.security.UserDetailsAdapter;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()
                    || !(auth.getPrincipal() instanceof UserDetailsAdapter adapter)) {
                return Optional.empty();
            }
            return Optional.of(adapter.getUserId());
        };
    }
}