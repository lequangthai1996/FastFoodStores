package fastfood.service.impl;

import com.google.common.base.Function;
import fastfood.contant.DBConstant;
import fastfood.contant.DefineConstant;
import fastfood.domain.*;
import fastfood.entity.*;
import fastfood.repository.*;
import fastfood.service.ItemService;
import fastfood.utils.PageConverter;
import fastfood.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public  class ItemServiceImpl implements ItemService {

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
        UnitEntity unitEntity = unitRepository.getOne(Long.parseLong(itemDTO.getUnit().getId()));
        if(unitEntity!= null) {
            itemEntity.setUnit(unitEntity);
        }

        //set supplier
        SupplierEntity supplierEntity = supplierRepository.findByUser_IdAndUser_IsDeleted(StringUtils.convertStringToLongOrNull(itemDTO.getCurrentUserId()), false);
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

        List<ItemEntity> listItemEntity = null;
        if(categoryId == 0) {
            listItemEntity = itemRepository.findBySupplierAndPagination(storeId, pageable);
        } else {
           listItemEntity = itemRepository.findBySupplierAndCategoryAndPagination(storeId, categoryId, pageable);
        }
        List<ItemResponse> listItemResponse = listItemEntity.stream().map(t-> {
            ItemResponse itemResponse = new ItemResponse();
            itemResponse.setId(StringUtils.convertObjectToString(t.getId()));
            itemResponse.setName(t.getName());
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


        Integer totalItems = 0;

        if(categoryId == 0) {
            totalItems = itemRepository.getTotalItemWithSupplier(storeId);
        } else {
            totalItems = itemRepository.getTotalItemWithSupplierAndCategory(storeId, categoryId);
        }
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(true);
        res.setData(listItemResponse);
        res.setTotalPages((int)Math.ceil((float)totalItems/ DefineConstant.PAGESIZE));
        res.setNumber(pageable.getPageNumber()+1);


        return res;
    }

    @Override
    public Page<ItemVO> searchItems(int page, int size, String sort, Long userID) throws Exception{
        SupplierEntity supplierEntity = supplierRepository.findByUser_IdAndUser_IsDeleted(userID,false);
        String direction = sort.substring(0,1);
        String keySort = sort.substring(1,sort.length());
        Page<ItemEntity> items = itemRepository.findBySupplier_Id(supplierEntity.getId(), PageRequest.of(page, size,
                direction.equals("D") ? Sort.Direction.DESC : Sort.Direction.ASC,
                keySort));

        return PageConverter.convert(items).using(new Function<ItemEntity, ItemVO>() {
            @Override
            public ItemVO apply(ItemEntity source) {
                return convertVO(source);
            }
        });
    }

    @Override
    public Page<ItemVO> searchItemsByCategory(Long supplierID, Integer categoryId, int page, int size, String sort) {
        String direction = sort.substring(0,1);
        String keySort = sort.substring(1,sort.length());

        Pageable pageable = PageRequest.of(page, size, direction.equals("-") ? Sort.Direction.DESC : Sort.Direction.ASC);

        Page<ItemEntity> itemEntities = itemRepository.searchItemBySupplierAndCategory(supplierID, categoryId, pageable);

        return PageConverter.convert(itemEntities).using(new Function<ItemEntity, ItemVO>() {
            @Override
            public ItemVO apply(ItemEntity itemEntity) {
                return convertVO(itemEntity);
            }
        });

    }


    public ItemVO convertVO(ItemEntity item) {
        ItemVO itemVO = new ItemVO();
        itemVO.setId(item.getId());
        itemVO.setName(item.getName());
        itemVO.setPrice(item.getPrice());
        itemVO.setQuantity(item.getQuantity());
        itemVO.setCreatedAt(item.getCreatedDate());
        return itemVO;
    }




}
