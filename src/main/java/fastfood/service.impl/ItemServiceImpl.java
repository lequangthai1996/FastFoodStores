package fastfood.service.impl;

import fastfood.contant.DBConstant;
import fastfood.domain.CategoryDTO;
import fastfood.domain.ItemDTO;
import fastfood.domain.ItemResponse;
import fastfood.entity.*;
import fastfood.repository.*;
import fastfood.service.ItemService;
import fastfood.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FileStorageService fileStorageService;


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

    @Override
    public List<ItemResponse> getListProductOfStores(Long id) throws Exception{

        List<ItemEntity> listItemEntity = itemRepository.findBySupplier_IdAndSupplier_IsDeletedAndIsDeletedFalseAndIsActivedTrue(id, false);

        List<ItemResponse> listItemResponse = listItemEntity.stream().map(t-> {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setId(StringUtils.convertObjectToString(t.getId()));
            itemResponse.setAvatar(t.getAvatar());
            itemResponse.setQuantity(StringUtils.convertObjectToString(t.getQuantity()));
            itemResponse.setDescription(t.getDescription());
            itemResponse.setPrice(StringUtils.convertObjectToString(t.getPrice()));
            itemResponse.setUnit(t.getUnit() != null ? t.getUnit().convertToUnitDTO() : null);

            //Get base64 of image
            if(!StringUtils.isEmpty(t.getAvatar())) {
               itemResponse.setAvatar(fileStorageService.loadImageBase64(t.getAvatar()));
            }
            return itemResponse;
        }).collect(Collectors.toList());


        return listItemResponse;
    }




}
