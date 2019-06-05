package fastfood.controller;

import fastfood.config.TokenProvider;
import fastfood.contant.DefineConstant;
import fastfood.domain.*;
import fastfood.service.GuessService;
import fastfood.service.ItemService;
import fastfood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/guess")
@RestController
public class GuessController {


    @Autowired
    private GuessService guessService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SupplierService supplierService;


//    @RequestMapping(value = "/products/store/{id}", method = RequestMethod.GET)
//    public ResponseEntity<ResponseCommonAPI> getListProductOfSupplier(@Valid @NotNull @PathVariable("id") Long id) {
//        ResponseCommonAPI res = new ResponseCommonAPI();
//        try {
//            List<ItemResponse> listProducts = itemService.getListProductOfStores(id);
//            res.setSuccess(true);
//            res.setData(listProducts);
//            return ResponseEntity.ok(res);
//        } catch (Exception e) {
//            res.setSuccess(false);
//            res.setMessage(e.getMessage());
//        }
//        if(res.getSuccess()) {
//            return ResponseEntity.ok(res);
//        } else {
//            return ResponseEntity.badRequest().body(res);
//        }
//    }

    @RequestMapping(value = "/stores/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseCommonAPI> searchListStore(
            @RequestBody SearchStoreDomain searchStoreDomain,
            @RequestParam("page") Integer page
            ) {
        ResponseCommonAPI res = null ;
        Pageable pageable =  PageRequest.of((page == null || page < 1) ? 0 : page-1, DefineConstant.PAGESIZE);
        try {
            res  = supplierService.searchSupplier(searchStoreDomain, pageable);
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


    @RequestMapping(value = "/stores/{id}/items" )
    public ResponseEntity<ResponseCommonAPI> searchProductOfSpecifyStore(
            @RequestParam("page") Integer page,
            @RequestParam("category") Integer category,
            @PathVariable("id") Long storeId
    ) {
        ResponseCommonAPI res = null;
        Pageable pageable = PageRequest.of(page== null || page < 1 ? 0 : page - 1, DefineConstant.PAGESIZE);

        try {
            res = itemService.getListProductOfStores(storeId, category, pageable);
        } catch (Exception e) {
            res = new ResponseCommonAPI();
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }

        if(res.getSuccess()) {
            return  ResponseEntity.ok(res);
        }else {
            return ResponseEntity.badRequest().body(res);
        }

    }


    @RequestMapping(value = "/register/saler", method = RequestMethod.POST)
    public ResponseEntity<ResponseCommonAPI> registerAccountSaler(RegisterSalerAccountDTO registerSalerAccountDTO, @RequestParam("files") MultipartFile[] files) {
        ResponseCommonAPI res = new ResponseCommonAPI();

        List<ErrorCustom> listErrors = registerSalerAccountDTO.validateData();
        if(!CollectionUtils.isEmpty(listErrors)) {
            res.setSuccess(false);
            res.setError(listErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }

        try {
            res = guessService.registerSalerAccount(registerSalerAccountDTO, files);
            if(res.getSuccess()) {
                return ResponseEntity.ok(res);
            } else {
                return ResponseEntity.badRequest().body(res);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
    }


}
