package com.assinger.taskservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${SECURITY.JWT_SEC}")
    private String jwtSecret;


    //converting secret string to Java Security key
    private Key getSignInKey() {
        byte[] keyBytes =  Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //extracting Claims from token by using Secret Key
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String extractEmpId(String token) {
        final Claims claims = extractAllClaims(token);
        return  claims.getSubject();
    }

    public boolean isTokenvalid(String token) {
        final Claims claims = extractAllClaims(token);

        return claims.getExpiration().before(new Date());
    }



}