package fastfood.controller;

import fastfood.domain.ResponseCommonAPI;
import fastfood.domain.SearchStoreDomain;
import fastfood.domain.SupplierResponse;
import fastfood.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/store")
@RestController
public class StoreController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseCommonAPI> searchListStore(@RequestBody SearchStoreDomain searchStoreDomain) {
        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            List<SupplierResponse>  listSupplier = supplierService.searchSupplier(searchStoreDomain);
            res.setSuccess(true);
            res.setData(listSupplier);
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
}
