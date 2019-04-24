package fastfood.repository;

import fastfood.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    SupplierEntity findByUser_IdAndUser_IsDeleted(Long userId, Boolean isDeleted);

    SupplierEntity findByIdAndIsDeleted(Long id, Boolean isDeleted);

    @Query("select distinct supplierEntity from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as itemEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category category " +
            "where  supplierEntity.isDeleted = ?1 " +
            "and (lower(itemEntity.name) like ?2 or lower(category.name) like ?3 and category.id in ?4)")
    List<SupplierEntity> searchSupplierEntityWithListCategoriesId(Boolean isDeleted, String productName, String categoryName, List<Integer> listIds);

    @Query("select distinct supplierEntity from SupplierEntity as supplierEntity " +
            "inner join supplierEntity.listItems as itemEntity " +
            "inner join supplierEntity.listSupplierCategories as supplierCategoryEntity " +
            "inner join supplierCategoryEntity.category category " +
            "where  supplierEntity.isDeleted = ?1 " +
            "and (lower(itemEntity.name) like ?2 or lower(category.name) like ?3)")
    List<SupplierEntity> searchSupplierEntity(Boolean isDeleted, String productName, String categoryName);

}
