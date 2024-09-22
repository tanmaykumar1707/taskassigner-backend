package com.assinger.taskusers.audit;

import com.assinger.taskusers.config.CustomUserDetailsEntity;
import com.assinger.taskusers.entity.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
@Slf4j
public class AuditAwareImpl implements AuditorAware<Long> {

    private static final ThreadLocal<Long> currentAuditor = new ThreadLocal<>();

    // Method to set the current auditor
    public static void setCurrentAuditor(Long auditor) {
        currentAuditor.set(auditor);
    }

    // Method to clear the current auditor
    public static void clear() {
        currentAuditor.remove();
    }
    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        CustomUserDetailsEntity usersEntity =   (CustomUserDetailsEntity) authentication.getPrincipal();
         log.info(" auditware===>"+usersEntity.getUserId());
        return Optional.ofNullable(usersEntity.getUserId());

       // return Optional.of("UserMicroService");
    }
}
