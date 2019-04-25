package fastfood.service;

import fastfood.domain.SearchStoreDomain;
import fastfood.domain.SupplierResponse;
import fastfood.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> searchSupplier(SearchStoreDomain searchStoreDomain);
}
