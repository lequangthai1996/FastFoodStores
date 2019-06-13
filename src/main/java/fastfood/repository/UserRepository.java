package fastfood.repository;

import fastfood.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndIsDeletedFalse(String username);

    UserEntity findByIdAndIsDeletedFalse(Long id);

}
