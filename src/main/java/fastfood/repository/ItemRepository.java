package fastfood.repository;

import fastfood.entity.ItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findBySupplier_IdAndSupplier_IsDeletedAndIsDeletedFalseAndIsActivedTrue(Long supplierId, Boolean isDeleted);

    @Query("select  listItemsEntity from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false and categoryEntity.id = ?2 " +
            "and listItemsEntity.isDeleted = false")
    List<ItemEntity> findBySupplierAndCategoryAndPagination(Long supplierId, Integer categoryId, Pageable pageable);

    @Query("select  count(listItemsEntity) from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false and categoryEntity.id = ?2 " +
            "and listItemsEntity.isDeleted = false")
    Integer getTotalItemWithSupplierAndCategory(Long supplierId, Integer categoryId);
}
