package fastfood.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "ITEM")
public class ItemEntity extends  BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ITEM_SEQ")
    private Long id;

    @Column(name = "is_actived")
    private Boolean isActived;

    @Column(name = "avatar")
    private  String avatar;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", foreignKey = @ForeignKey(name = "FK_UNIT_01"))
    private UnitEntity unit;

    @OneToMany(mappedBy = "item")
    private List<ImageEntity> listImages;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ItemCategoryEntity> listItemCategories;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<PromotionItemEntity> listPromotionItems;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<OrderItemEntity> listOrderItems;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "FK_SUPPLIER_02"))
    private SupplierEntity supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActived() {
        return isActived;
    }

    public void setActived(Boolean actived) {
        isActived = actived;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public UnitEntity getUnit() {
        return unit;
    }

    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    public List<ImageEntity> getListImages() {
        return listImages;
    }

    public void setListImages(List<ImageEntity> listImages) {
        this.listImages = listImages;
    }

    public List<ItemCategoryEntity> getListItemCategories() {
        return listItemCategories;
    }

    public void setListItemCategories(List<ItemCategoryEntity> listItemCategories) {
        this.listItemCategories = listItemCategories;
    }

    public List<PromotionItemEntity> getListPromotionItems() {
        return listPromotionItems;
    }

    public void setListPromotionItems(List<PromotionItemEntity> listPromotionItems) {
        this.listPromotionItems = listPromotionItems;
    }

    public List<OrderItemEntity> getListOrderItems() {
        return listOrderItems;
    }

    public void setListOrderItems(List<OrderItemEntity> listOrderItems) {
        this.listOrderItems = listOrderItems;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
