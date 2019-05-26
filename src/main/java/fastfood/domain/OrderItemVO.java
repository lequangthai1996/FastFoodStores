package fastfood.domain;

import java.util.Date;

public class OrderItemVO {
    private Long id;
    private Long idItem;
    private ItemVO itemVO;
    private Integer quantityCart;
    private Double priceOffical;
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPriceOffical() {
        return priceOffical;
    }

    public void setPriceOffical(Double priceOffical) {
        this.priceOffical = priceOffical;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long id) {
        this.idItem = id;
    }

    public Integer getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(Integer quantity) {
        this.quantityCart = quantity;
    }


    public ItemVO getItemVO() {
        return itemVO;
    }

    public void setItemVO(ItemVO itemVO) {
        this.itemVO = itemVO;
    }
}