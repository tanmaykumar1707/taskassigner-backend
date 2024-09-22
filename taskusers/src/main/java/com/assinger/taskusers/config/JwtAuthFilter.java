package com.assinger.taskusers.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authHeader = request.getHeader("Authorization");

        String serviceHeader = request.getHeader("Service-Source");
        log.info("====>serviceHeader===>"+serviceHeader);

        if("internal".equals(serviceHeader)){
            log.info("returning not tho");
            filterChain.doFilter(request, response);
            return;
        }

        String email=null,token=null;
//        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            email = jwtService.extractEmailId(token);
//
//        }

        if(authHeader!=null ) {
           token = authHeader;
            email = jwtService.extractEmailId(authHeader);

        }
        log.info("===> email in the token :: " + email);

        if(email!=null  && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token, userDetails)) {
                log.info("token validated ");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails	,null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                System.out.println("token not  validated ");
            }
        }

        filterChain.doFilter(request, response);


    }
}
