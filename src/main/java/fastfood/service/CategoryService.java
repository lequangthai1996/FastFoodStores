package fastfood.service;

import fastfood.domain.CategoryDTO;
import fastfood.domain.ItemVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategory();

    List<CategoryDTO> getCategoriesOfStore(Long storeID);

}
