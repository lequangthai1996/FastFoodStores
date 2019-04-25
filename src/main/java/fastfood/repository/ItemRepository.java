package fastfood.repository;

import fastfood.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findBySupplier_IdAndSupplier_IsDeletedAndIsDeletedFalseAndIsActivedTrue(Long supplierId, Boolean isDeleted);
}
