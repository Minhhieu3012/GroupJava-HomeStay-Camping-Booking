package admin.bookinghomecamping.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUntil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    private String secretKey = "secret";
    public String generateToken(String admin) {

        return Jwts.builder()
                .setSubject(admin)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 giờ
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token,  userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
}
