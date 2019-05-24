package fastfood.service;

import fastfood.domain.ResponseCommonAPI;
import fastfood.domain.SearchStoreDomain;
import fastfood.domain.SupplierResponse;
import fastfood.entity.SupplierEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SupplierService {
    ResponseCommonAPI searchSupplier(SearchStoreDomain searchStoreDomain, Pageable pageable) throws Exception;

}
