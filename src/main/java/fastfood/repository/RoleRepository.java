package fastfood.repository;

import fastfood.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String roleName);

    Set<RoleEntity> findByNameInAndIsDeletedFalse(List<String> names);
}
