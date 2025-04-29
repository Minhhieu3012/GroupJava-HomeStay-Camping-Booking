package ut.edu.database.configurations;
//Cấu hình bảo mật Spring Security - quản lý xác thực và phân quyền
//muc tieu:
// bao ve API
// kich hoat phan quyen qua Jwt

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ut.edu.database.filters.JwtAuthenticationFilter;

@EnableMethodSecurity(prePostEnabled = true) //cho phep dung @PreAuthorize trong cac controller
@Configuration
public class SecurityConfig {

    // kiem tra token, lay username, role từ Jwt
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // cau hinh bao mat chinh
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                // STATELESS = ko luu session, moi request phai tu co token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // phan quyen chi tiet
                .authorizeHttpRequests(auth -> auth
                        // Công khai - cho phep truy cap ko can token
                        .requestMatchers(
                                "/auth/**", //login, register
                                "/swagger-ui/**", //giao dien test API
                                "/v3/api-docs/**" //OpenAPI docs (cho Swagger)
                        ).permitAll()

                        // Phân quyền theo vai trò
                        // tu dong kiem tra role cua user trong Jwt
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/owner/**").hasRole("OWNER")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")

                        // Cac endpoint can login, nhung ko phan quyen cu the
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/home", "/login", "/register", "/auth/**").permitAll()
                        .requestMatchers("/home", "/login", "/register", "/homestay-detail", "/auth/**").permitAll()

                        // Còn lại cho phép truy cập
                        .anyRequest().permitAll()
                )

                // dat filter kiem tra Jwt truoc khi Spring xu li UsernamePassword
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}