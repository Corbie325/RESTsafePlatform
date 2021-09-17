package com.example.restsafeplatform.authorization.service;

import com.example.restsafeplatform.common.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${server.secret}")
    private String secretKey;
    @Value("${server.header}")
    private String authorizationHeader;
    @Value("${server.expiration}")
    private Long validityInMilliseconds;

    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role, String passport_id, Long idUser) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.setId(passport_id);
        claims.setIssuer(String.valueOf(idUser));
        claims.put("role", role);
        claims.put("passport", passport_id);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getIssuer();
    }

    public String getSerialAndNumber(String token) {
        log.info("token123 = {}", token);
        String name  = getUsername(token);
        log.info("name = {}", name);
        log.info("id = {}", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId();
    }
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
