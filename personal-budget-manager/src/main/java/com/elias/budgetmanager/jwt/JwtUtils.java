package com.elias.budgetmanager.jwt;

import com.elias.budgetmanager.model.CustomUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtils {


    @Value("${project.jwt.secret}")
    private String jwtSecret;

    @Value("${project.jwt.expiration}")
    private long jwtExpirationMs;


    private Key signingKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    };


    public String generateJwtToken(CustomUserDetails userDetails){

        List<String> roles =  userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userDetails.getUsername())
                .claim("nickname", userDetails.getNickname())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey(),  SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(signingKey())
                    .build()
                    .parse(token);
            return true;
        }
        catch (JwtException ex){
            return false;
        }
    }


    public String getEmailFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
