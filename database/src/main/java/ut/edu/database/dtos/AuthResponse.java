package ut.edu.database.dtos;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    //getters & setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
