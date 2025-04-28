package ut.edu.database.dtos;

import ut.edu.database.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    //=============Cần tạo================
    private String username;
    private String email;
    private String password;
    private String phone;
    private String identityCard;
    private String avatar;
    private Role role;
    //===================================
}
