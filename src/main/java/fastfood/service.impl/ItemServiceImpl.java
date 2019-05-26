package fastfood.service.impl;

import fastfood.contant.DBConstant;
import fastfood.contant.DefineConstant;
import fastfood.domain.*;
import fastfood.entity.*;
import fastfood.repository.*;
import fastfood.service.ItemService;
import fastfood.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
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
        UnitEntity unitEntity = unitRepository.getOne(itemDTO.getUnit_id());
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
    public ResponseCommonAPI getListProductOfStores(Long storeId, Integer categoryId, Pageable pageable) throws Exception{

        List<ItemEntity> listItemEntity = itemRepository.findBySupplierAndCategoryAndPagination(storeId, categoryId, pageable);

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

        Integer totalItems = itemRepository.getTotalItemWithSupplierAndCategory(storeId, categoryId);

        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(true);
        res.setData(listItemResponse);
        res.setTotalPages((int)Math.ceil((float)totalItems/ DefineConstant.PAGESIZE));
        res.setNumber(pageable.getPageNumber()+1);


        return res;
    }


    public ItemVO convertVO(ItemEntity item) {

//        List<PromotionItemVO> promotionItemVOS = new ArrayList<>();
//        if (item.getPromotionItems() != null) {
//            for (PromotionItem promotionItem: item.getPromotionItems()
//            ) {
//                PromotionItemVO promotionItemVO = PromotionItemVOBuilder.aPromotionItemVO()
//                        .withId(promotionItem.getId())
//                        .withPercent(promotionItem.getPercent()).build();
//                promotionItemVOS.add(promotionItemVO);
//            }
//        }
//        Set<CategoryVO> categoryVOSet = new HashSet<>();
//        for(Category category: item.getCategories()){
//            CategoryVO categoryVO = CategoryVOBuilder.aCategoryVO()
//                    .withId(category.getId())
//                    .withLevelCategory(category.getLevelCategory())
//                    .withParentId(category.getParentId())
//                    .withDescription(category.getDescription())
//                    .withName(category.getName())
//                    .build();
//            categoryVOSet.add(categoryVO);
//        }
//        UnitVO unitVO = UnitVOBuilder.anUnitVO().withId(item.getUnit().getId())
//                .withName(item.getUnit().getName())
//                .withSyntax(item.getUnit().getSyntax())
//                .build();
//        List<ImageItemVO> imageItemVOS = new ArrayList<>();
//        if (item.getImageItems() != null) {
//            for (ImageItem imageItem: item.getImageItems()) {
//                ImageItemVO imageItemVO = new ImageItemVO();
//                imageItemVO.setId(imageItem.getId());
//                imageItemVO.setImage(imageItem.getImage());
//                imageItemVOS.add(imageItemVO);
//            }
//        }
//        ItemVO itemVO = ItemVOBuilder.anItemVO().withId(item.getId())
//                .withName(item.getName())
//                .withPrice(item.getPrice())
//                .withAvatar(item.getAvatar())
//                .withDescription(item.getDescription())
//                .withQuantity(item.getQuantity())
//                .withPromotions(promotionItemVOS)
//                .withCreatedAt(item.getCreatedAt())
//                .withCategory(categoryVOSet)
//                .withImageItems(imageItemVOS).withUnit(unitVO)
//                .build();
//        return itemVO;
        return new ItemVO();
    }




}
