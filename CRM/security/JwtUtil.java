package CRM.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Base64;

@Component
public class JwtUtil {

    private final String SECRET_KEY = Base64.getEncoder()
            .encodeToString("STRONG_SECRET_KEY_FOR_CRM_SECURITY_123456789".getBytes());

    //Generate Token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10)) //10 Hrs
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Email
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract Role
    public String extractRole(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }
}
