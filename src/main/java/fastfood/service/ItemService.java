package fastfood.service;

import fastfood.domain.ItemDTO;
import fastfood.domain.ItemResponse;
import fastfood.domain.ItemVO;
import fastfood.domain.ResponseCommonAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    public boolean addItem(ItemDTO itemDTO);

    ResponseCommonAPI getListProductOfStores(Long storeId, Integer categoryId, Pageable pageable) throws Exception;

    Page<ItemVO> searchItems(int page, int size, String sort, Long supplierID) throws  Exception;

    Page<ItemVO> searchItemsByCategory(Long supplierID, Integer categoryId, int page, int size, String sort) throws Exception;
}
