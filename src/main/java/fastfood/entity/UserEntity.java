package fastfood.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USERS_SEQ")
    private Long id;

    @Column(name="username",nullable = false, unique = false, length = 100)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="avatar")
    private String avatar;

    @Column(name="is_locked", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isLocked;

    @Column(name="full_name")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<UserRoleEntity> listUserRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SupplierEntity> listSuppliers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrderEntity> listOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Set<UserRoleEntity> getListUserRoles() {
        return listUserRoles;
    }

    public void setListUserRoles(Set<UserRoleEntity> listUserRoles) {
        this.listUserRoles = listUserRoles;
    }

    public Set<SupplierEntity> getListSuppliers() {
        return listSuppliers;
    }

    public void setListSuppliers(Set<SupplierEntity> listSuppliers) {
        this.listSuppliers = listSuppliers;
    }

    public List<OrderEntity> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderEntity> listOrders) {
        this.listOrders = listOrders;
    }
}
