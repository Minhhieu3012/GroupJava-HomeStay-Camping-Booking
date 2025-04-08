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
    @NotBlank(message = "Email cannot be blank!!")
    @Email(message = "Email invalid!")
    private String email;

    @NotBlank(message = "Password cannot be blank!!")
    private String password;
}