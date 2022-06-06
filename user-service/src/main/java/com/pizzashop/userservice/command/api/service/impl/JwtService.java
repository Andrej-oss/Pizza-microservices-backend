package com.pizzashop.userservice.command.api.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String SECURITY_KEY;

    @PostConstruct
    public void init() {
        SECURITY_KEY = Base64.getEncoder().encodeToString(SECURITY_KEY.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public String extractName(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return this.createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY)
                .compact();
    }
    public Boolean validateToken(String token, String userName) {
        String extractName = this.extractName(token);
        return extractName.equals(userName) && !this.isTokenExpiated(token);
    }

    private boolean isTokenExpiated(String token) {
        return this.extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
