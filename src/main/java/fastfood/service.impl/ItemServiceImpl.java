package fastfood.service.impl;

import fastfood.contant.DBConstant;
import fastfood.domain.CategoryDTO;
import fastfood.domain.ItemDTO;
import fastfood.entity.*;
import fastfood.repository.*;
import fastfood.service.ItemService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;


    @Override
    public boolean addItem(ItemDTO itemDTO) {
        ItemEntity itemEntity = itemDTO.toItemEntity();


        // set unit
        UnitEntity unitEntity = unitRepository.findById(itemDTO.getUnit_id()).get();
        if(unitEntity!= null) {
            itemEntity.setUnit(unitEntity);
        }

        //set supplier
        SupplierEntity supplierEntity = supplierRepository.findByIdAndIsDeleted(itemDTO.getSupplier_id(), false);
        if(supplierEntity != null) {
            itemEntity.setSupplier(supplierEntity);
        }

        //set categories
        List<CategoryEntity> listCategory = new ArrayList<>();
        CategoryEntity categoryEntity = null;
        for(Integer category_id: itemDTO.getCategories()) {
            categoryEntity = categoryRepository.findByIdAndIsDeleted( category_id, false);
            if(categoryEntity!= null) {
                listCategory.add(categoryEntity);
            }
        }

        List<ItemCategoryEntity> listItemCategoryEntity = new ArrayList<>();
        ItemCategoryEntity itemCategoryEntity = null;
        for (CategoryEntity c: listCategory) {
            itemCategoryEntity = new ItemCategoryEntity();
            itemCategoryEntity.setItem(itemEntity);
            itemCategoryEntity.setCategory(c);
            listItemCategoryEntity.add(itemCategoryEntity);
        }

        itemEntity.setListItemCategories(listItemCategoryEntity);
        itemEntity= itemRepository.save(itemEntity);
        if(itemEntity != null) {
            return true;
        }else {
            return false;
        }
    }
}
