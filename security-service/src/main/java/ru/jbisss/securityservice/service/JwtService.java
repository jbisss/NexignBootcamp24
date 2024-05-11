package ru.jbisss.securityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public static final Set<String> ABONENT_ENDPOINT = Set.of("money");
    public static final Set<String> MANAGER_ENDPOINT = Set.of("abonents", "tariff");
    public static final Set<String> ADMIN_ENDPOINT = Set.of("generateCdrs");

    public void validateToken(String token) {
        getClaims(token);
    }

    public void validateRequest(String token, String requestPath) {
        Jws<Claims> claims = getClaims(token);
        String sub = (String) claims.getBody().get("sub");
        final RuntimeException accessDenied = new RuntimeException("Access denied!");
        if (sub.equals("abonent")) {
            if (!ABONENT_ENDPOINT.contains(requestPath)) {
                throw accessDenied;
            }
        } else if (sub.equals("manager")) {
            if (!MANAGER_ENDPOINT.contains(requestPath)) {
                throw accessDenied;
            }
        } else if (sub.equals("admin")) {
            if (!ADMIN_ENDPOINT.contains(requestPath)) {
                throw accessDenied;
            }
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
