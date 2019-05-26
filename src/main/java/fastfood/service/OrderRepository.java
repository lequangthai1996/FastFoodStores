package fastfood.service;

import fastfood.entity.OrderEntity;
import fastfood.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
        Page<OrderEntity> findAllByUser(UserEntity user, Pageable pageable);

        Page<OrderEntity> findAllByStatus(byte status, Pageable pageable);
}
