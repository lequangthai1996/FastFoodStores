package fastfood.controller;

import fastfood.domain.CategoryDTO;
import fastfood.domain.ResponseCommonAPI;
import fastfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    public ResponseEntity<ResponseCommonAPI> getAllCategories() {
        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            List<CategoryDTO> listCategory = categoryService.getAllCategory();
            res.setSuccess(true);
            res.setData(listCategory);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }


    @RequestMapping("/{storeID}")
    public ResponseEntity<ResponseCommonAPI> getCategoryOfSpecifyStore(@PathVariable("storeID") Long storeID) {
        ResponseCommonAPI res = new ResponseCommonAPI();
        try {
            List<CategoryDTO> listCategories = categoryService.getCategoriesOfStore(storeID);
            res.setSuccess(true);
            res.setData(listCategories);
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

