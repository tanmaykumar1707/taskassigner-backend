package com.assinger.taskservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl  implements AuditorAware<Long> {
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
       return Optional.ofNullable(currentAuditor.get());
        //return Optional.of(Long.parseLong("0"));
        // return Optional.of("UserMicroService");
    }
}
