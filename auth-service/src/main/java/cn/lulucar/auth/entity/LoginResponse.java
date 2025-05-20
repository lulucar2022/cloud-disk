package cn.lulucar.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wxl
 * @date 2025/5/10 20:28
 * @description
 */
@Data
public class LoginResponse{
    private String token;
    private User user;
    public LoginResponse(){
    }
    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
