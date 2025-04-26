package ut.edu.database.controllers.res;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import ut.edu.database.dtos.UserDTO;
import ut.edu.database.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //ADMIN: xem toan bo user
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //ADMIN: xem chi tiet user
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //ADMIN: cap nhat thong tin user (role, status,...)
    @PutMapping("/update-profile-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        UserDTO updatedUser = userService.updateUser(currentUser.getUsername(), userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //CUS & OWNER: cap nhat thong tin profile ( ngoai tru role)
    @PutMapping("/update-profile")
    @PreAuthorize("hasAnyRole('OWNER','CUSTOMER')")
    public ResponseEntity<UserDTO> updateOwnProfile(@RequestBody UserDTO userDTO, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
        UserDTO updatedUser = userService.updateOwnProfile(currentUser.getUsername(), userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //ADMIN: xoa user
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
