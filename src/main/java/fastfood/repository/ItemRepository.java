package fastfood.repository;

import fastfood.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findBySupplier_IdAndSupplier_IsDeletedAndIsDeletedFalseAndIsActivedTrue(Long supplierId, Boolean isDeleted);

    @Query("select distinct listItemsEntity from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false and categoryEntity.id = ?2 " +
            "and listItemsEntity.isDeleted = false and listItemsEntity.isActived = true")
    List<ItemEntity> findBySupplierAndCategoryAndPagination(Long supplierId, Integer categoryId, Pageable pageable);

    @Query("select  distinct listItemsEntity from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false " +
            "and listItemsEntity.isDeleted = false and listItemsEntity.isActived = true")
    List<ItemEntity> findBySupplierAndPagination(Long supplierId, Pageable pageable);

    @Query("select  count(distinct listItemsEntity) from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false and categoryEntity.id = ?2 " +
            "and listItemsEntity.isDeleted = false and listItemsEntity.isActived = true")
    Integer getTotalItemWithSupplierAndCategory(Long supplierId, Integer categoryId);

    @Query("select  count(distinct listItemsEntity) from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as listItemsEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category categoryEntity " +
            "where  supplierEntity.isDeleted = false and supplierEntity.id = ?1 " +
            "and categoryEntity.isDeleted = false " +
            "and listItemsEntity.isDeleted = false and listItemsEntity.isActived = true")
    Integer getTotalItemWithSupplier(Long supplierId);

    /**
     * find item of specify supplierID
     * @param supplierID
     * @return
     */
    Page<ItemEntity> findBySupplier_Id(Long supplierID, Pageable pageable);


    @Query("select distinct  itemEntity from ItemEntity  as itemEntity " +
            "inner join itemEntity.supplier as supplierEntity " +
            "inner join itemEntity.listItemCategories as itemCategoryEntity " +
            "inner join itemCategoryEntity.category as categoryEntity " +
            "where supplierEntity.id = ?1 and categoryEntity.id  = ?2")
    Page<ItemEntity> searchItemBySupplierAndCategory(Long supplierID, Integer categoryID, Pageable pageable);
}
