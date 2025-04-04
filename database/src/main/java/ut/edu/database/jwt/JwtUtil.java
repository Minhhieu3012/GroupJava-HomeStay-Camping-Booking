package ut.edu.database.jwt;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtil {
    private String secretKey = "secret";  // Secret key để ký JWT token

    // Tạo JWT token với username, ngày tạo và thời gian hết hạn
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token hết hạn sau 1 giờ
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Dùng thuật toán HS256 để ký
                .compact();
    }

    // Lấy username từ JWT token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()  // Sử dụng JwtParserBuilder
                .setSigningKey(secretKey)  // Cung cấp secret key
                .build()  // Xây dựng parser
                .parseClaimsJws(token)  // Giải mã token
                .getBody();  // Lấy Claims (thông tin trong token)

        return claims.getSubject();  // Trả về username từ claims
    }

    // Kiểm tra token có hợp lệ không
    public boolean validateToken(String token, UserDetails userDetails) {
        // Kiểm tra username trong token có trùng với username trong userDetails không
        return extractUsername(token).equals(userDetails.getUsername());
    }
}
