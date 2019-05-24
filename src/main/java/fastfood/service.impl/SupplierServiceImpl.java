package fastfood.service.impl;

import fastfood.contant.DefineConstant;
import fastfood.domain.ResponseCommonAPI;
import fastfood.domain.SearchStoreDomain;
import fastfood.domain.SupplierResponse;
import fastfood.entity.SupplierEntity;
import fastfood.repository.SupplierRepository;
import fastfood.service.SupplierService;
import fastfood.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Override
    public ResponseCommonAPI searchSupplier(SearchStoreDomain searchStoreDomain, Pageable pageable) throws  Exception{
        String keySearch = null;
        String searchWord = null;
        Integer totalRows = 0;
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
            listSuppliers = supplierRepository.searchSupplierEntityWithListCategoriesId(false, searchWord, searchWord, searchStoreDomain.getCategories(), pageable);
            totalRows = supplierRepository.searchSupplierEntityWithListCategorieIdReturnTotalPages(false, searchWord, searchWord, searchStoreDomain.getCategories());

        } else {
            listSuppliers = supplierRepository.searchSupplierEntity(false, searchWord, searchWord, pageable);
            totalRows = supplierRepository.searchSupplierEntityReturnTotalPages(false, searchWord, searchWord);
        }

        List<SupplierResponse> supplierResponseList = null;
        if(!CollectionUtils.isEmpty(listSuppliers)) {
            supplierResponseList = listSuppliers.stream().map( t-> {
                SupplierResponse supplierResponse = new SupplierResponse();
                supplierResponse.setId(t.getId().toString());
                supplierResponse.setName(t.getName());
                supplierResponse.setBackgroundImage(t.getBackgroundImage());
                supplierResponse.setStoreAddress(t.getStoreAddress());
                return supplierResponse;
            }).collect(Collectors.toList());
        }

        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(true);
        res.setData(supplierResponseList);
        res.setTotalPages((int)Math.ceil((float)totalRows/ DefineConstant.PAGESIZE));
        res.setNumber(pageable.getPageNumber()+1);

        return res;
    }
}
