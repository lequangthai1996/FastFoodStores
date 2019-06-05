package fastfood.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class LoginResponse {
    private  UserResponse user;
    private  String token;
    private static Integer expire = 604800;

    public LoginResponse(UserResponse user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static Integer getExpire() {
        return expire;
    }

    public static void setExpire(Integer expire) {
        LoginResponse.expire = expire;
    }
}
