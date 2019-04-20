package fastfood.domain;

import fastfood.entity.ItemEntity;
import fastfood.utils.StringUtils;

import java.util.List;

public class ItemDTO {
    private String name;
    private Double price;
    private Integer quantity;
    private String description;
    private Boolean active;
    private Long unit_id;
    private List<Integer> categories;
    private Long supplier_id;
    private String avatar;
    private String currentUserId;


    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Long unit_id) {
        this.unit_id = unit_id;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ItemEntity toItemEntity() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setAvatar(this.getAvatar());
        itemEntity.setActived(this.getActive());
        itemEntity.setDescription(this.getDescription());
        itemEntity.setPrice(this.getPrice());
        itemEntity.setQuantity(this.getQuantity());
        itemEntity.setAvatar(this.getAvatar());
        itemEntity.setCreatedBy(StringUtils.convertStringToLongOrNull(this.getCurrentUserId()));
        return  itemEntity;
    }
}
