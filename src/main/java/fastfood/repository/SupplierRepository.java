package fastfood.repository;

import fastfood.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    SupplierEntity findByUser_IdAndUser_IsDeleted(Long userId, Boolean isDeleted);

    SupplierEntity findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
