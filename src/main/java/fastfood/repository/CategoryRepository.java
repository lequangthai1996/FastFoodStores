package fastfood.repository;

import fastfood.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    public CategoryEntity findByIdAndIsDeleted(Integer id, Boolean isDeleted);
}
