package fastfood.build;

import fastfood.domain.ItemVO;
import fastfood.domain.OrderItemVO;

public final class OrderItemVOBuilder {
    private Long id;
    private Long idItem;
    private ItemVO itemVO;
    private Integer quantityCart;
    private Double priceOffical;

    private OrderItemVOBuilder() {
    }

    public static OrderItemVOBuilder anOrderItemVO() {
        return new OrderItemVOBuilder();
    }

    public OrderItemVOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderItemVOBuilder withIdItem(Long idItem) {
        this.idItem = idItem;
        return this;
    }

    public OrderItemVOBuilder withItemVO(ItemVO itemVO) {
        this.itemVO = itemVO;
        return this;
    }

    public OrderItemVOBuilder withQuantityCart(Integer quantityCart) {
        this.quantityCart = quantityCart;
        return this;
    }

    public OrderItemVOBuilder withPriceOffical(Double priceOffical) {
        this.priceOffical = priceOffical;
        return this;
    }

    public OrderItemVO build() {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setId(id);
        orderItemVO.setIdItem(idItem);
        orderItemVO.setItemVO(itemVO);
        orderItemVO.setQuantityCart(quantityCart);
        orderItemVO.setPriceOffical(priceOffical);
        return orderItemVO;
    }
}
