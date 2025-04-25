package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Email không được để trống -.-")
    @Email(message = "Email không hợp lệ :((")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống -.-")
    private String password;
}