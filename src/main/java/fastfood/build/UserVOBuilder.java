package fastfood.build;

import fastfood.domain.AuthorityVO;
import fastfood.domain.OrderVO;
import fastfood.domain.UserVO;
import fastfood.entity.SupplierEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public final class UserVOBuilder {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private Boolean gender;
    private Date birthday;
    private String avatar;
    private String creditCard;
    private Date createdAt;
    private Date editedAt;
    private SupplierEntity supplier;
    private Boolean active;
    private Set<AuthorityVO> authorities;
    private List<OrderVO> orders;
    private UserVOBuilder() {
    }

    public static UserVOBuilder anUserVO() {
        return new UserVOBuilder();
    }

    public UserVOBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public UserVOBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserVOBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserVOBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserVOBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserVOBuilder withAddress(String address) {
        this.address = address;
        return this;
    }
    public UserVOBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserVOBuilder withGender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public UserVOBuilder withBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public UserVOBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public UserVOBuilder withCreditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public UserVOBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserVOBuilder withEditedAt(Date editedAt) {
        this.editedAt = editedAt;
        return this;
    }

    public UserVOBuilder withSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
        return this;
    }

    public UserVOBuilder withActive(Boolean active) {
        this.active = active;
        return this;
    }

    public UserVOBuilder withAuthorities(Set<AuthorityVO> authorities) {
        this.authorities = authorities;
        return this;
    }
    public UserVOBuilder withOrder(List<OrderVO> orders) {
        this.orders = orders;
        return this;
    }
    public UserVO build() {
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setUsername(username);
        userVO.setEmail(email);
        userVO.setPassword(password);
        userVO.setFullName(fullName);
        userVO.setAddress(address);
        userVO.setGender(gender);
        userVO.setBirthday(birthday);
        userVO.setAvatar(avatar);
        userVO.setCreditCard(creditCard);
        userVO.setCreatedAt(createdAt);
        userVO.setEditedAt(editedAt);
        userVO.setSupplier(supplier);
        userVO.setActive(active);
        userVO.setAuthorities(authorities);
        userVO.setOrders(orders);
        userVO.setPhone(phone);
        return userVO;
    }
}
