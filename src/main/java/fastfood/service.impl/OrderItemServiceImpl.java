package fastfood.service.impl;

import fastfood.domain.ItemVO;
import fastfood.domain.OrderItemVO;
import fastfood.entity.OrderItemEntity;
import fastfood.repository.ItemRepository;
import fastfood.service.OrderItemRepository;
import fastfood.service.OrderItemService;
import fastfood.service.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemServiceImpl itemService;

    public OrderItemVO convertVO(OrderItemEntity orderItem) {
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setId(orderItem.getId());
        orderItemVO.setQuantityCart(orderItem.getQuantity());
        orderItemVO.setPriceOffical(orderItem.getPriceOfficial());
        orderItemVO.setCreateAt(orderItem.getCreatedDate());
        ItemVO itemVO = new ItemVO();
        itemVO = itemService.convertVO(orderItem.getItem());
        orderItemVO.setItemVO(itemVO);
        return orderItemVO;
    }

    @Override
    @Transactional
    public List<OrderItemVO> getItemByOrder(long id) throws EntityNotFoundException {
        List<OrderItemEntity> orderItems = orderRepository.getOne(id).getListOrderItems();
        List<OrderItemVO> orderItemVOS = new ArrayList<>();
        for (OrderItemEntity orderItem: orderItems
                ) {
            orderItemVOS.add(convertVO(orderItem));
        }
        return orderItemVOS;
    }
    @Override
    @Transactional
    public OrderItemVO deleteItem(long id) {
        OrderItemEntity orderItem = orderItemRepository.getOne(id);
        OrderItemVO orderItemVO = this.convertVO(orderItem);
        orderItemRepository.delete(orderItem);
        return orderItemVO;
    }

}