package fastfood.service.impl;

import com.google.common.base.Function;
import com.paypal.api.payments.Order;
import fastfood.build.OrderVOBuilder;
import fastfood.contant.DBConstant;
import fastfood.contant.DefineConstant;
import fastfood.domain.OrderItemVO;
import fastfood.domain.OrderVO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.entity.*;
import fastfood.repository.ItemRepository;
import fastfood.repository.SupplierRepository;
import fastfood.repository.UserRepository;
import fastfood.service.OrderItemRepository;
import fastfood.service.OrderRepository;
import fastfood.service.OrderService;
import fastfood.utils.PageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private OrderItemServiceImpl orderItemService;

    public OrderVO convertVO(OrderEntity order) {
        Double total = 0.0;
        List<OrderItemVO> orderItemVOS = new ArrayList<>();
        if (order.getListOrderItems() != null) {
            for (OrderItemEntity orderItem:order.getListOrderItems()
                    ) {
                orderItemVOS.add(orderItemService.convertVO(orderItem));
                if (orderItem.getPriceOfficial() != null) {
                    total += orderItem.getPriceOfficial() * orderItem.getQuantity();
                }
            }
        }
        OrderVO orderVO = OrderVOBuilder.anOrderVO().withId(order.getId()).withAddress(order.getDeliveryAddress())
                .withName(order.getCustomerName()).withNote(order.getNote())
                .withPhone(order.getCustomerPhone())
                .withTransportedAt(order.getDeliveryTime())
                .withUserId(order.getUser().getId()).withOrderItems(orderItemVOS)
                .withTotal(total).withCreatedAt(order.getCreatedDate())
                .withStatus(order.getStatus())
                .build();
        return orderVO;
    }
    @Override
    @Transactional
    public ResponseCommonAPI createOrder(OrderVO orderVO) throws Exception {

        ResponseCommonAPI res = new ResponseCommonAPI();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setShip(null);
        orderEntity.setCustomerName(orderVO.getName());
        orderEntity.setCustomerPhone(orderVO.getPhone());
        orderEntity.setDeliveryAddress(orderVO.getAddress());
        orderEntity.setNote(orderVO.getNote());
        orderEntity.setStatus(Integer.parseInt(DBConstant.OrderStatus.Pending.getValue()));
        orderEntity.setDeliveryTime(orderVO.getTransportedAt());

        // create User that bought product
        UserEntity userEntity = userRepository.findById(orderVO.getUserId()).get();
        if(userEntity!= null) {
            orderEntity.setUser(userEntity);
        } else {
            res.setSuccess(false);
            res.setMessage("User not found");
            return res;
        }

        // create supplier
        SupplierEntity supplierEntity = supplierRepository.findById(orderVO.getSupplierId()).get();
        orderEntity.setSupplier(supplierEntity);

        // save order entity
        orderEntity = orderRepository.save(orderEntity);


        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();


        // create list items of order
        for(OrderItemVO orderItemVO: orderVO.getOrderItems()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(orderEntity);
            ItemEntity itemEntity = itemRepository.findById(orderItemVO.getId()).get();
            orderItemEntity.setItem(itemEntity);
            orderItemEntity.setPriceOfficial(itemEntity.getPrice().doubleValue());
            orderItemEntity.setQuantity(orderItemVO.getQuantityCart());
            orderItemRepository.save(orderItemEntity);
            itemEntity.setQuantity(itemEntity.getQuantity()- orderItemVO.getQuantityCart());
            itemRepository.save(itemEntity);

        }

        orderEntity.setListOrderItems(orderItemEntityList);

        res.setData(convertVO(orderEntity));
        res.setSuccess(true);
        return res;

    }

    @Override
    @Transactional
    public OrderVO updateStatus(Long id, Integer status) {
        OrderEntity order = orderRepository.findById(id).get();
        order.setStatus(status);
        orderRepository.save(order);
        return convertVOWithoutOrderItem(order);
    }

    @Override
    public Page<OrderVO> getOrderByUser(UserEntity user, int page, int size, String sort) {
        Page<OrderEntity> orders;
        if (sort != null && !sort.equals("") && (sort.charAt(0) == ' ' || sort.charAt(0) == '-')) {
            String direction = sort.substring(0,1);
            String keySort = sort.substring(1,sort.length());
            orders = orderRepository.findAllByUser(user,  PageRequest.of(page, size,
                    direction.equals("-") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    keySort));
        } else {
            orders = orderRepository.findAllByUser(user, PageRequest.of(page, size));
        }



        return PageConverter.convert(orders).using(new Function<OrderEntity, OrderVO>() {
            @Override
            public OrderVO apply(OrderEntity source) {
                return convertVOWithoutOrderItem(source);
            }
        });
    }

    @Override
    public Page<OrderVO> getOrderByStatus(int status, int page, int size, String sort, Long userId) {
        Page<OrderEntity> orders;
        String direction, keySort;


        if (sort != null && !sort.equals("") && (sort.charAt(0) == ' ' || sort.charAt(0) == '-')) {
             direction = sort.substring(0,1);
             keySort = sort.substring(1,sort.length());

             SupplierEntity supplierEntity = supplierRepository.findByUser_IdAndUser_IsDeleted(userId, false);
             if(supplierEntity != null) {
                 orders = orderRepository.findAllByStatusAndIsDeletedFalseAndSupplier_Id(status, supplierEntity.getId(), PageRequest.of(page, size,
                         direction.equals("-") ? Sort.Direction.DESC : Sort.Direction.ASC,
                         keySort));
             } else {
                 orders = null;
             }
        } else {
            orders = null;
        }
        if (orders != null) {

            return PageConverter.convert(orders).using(new Function<OrderEntity, OrderVO>() {
                @Override
                public OrderVO apply(OrderEntity source) {
                    return convertVOWithoutOrderItem(source);
                }
            });
        } else {
            return null;
        }
    }

    public OrderVO convertVOWithoutOrderItem(OrderEntity order) {
        Double total = 0.0;
        if (order.getListOrderItems() != null) {
            for (OrderItemEntity orderItem: order.getListOrderItems()
                    ) {
                if (orderItem.getPriceOfficial() != null) {
                    total += orderItem.getPriceOfficial();
                }
            }
        }
        OrderVO orderVO = OrderVOBuilder.anOrderVO().withId(order.getId()).withAddress(order.getDeliveryAddress())
                .withName(order.getCustomerName()).withNote(order.getNote())
                .withPhone(order.getCustomerPhone())
                .withTransportedAt(order.getDeliveryTime())
                .withUserId(order.getUser().getId())
                .withCreatedAt(order.getCreatedDate())
                .withTotal(total).withStatus(order.getStatus())
                .build();
        return orderVO;
    }

    @Override
    public OrderVO getByID(Long id) {
        return convertVO(orderRepository.findById(id).get());
    }

    @Override
    public OrderEntity deleteItem(Long id) {
        OrderEntity order = orderRepository.findById(id).get();
        order.setIsDeleted(true);
        orderRepository.save(order);
        return order;
    }
}