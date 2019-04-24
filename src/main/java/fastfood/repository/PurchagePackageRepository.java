package fastfood.repository;

import fastfood.entity.PurchasePackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchagePackageRepository extends JpaRepository<PurchasePackageEntity, Integer> {

    PurchasePackageEntity findByIdAndIsDeletedFalse(Integer id);
}
