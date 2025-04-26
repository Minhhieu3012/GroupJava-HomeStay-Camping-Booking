package ut.edu.database.services;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ut.edu.database.dtos.RegisterRequest;
import ut.edu.database.dtos.UserDTO;
import ut.edu.database.enums.Role;
import ut.edu.database.mapper.UserMapper;
import ut.edu.database.models.User;
import ut.edu.database.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Get all users (Admin)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public Optional<UserDTO> getUserDTOById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Tìm theo email (dùng cho phân quyền)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
        .orElseGet(() -> userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User: " + username + ":((")));
        return user.getId();
    }

    // Đăng ký (đã dùng ở AuthController)
    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email đã tồn tại :((";
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username đã tồn tại :((";
        }
        if(userRepository.existsByPhone(request.getPhone())){
            return "Số điện thoại đã tồn tại :((";
        }

        if(userRepository.existsByIdentityCard(request.getIdentityCard())){
            return "Thẻ căn cước đã tồn tại :((";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setIdentityCard(request.getIdentityCard());
        user.setRole(request.getRole() != null ? request.getRole() : Role.CUSTOMER);

        userRepository.save(user);
        return "Đăng ký thành công :D";
    }

    //cap nhat user
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User với id là: " + id));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setIdentityCard(dto.getIdentityCard());

        return userMapper.toDTO(userRepository.save(user));
    }


    // Admin xoá người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User :(("));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                )
        );
    }

    // Add this method to UserService
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}