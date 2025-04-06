package ut.edu.database.jwt;

import java.util.Date;
import java.util.Base64;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ut.edu.database.models.Role;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String base64Secret;

    private SecretKey secretKey;

    // Khởi tạo secretKey sau khi Spring inject xong
    // Chạy sau khi object được khởi tạo -> decode secretKey từ base64
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Tạo JWT token với username
    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name()) // chuyển enum thành chuỗi (ADMIN, CUSTOMER...)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 giờ
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Trích xuất username từ token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Kiểm tra token hợp lệ
    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public Role extractRole(String token) {
        String roleStr = extractClaims(token).get("role", String.class);
        return Role.valueOf(roleStr); //convert từ String về enum Role
    }

//    public String extractRole(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("role", String.class);
//    }

    // Kiểm tra token đã hết hạn chưa
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Trích xuất toàn bộ Claims
    private Claims extractClaims(String token) {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return jwsClaims.getBody();
    }
}


