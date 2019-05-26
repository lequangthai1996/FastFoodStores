package fastfood.domain;

import java.util.Date;

public class ItemVO {

    private Integer id;
    private String name;
    private Float price;
    private String avatar;
    private Boolean status;
    private Integer quantity;
    private String description;

    private Date createdAt;
    private Date editedAt;
    private Boolean active;
    private UnitVO unit;
    private SupplierVO supplier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UnitVO getUnit() {
        return unit;
    }

    public void setUnit(UnitVO unit) {
        this.unit = unit;
    }

    public SupplierVO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierVO supplier) {
        this.supplier = supplier;
    }



}