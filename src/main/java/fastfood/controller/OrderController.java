package fastfood.controller;

import fastfood.domain.OrderVO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

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
}
