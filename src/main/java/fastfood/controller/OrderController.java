package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.OrderItemVO;
import fastfood.domain.OrderVO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.entity.OrderEntity;
import fastfood.entity.UserEntity;
import fastfood.repository.UserRepository;
import fastfood.service.OrderItemService;
import fastfood.service.OrderService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemService orderItemService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCommonAPI> deleteOrder(@PathVariable("id") Long id) {
        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            OrderEntity orderEntity = orderService.deleteItem(id);
            if(orderEntity != null) {
                res.setSuccess(true);
            }else {
                res.setSuccess(false);
            }
        } catch ( Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PostMapping("/createOrder")
    public ResponseEntity<ResponseCommonAPI> createOrder(@RequestBody OrderVO orderVO) {

        ResponseCommonAPI res = null;
        try {
            res = orderService.createOrder(orderVO);
        } catch (Exception e) {
            res = new ResponseCommonAPI();
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        }else {
            return ResponseEntity.badRequest().body(res);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<ResponseCommonAPI> getListOrder(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("status") int status,
            @RequestParam(value = "sort", required = false) String sort
    ) {

        String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        ResponseCommonAPI res = null;
        try {
            Page<OrderVO>  pageOrder = orderService.getOrderByStatus(status, page, size,sort, StringUtils.convertStringToLongOrNull(currentUserID));
            res = new ResponseCommonAPI();
            res.setSuccess(true);
            res.setData(pageOrder);
        } catch (Exception e) {
            res = new ResponseCommonAPI();
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommonAPI> getDetailOrder(@PathVariable("id") Long id){
        String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        ResponseCommonAPI res = res = new ResponseCommonAPI();;
        try {
            OrderVO  orderVO = orderService.getByID(id);
            if(orderVO != null) {
                res.setSuccess(true);
                res.setData(orderVO);
            } else {
                res.setSuccess(false);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/user/{orderId}")
    public ResponseEntity<ResponseCommonAPI> getOrderDetail(@PathVariable("orderId") Long orderId) {
        String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        ResponseCommonAPI res = res = new ResponseCommonAPI();
        try {

            OrderVO orderVO = orderService.getByID(orderId);

            res.setSuccess(true);
            res.setData(orderVO);

        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping("/{orderID}/status/{status}")
    public ResponseEntity<ResponseCommonAPI> updateStatus(@PathVariable("orderID") Long orderID, @PathVariable("status") Integer status) {
        String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        ResponseCommonAPI res = res = new ResponseCommonAPI();;
        try {
            OrderVO  orderVO = orderService.updateStatus(orderID, status);
            if(orderVO != null) {
                res.setSuccess(true);
                res.setData(orderVO);
            } else {
                res.setSuccess(false);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseCommonAPI> getHistoryOrders() {

        String currentUserID = tokenProvider.getCurrentUserLogin().getId();
        ResponseCommonAPI res = null;
        try {
            List<OrderVO> listOrders = orderService.getHistoryOrderOfUser(StringUtils.convertStringToLongOrNull(currentUserID));
            res = new ResponseCommonAPI();
            res.setSuccess(true);
            res.setData(listOrders);
        } catch (Exception e) {
            res = new ResponseCommonAPI();
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.badRequest().body(res);
        }
    }
}
