package com.interview.reminder.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;


class SpringSecurityAuditorAware implements AuditorAware<UUID> {

    @Override
    public UUID getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((MyUser) authentication.getPrincipal()).getAccount_id();
    }
}
