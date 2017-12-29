package de.predic8.cockroachdemo.repository;

import de.predic8.cockroachdemo.model.Order;
import de.predic8.cockroachdemo.model.OrderId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, OrderId> {

    @Query("select e from orders e left join       e.orderId.customer b where e.orderId.id = ?1 and b.id=?2")
    Order findOneByCustomerAndId(Integer i, Integer j);
}
