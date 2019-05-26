package fastfood.build;

import fastfood.domain.SupplierVO;
import fastfood.domain.UserVO;
import fastfood.entity.ItemEntity;

import java.util.Date;
import java.util.List;

public final class SupplierVOBuilder {
    private Integer id;
    private Integer amount;
    private Date createdAt;
    private Date editedAt;
    private Boolean active;
    private UserVO user;
    private List<ItemEntity> items;

    private SupplierVOBuilder() {
    }

    public static SupplierVOBuilder aSupplierVO() {
        return new SupplierVOBuilder();
    }

    public SupplierVOBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public SupplierVOBuilder withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public SupplierVOBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public SupplierVOBuilder withEditedAt(Date editedAt) {
        this.editedAt = editedAt;
        return this;
    }

    public SupplierVOBuilder withActive(Boolean active) {
        this.active = active;
        return this;
    }

    public SupplierVOBuilder withUser(UserVO user) {
        this.user = user;
        return this;
    }

    public SupplierVOBuilder withItems(List<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public SupplierVO build() {
        SupplierVO supplierVO = new SupplierVO();
        supplierVO.setId(id);
        supplierVO.setAmount(amount);
        supplierVO.setCreatedAt(createdAt);
        supplierVO.setEditedAt(editedAt);
        supplierVO.setActive(active);
        supplierVO.setUser(user);
        supplierVO.setItems(items);
        return supplierVO;
    }
}
