package fastfood.build;

import fastfood.domain.OrderItemVO;
import fastfood.domain.OrderVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class OrderVOBuilder {
    private Long id;
    private String name = "";
    private String address;
    private String phone;
    private String note;
    private Date transportedAt;
    private Date createdAt;
    private Long userId;
    private Integer promotionId;
    private Integer shipId;
    private Double total = 0.0;
    private List<OrderItemVO> orderItems = new ArrayList<>();
    private Integer status;
    private OrderVOBuilder() {
    }

    public static OrderVOBuilder anOrderVO() {
        return new OrderVOBuilder();
    }

    public OrderVOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderVOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public OrderVOBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public OrderVOBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public OrderVOBuilder withNote(String note) {
        this.note = note;
        return this;
    }

    public OrderVOBuilder withTransportedAt(Date transportedAt) {
        this.transportedAt = transportedAt;
        return this;
    }

    public OrderVOBuilder withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OrderVOBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderVOBuilder withPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public OrderVOBuilder withShipId(Integer shipId) {
        this.shipId = shipId;
        return this;
    }
    public OrderVOBuilder withStatus(Integer status) {
        this.status = status;
        return this;
    }
    public OrderVOBuilder withTotal(Double total) {
        this.total = total;
        return this;
    }

    public OrderVOBuilder withOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public OrderVO build() {
        OrderVO orderVO = new OrderVO();
        orderVO.setId(id);
        orderVO.setName(name);
        orderVO.setAddress(address);
        orderVO.setPhone(phone);
        orderVO.setNote(note);
        orderVO.setTransportedAt(transportedAt);
        orderVO.setUserId(userId);
        orderVO.setPromotionId(promotionId);
        orderVO.setShipId(shipId);
        orderVO.setOrderItems(orderItems);
        orderVO.setTotal(total);
        orderVO.setStatus(status);
        orderVO.setCreatedAt(createdAt);
        return orderVO;
    }
}
