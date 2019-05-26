package fastfood.service;

import fastfood.entity.ItemEntity;
import fastfood.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query(value = "select item_id from (select item_id, count(item_id) as sl from order_items GROUP BY item_id) as temp ORDER by temp.sl", nativeQuery = true)
    List<Integer> getIDBest();

    List<OrderItemEntity> findAllByItem(ItemEntity item);
}
