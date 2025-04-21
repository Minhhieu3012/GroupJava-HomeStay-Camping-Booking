package ut.edu.database.dtos;

import lombok.*;

@Getter
@Setter
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

    public AuthResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }
}


