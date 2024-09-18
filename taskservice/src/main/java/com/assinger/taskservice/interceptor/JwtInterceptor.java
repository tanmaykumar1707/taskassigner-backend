package com.assinger.taskservice.interceptor;


import com.assinger.taskservice.audit.AuditAwareImpl;
import com.assinger.taskservice.exception.UnauthorizedException;
import com.assinger.taskservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;

    public JwtInterceptor(JwtUtils jwtUtils){
        this.jwtUtils=jwtUtils;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // JwtUtils jwtUtils = new JwtUtils();
        if (!"GET".equals(request.getMethod() )){
          String jwtToken =
                  Optional.of(request.getHeader("Authorization"))
                          .filter(token -> !token.trim().isEmpty()).orElseThrow(()->
                          new UnauthorizedException("NON-GET ACTION REQUIRES JWT TOKEN"));

            try{
                log.info("jwt id "+jwtUtils.extractEmpId(jwtToken));
                AuditAwareImpl.setCurrentAuditor(Long.parseLong(jwtUtils.extractEmpId(jwtToken)));
            }catch (Exception ex){
                log.error("error in inte "+ ex.getMessage());
                throw new UnauthorizedException("Invalid Jwt Token " );
            }

        }
        return  true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuditAwareImpl.clear();

    }
}
