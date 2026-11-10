package nichy.beancafe.repository;

import nichy.beancafe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusOrderByCreatedAtDesc(Order.OrderStatus status);
    List<Order> findAllByOrderByCreatedAtDesc();
}
