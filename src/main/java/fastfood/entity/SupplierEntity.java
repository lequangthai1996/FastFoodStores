package fastfood.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SUPPLIER")
public class SupplierEntity extends  BasicEntity{
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SUPPLIER_SEQ", sequenceName = "SUPPLIER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SUPPLIER_SEQ")
    private Long id;

    @Column(name = "active_from_date")
    private Date activeFromDate;

    @Column(name = "active_to_date")
    private Date activeToDate;

    @Column(name = "total_item")
    private Long totalItem;

    @Column(name = "background_image")
    private String backgroundImage;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "store_phone")
    private String storePhone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_03"))
    private UserEntity user;

    @OneToMany(mappedBy = "supplier")
    private List<ItemEntity> listItems;

    @OneToMany(mappedBy = "supplier")
    private List<OrderEntity> listOrders;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierCategoryEntity> listSupplierCategories;

    public List<SupplierCategoryEntity> getListSupplierCategories() {
        return listSupplierCategories;
    }

    public void setListSupplierCategories(List<SupplierCategoryEntity> listSupplierCategories) {
        this.listSupplierCategories = listSupplierCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(Date activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public Date getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(Date activeToDate) {
        this.activeToDate = activeToDate;
    }

    public Long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Long totalItem) {
        this.totalItem = totalItem;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<ItemEntity> getListItems() {
        return listItems;
    }

    public void setListItems(List<ItemEntity> listItems) {
        this.listItems = listItems;
    }

    public List<OrderEntity> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<OrderEntity> listOrders) {
        this.listOrders = listOrders;
    }
}
