package com.krong.profile.config;

import com.krong.profile.config.model.TokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
//@RequiredArgsConstructor
public class JwtTokenProvider {
    //

    private final SecretKey key;
//    @Value("${jwt.expiration}")
    private final long expiration;
//    @Value("${jwt.refreshExpiration}")
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secretKey}") String secretKey,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.refreshExpiration}") long refreshExpiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }

    public TokenInfo generateToken(Authentication authentication) {
        String accessToken = this.genAccessToken(authentication);
        String refreshToken = this.genRefreshToken();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String genAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        long now = System.currentTimeMillis();
        Date accessTokenExp = new Date(now + expiration);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String genRefreshToken() {
        long now = System.currentTimeMillis();
        Date refreshTokenExp = new Date(now + refreshExpiration);
        return Jwts.builder()
                .setExpiration(refreshTokenExp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = this.parseClaims(accessToken);
        if(claims.get("auth") == null)
            throw new RuntimeException("권한이 없습니다.");
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();
        UserDetails principal = new User(claims.getSubject(), "", List.of());
        return new UsernamePasswordAuthenticationToken(principal, "", List.of());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return this.parseToken(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
