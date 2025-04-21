package com.chotot.travel.service;

import com.chotot.travel.dto.LoginRequest;
import com.chotot.travel.dto.RegisterRequest;
import com.chotot.travel.dto.UpdateProfileRequest;
import com.chotot.travel.exception.InvalidCredentialsException;
import com.chotot.travel.exception.UserAlreadyExistsException;
import com.chotot.travel.model.User;
import com.chotot.travel.repository.UserRepository;
import com.chotot.travel.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đăng ký người dùng với mã hoá mật khẩu + gán name, phone
    public User register(RegisterRequest req) {
        if (userRepository.findByEmail(req.email) != null) {
            throw new UserAlreadyExistsException("Email đã tồn tại!");
        }

        String hashedPassword = passwordEncoder.encode(req.password);
        User user = new User(req.email, hashedPassword, req.role);
        user.setName(req.name);
        user.setPhone(req.phone);
        return userRepository.save(user);
    }

    // Đăng nhập
    public String login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email);
        if (user == null || !passwordEncoder.matches(req.password, user.getPassword())) {
            throw new InvalidCredentialsException("Sai email hoặc mật khẩu");
        }
        return jwtTokenProvider.generateToken(user.getEmail());
    }

    // Cập nhật hồ sơ người dùng
    public User updateProfile(String token, UpdateProfileRequest req) {
        String email = jwtTokenProvider.getEmailFromToken(token);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User không tồn tại");
        }

        user.setName(req.name);
        user.setPhone(req.phone);
        return userRepository.save(user);
    }

    // Đăng xuất
    public String logout(String token) {
        // Xử lý token (xóa token hoặc đánh dấu hết hạn trên phía server)
        // Token được lưu trên client-side nên không cần thực hiện gì thêm ở server
        return "Logged out successfully";
    }

    // Làm mới token
    public String refreshToken(String expiredToken) {
        if (jwtTokenProvider.validateToken(expiredToken)) {
            String email = jwtTokenProvider.getEmailFromToken(expiredToken);
            return jwtTokenProvider.generateToken(email);
        }
        throw new RuntimeException("Invalid token");
    }
}
