package fastfood.service;

import fastfood.domain.OrderVO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.entity.OrderEntity;
import fastfood.entity.UserEntity;
import org.springframework.data.domain.Page;

public interface OrderService {
    public ResponseCommonAPI createOrder(OrderVO order) throws Exception;
    public Page<OrderVO> getOrderByUser(UserEntity user, int page, int size, String sort);
    public Page<OrderVO> getOrderByStatus(int status, int page, int size, String sort, Long supplierID);
    OrderEntity deleteItem(Long id);
    OrderVO updateStatus(Long id, Integer status);
    OrderVO getByID(Long id);

}