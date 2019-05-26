package fastfood.service;

import fastfood.domain.ItemDTO;
import fastfood.domain.ItemResponse;
import fastfood.domain.ResponseCommonAPI;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    public boolean addItem(ItemDTO itemDTO);

    ResponseCommonAPI getListProductOfStores(Long storeId, Integer categoryId, Pageable pageable) throws Exception;
}
