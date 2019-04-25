package fastfood.service;

import fastfood.domain.ItemDTO;
import fastfood.domain.ItemResponse;

import java.util.List;

public interface ItemService {

    public boolean addItem(ItemDTO itemDTO);

    List<ItemResponse> getListProductOfStores(Long id) throws Exception;
}
