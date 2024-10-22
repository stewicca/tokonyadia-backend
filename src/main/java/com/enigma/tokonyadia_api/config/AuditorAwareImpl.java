package com.enigma.tokonyadia_api.config;

import com.enigma.tokonyadia_api.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return Optional.of(user.getId());
            }

            if (principal instanceof String) {
                return Optional.of((String) principal);
            }
        }

        return Optional.empty();
    }
}