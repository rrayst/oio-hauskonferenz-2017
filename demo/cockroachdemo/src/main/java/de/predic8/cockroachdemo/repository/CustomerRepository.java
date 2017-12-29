package de.predic8.cockroachdemo.repository;

import de.predic8.cockroachdemo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "select c.name, o.total from customers c inner join orders o on c.id = o.customer where c.id = :customerId", nativeQuery = true)
    public List<Object[]> findOrder1(@Param("customerId") int customerId);

    @Query(value = "select c.name, o.total from customers c inner join orders o on c.id = o.customer where c.id = :customerId and o.customer = :customerId2", nativeQuery = true)
    public List<Object[]> findOrder2(@Param("customerId") int customerId, @Param("customerId2") int customerId2);
}
