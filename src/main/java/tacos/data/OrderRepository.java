package tacos.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import tacos.Order;
import tacos.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
//    Order save(Order order);
    List<Order> findByUserOrderByPlacedAtDesc(User user, PageRequest pageable);
}
