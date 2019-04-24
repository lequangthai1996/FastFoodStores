package fastfood.repository;

import fastfood.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByIdAndIsDeleted(Integer id, Boolean isDeleted);

    List<CategoryEntity> findByIdInAndIsDeletedFalse(List<Integer> listIds);
}
