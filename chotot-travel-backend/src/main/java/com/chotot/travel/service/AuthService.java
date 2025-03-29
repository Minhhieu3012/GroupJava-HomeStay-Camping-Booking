package com.chotot.travel.service;


import com.chotot.travel.dto.LoginRequest;
import com.chotot.travel.dto.RegisterRequest;
import com.chotot.travel.model.User;
import com.chotot.travel.repository.UserRepository;
import com.chotot.travel.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User register(RegisterRequest req) {
        if (userRepository.findByEmail(req.email) != null) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        User user = new User(req.email, req.password, req.role);
        return userRepository.save(user);
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email);
        if (user == null || !user.getPassword().equals(req.password)) {
            throw new RuntimeException("Sai email hoặc mật khẩu");
        }
        return jwtTokenProvider.generateToken(user.getEmail());
    }
}
