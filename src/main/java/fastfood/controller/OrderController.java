package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.domain.OrderVO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.service.OrderService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenProvider tokenProvider;

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
}
