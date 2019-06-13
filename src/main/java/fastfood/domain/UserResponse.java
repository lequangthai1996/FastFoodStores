package fastfood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fastfood.entity.RoleEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResponse {
    private  String id;
    private String username;
    private String fullName;
    private String avatar;
    private String email;
    private String address;
    private String phone;
    private Boolean gender;

    @JsonIgnore
    private String password;
    private List<RoleDTO>  authorities;


    public UserResponse() {
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public UserResponse(String id, String avatar, String username, String fullName, String password, List<RoleDTO> authorities, String email) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.id = id;
        this.avatar = avatar;
        this.authorities = authorities;
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<RoleDTO> authorities) {
        this.authorities = authorities;
    }


}
