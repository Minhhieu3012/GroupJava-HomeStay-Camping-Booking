//package admin.bookinghomecamping.service;
//
//
//import admin.bookinghomecamping.dto.request.UserCreationRequest;
//import admin.bookinghomecamping.dto.request.UserUpdateRequest;
//import admin.bookinghomecamping.dto.response.UserResponse;
//import admin.bookinghomecamping.entity.User;
//import admin.bookinghomecamping.exception.AppException;
//import admin.bookinghomecamping.exception.ErrorCode;
//import admin.bookinghomecamping.mapper.UserMapper;
//import admin.bookinghomecamping.repository.UserRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class UserService {
//    UserRepository userRepository;
//    UserMapper userMapper;
//
//    public User createUser(UserCreationRequest request) {
//        if(userRepository.existsByUsername(request.getUsername()))
//            throw new AppException(ErrorCode.USER_EXISTED);
//
//        User user = userMapper.toUser(request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        return userRepository.save(user);
//    }
//
//    public List<User> getUsers(){
//        return userRepository.findAll();
//    }
//
//    public UserResponse getUser(String id){
//        return userMapper.toUserResponse(userRepository.findById(Long.valueOf(id))
//                .orElseThrow(()-> new RuntimeException("User with id  not found")));
//    }
//
//    public UserResponse updateUser(String userId,UserUpdateRequest request){
//        User user = userRepository.findById(Long.valueOf(userId))
//                .orElseThrow(()-> new RuntimeException("User with id  not found"));
//
//        userMapper.updateUser(user,request);
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }
//
//    public void deleteUser(String userId) {
//        userRepository.deleteById(Long.valueOf(userId));
//    }
//}