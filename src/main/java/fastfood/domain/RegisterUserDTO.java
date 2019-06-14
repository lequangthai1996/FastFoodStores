package fastfood.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fastfood.contant.DefineConstant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RegisterUserDTO {

    @NotNull(message = "username not null")
    private String username;
    @NotNull(message = "email not null")
    @Pattern(regexp = DefineConstant.EMAIL_PATTERN, message = "Email must be valid")
    private String email;
    @NotNull(message = "Password not null")
    private String password;
    private String fullName;
    @NotNull(message = "address not null")
    private String address;
    @Pattern(regexp = DefineConstant.PHONE_PATTERN, message = "Phone number invalid format")
    private String phone;
    private Boolean gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}
