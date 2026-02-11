package in.ashokit.repo;

import in.ashokit.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, Integer> {
}
