package fastfood.service.impl;

import fastfood.domain.SearchStoreDomain;
import fastfood.domain.SupplierResponse;
import fastfood.entity.SupplierEntity;
import fastfood.repository.SupplierRepository;
import fastfood.service.SupplierService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Override
    public List<SupplierResponse> searchSupplier(SearchStoreDomain searchStoreDomain) {
        String keySearch = null;
        String searchWord = null;
        if(!StringUtils.isEmpty(searchStoreDomain.getKeySearch())) {
            keySearch = searchStoreDomain.getKeySearch().trim();
        }
        if(!StringUtils.isEmpty(keySearch)) {
            searchWord = "%"+ keySearch.toLowerCase() + "%";
        } else {
            searchWord = "%%";
        }
        List<SupplierEntity> listSuppliers = null;
        if(!CollectionUtils.isEmpty(searchStoreDomain.getCategories())) {
            listSuppliers = supplierRepository.searchSupplierEntityWithListCategoriesId(false, searchWord, searchWord, searchStoreDomain.getCategories());
        } else {
            listSuppliers = supplierRepository.searchSupplierEntity(false, searchWord, searchWord);
        }

        if(!CollectionUtils.isEmpty(listSuppliers)) {
            return listSuppliers.stream().map( t-> {
                SupplierResponse supplierResponse = new SupplierResponse();
                supplierResponse.setId(t.getId().toString());
                supplierResponse.setName(t.getName());
                supplierResponse.setBackgroundImage(t.getBackgroundImage());
                supplierResponse.setStoreAddress(t.getStoreAddress());
                return supplierResponse;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
