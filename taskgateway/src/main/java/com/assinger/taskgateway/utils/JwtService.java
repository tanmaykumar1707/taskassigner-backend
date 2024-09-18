package com.assinger.taskgateway.utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;



@Component
public class JwtService {

    @Value("${SECURITY.JWT_SEC}")
    private String jwtSecret;

    @Value("${SECURITY.JWT_EXP}")
    private long expirationDuration;


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

    public String extractEmailId(String token) {
        Claims claim = extractAllClaims(token);
        return claim.get("email",String.class);
    }


    //extract claims method
    private <T> T extractClaims (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }




    public String extractEmpId(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

    public boolean validateToken(String token) {
        try{
            isTokenExpired(token) ;
            extractEmailId(token);
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
