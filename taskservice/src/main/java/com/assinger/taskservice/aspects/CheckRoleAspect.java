package com.assinger.taskservice.aspects;

import com.assinger.taskservice.enums.UserRoleEnum;
import com.assinger.taskservice.exception.UnauthorizedException;
import com.assinger.taskservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@Order(1)
@Slf4j
public class CheckRoleAspect {

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtUtils jwtUtils;

    @Before("@annotation(com.assinger.taskservice.aspects.annotation.OnlyAdmin)")
    public void checkOnlyAdmin(JoinPoint joinPoint){
        String jwtToken = request.getHeader("Authorization");
        String method = joinPoint.getSignature().toShortString();
        try{
            Optional<String> adminCheck =    Optional.of(jwtUtils.extractRole(jwtToken));
            log.info("checking the role for "+method+" with token "+adminCheck.get());
            if(!UserRoleEnum.admin.toString().equals(adminCheck.get())){
                throw new UnauthorizedException("Operation not Allowed!");
                }
        }catch (UnauthorizedException ex){
            throw new UnauthorizedException(ex.getMessage());
        }
        catch (Exception e){
            throw new UnauthorizedException("Invalid Token");
        }
    }
}
