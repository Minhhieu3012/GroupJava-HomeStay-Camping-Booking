package ut.edu.database.services;

import jakarta.transaction.Transactional;
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
import ut.edu.database.models.Property;
import ut.edu.database.repositories.PropertyRepository;
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
    private PropertyRepository propertyRepository;


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

    //cap nhat user cua admin
    public UserDTO updateUser(String username, UserDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User là: " + username));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setIdentityCard(dto.getIdentityCard());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userMapper.toDTO(userRepository.save(user));
    }

    //cap nhat user cua owner
    public UserDTO updateOwnProfile(String username, UserDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User: "+username));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setIdentityCard(dto.getIdentityCard());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userMapper.toDTO(userRepository.save(user));
    }

    //ADMIN XÓA CUSTOMER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //ADMIN XÓA OWNER
    @Transactional
    public void deleteOwner(Long userId) {
        // 1. Lấy danh sách properties của user
        List<Property> properties = propertyRepository.findByOwnerId(userId);

        // 2. Xóa các property
        propertyRepository.deleteByOwnerId(userId);

        // 3. Xóa user
        userRepository.deleteById(userId);
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


//LỌC ROLE == CUSTOMER ĐỂ ĐƯA VÀO QUẢN LÍ TK USER
    public List<UserDTO> getAllCustomerUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> "CUSTOMER".equalsIgnoreCase(String.valueOf(user.getRole()))) // lọc role = "USER" (không phân biệt hoa thường)
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

//LỌC ROLE == OWNER ĐỂ ĐƯA VÀO QUẢN LÍ TK OWNER
    public List<UserDTO> getAllOwnerUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> "OWNER".equalsIgnoreCase(String.valueOf(user.getRole()))) // lọc role = "USER" (không phân biệt hoa thường)
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }


    //LƯU THAY ĐỔI TK CUS
    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setIdentityCard(userDTO.getIdentityCard());

        userRepository.save(user);
    }

    //LƯU THAY ĐỔI TK OWNER
    public void updateOwner(UserDTO ownerDTO) {
        User owner = userRepository.findById(ownerDTO.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        owner.setUsername(ownerDTO.getUsername());
        owner.setEmail(ownerDTO.getEmail());
        owner.setPhone(ownerDTO.getPhone());
        owner.setIdentityCard(ownerDTO.getIdentityCard());

        userRepository.save(owner);
    }
}