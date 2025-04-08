package ut.edu.database.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private String username;
    private String role;

    // Constructor cho trường hợp chỉ có message
    public AuthResponse(String message) {
        this.message = message;
    }
}


