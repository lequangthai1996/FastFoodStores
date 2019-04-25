package fastfood.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fastfood.contant.Error;
import fastfood.entity.SupplierEntity;
import fastfood.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterSalerAccountDTO {
    // User info => UserEntity
    private String username;
    private String password;
    private String confirm_password;
    private String address;
    private String email;
    private String full_name;
    private Boolean gender;
    private String phone;
    @JsonProperty
    private String avatar;

    // Saler info => SupplierEntity
    private Integer package_id;
    private String store_name;
    private String store_address;
    private String store_phone;
    private String backgroundImage;
    private List<Integer> categories;

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getPackage_id() {
        return package_id;
    }

    public void setPackage_id(Integer package_id) {
        this.package_id = package_id;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public List<ErrorCustom> validateData() {
        List<ErrorCustom> listErrors = new ArrayList<>();
        ErrorCustom errorCustom = null;
        if(!this.getPassword().equals(this.getConfirm_password())) {
             errorCustom = new ErrorCustom(null, Error.PASSWORD_NOT_MATCH.getCode(), Error.PASSWORD_NOT_MATCH.getMessage());
             listErrors.add(errorCustom);
        }
        return listErrors;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public UserEntity toUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(this.getUsername());
        userEntity.setAddress(this.getAddress());
        userEntity.setAvatar(this.getAvatar());
        userEntity.setEmail(this.getEmail());
        userEntity.setFullName(this.getFull_name());
        userEntity.setGender(this.getGender());
        userEntity.setPhone(this.getPhone());
        return userEntity;
    }

    public SupplierEntity toSupplierEntity() {
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setStoreAddress(this.getStore_address());
        supplierEntity.setStorePhone(this.getStore_phone());
        return  supplierEntity;
    }
}
