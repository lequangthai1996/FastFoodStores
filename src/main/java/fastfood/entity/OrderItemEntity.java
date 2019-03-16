package fastfood.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItemEntity extends  BasicEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ORDER_ITEM_SEQ", sequenceName = "ORDER_ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ORDER_ITEM_SEQ")
    private Long id;

    @Column(name = "price_official")
    private Double priceOfficial;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "FK_ORDER_02"))
    private OrderEntity order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", foreignKey = @ForeignKey(name = "FK_ITEM_01"))
    private ItemEntity item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPriceOfficial() {
        return priceOfficial;
    }

    public void setPriceOfficial(Double priceOfficial) {
        this.priceOfficial = priceOfficial;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
