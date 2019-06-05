package fastfood.service.impl;

import fastfood.contant.DBConstant;
import fastfood.domain.CategoryDTO;
import fastfood.domain.ItemVO;
import fastfood.entity.CategoryEntity;
import fastfood.repository.CategoryRepository;
import fastfood.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> listCategoryEntity = categoryRepository.findByLevelAndIsDeletedFalse(DBConstant.LEVEL.PARENT.getValue());

        List<CategoryDTO> listCatogoryDTO = listCategoryEntity.stream().map(t -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(t.getId());
            categoryDTO.setName(t.getName());
            categoryDTO.setDescription(t.getDesciption());
            return  categoryDTO;
        }).collect(Collectors.toList());
        return listCatogoryDTO;
    }

    @Override
    public List<CategoryDTO> getCategoriesOfStore(Long storeID) {

        List<CategoryEntity> listCategoriesEntity = categoryRepository.findByListSupplierCategories_Supplier_IdAndIsDeletedFalseAndLevel(storeID, DBConstant.LEVEL.PARENT.getValue());
        List<CategoryDTO> categoryDTOList = listCategoriesEntity.stream().map( t-> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(t.getId());
            categoryDTO.setName(t.getName());
            categoryDTO.setDescription(t.getDesciption());
            return  categoryDTO;
        }).collect(Collectors.toList());
        return categoryDTOList;
    }


}
