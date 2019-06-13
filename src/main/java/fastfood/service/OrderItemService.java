package fastfood.service;

import fastfood.domain.OrderItemVO;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface OrderItemService {
    public List<OrderItemVO> getItemByOrder(Long orderId, Long userId) throws EntityNotFoundException;
    OrderItemVO deleteItem(long id);

}