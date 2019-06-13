package fastfood.service;

import fastfood.entity.OrderEntity;
import fastfood.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
        Page<OrderEntity> findAllByUser(UserEntity user, Pageable pageable);

        OrderEntity findByIdAndUser_IdAndIsDeletedFalse(Long orderId, Long userId);

        List<OrderEntity> findByUser_IdAndIsDeletedFalseOrderByCreatedDateDesc(Long userId);

        Page<OrderEntity> findAllByStatusAndIsDeletedFalseAndSupplier_Id(int status, Long supplierId, Pageable pageable);
}
