package ut.edu.database.filters;
//Bộ lọc kiểm tra Jwt trong mỗi request, xác thực người dùng trước 	khi xử lí controller.

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ut.edu.database.jwt.JwtUtil;
import ut.edu.database.services.UserService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, @Lazy UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7); // Extract the token
                String username = jwtUtil.extractUsername(token); // Extract username from token
                String role = jwtUtil.extractRole(token).name(); //ADMIN, CUSTOMER, OWNER

                // Check if the user is not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Kiểm tra token hợp lệ (signature, expiration, username khớp)
                    if (jwtUtil.validateToken(token, username)) {
                        // Chuyển vai trò sang ROLE_ prefix cho Spring Security
                        List<SimpleGrantedAuthority> authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_" + role)
                        );

                        // Tạo authentication object
                        if(jwtUtil.validateToken(token,username)) {
                            UserDetails userDetails = userService.loadUserByUsername(username); //lay userDetail that su
                            List<SimpleGrantedAuthority> authorities1 = List.of(
                                    new SimpleGrantedAuthority("ROLE_" + role)
                            );
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities1);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            // Set vào security context
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            } catch (Exception e) {
                // Không cho lỗi JWT làm crash hệ thống
                logger.warn("JWT token error: {}"+e.getMessage());
            }
        }
        chain.doFilter(request, response); // Continue the filter chain
    }
}
