package fastfood.repository;

import fastfood.entity.SupplierCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCategoryRepository extends JpaRepository<SupplierCategoryEntity, Long> {

}
