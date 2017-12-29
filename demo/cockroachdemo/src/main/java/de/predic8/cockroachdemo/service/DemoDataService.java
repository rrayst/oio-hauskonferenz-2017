package de.predic8.cockroachdemo.service;

import de.predic8.cockroachdemo.model.Customer;
import de.predic8.cockroachdemo.model.Order;
import de.predic8.cockroachdemo.model.OrderId;
import de.predic8.cockroachdemo.repository.CustomerRepository;
import de.predic8.cockroachdemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DemoDataService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DemoDataService demoDataService;

    @Autowired
    EntityManager entityManager;

    @Transactional
    public void createCustomerAndOrder(int initial, int customerOffset, String namePrefix, int ordersPerCustomer, int threads, int transactionSize) {
        for (int k = 0; k < transactionSize; k++) {
            int i = initial + k * threads;
            Customer c = new Customer();

            c.setId(i + customerOffset);
            c.setName(namePrefix + i);

            entityManager.persist(c);

            for (int j = 0; j < ordersPerCustomer; j++) {
                OrderId oid = new OrderId();
                oid.setId((i + customerOffset) * ordersPerCustomer + j);
                oid.setCustomer(c);

                Order o = new Order();
                o.setOrderId(oid);
                o.setTotal(new BigDecimal(j / 10.0));

                entityManager.persist(o);
            }
        }
    }

    public void create(int customerCount,
                       int customerOffset,
                       int ordersPerCustomer,
                       int nameLength,
                       int transactionSize) {

        int threads = 20;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nameLength; i++)
            sb.append('a');
        String namePrefix = sb.toString();

        AtomicReference<Exception> exception = new AtomicReference<>();

        for (int thread = 0; thread < threads; thread++) {
            int theThread = thread;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = theThread; i < customerCount; i += threads * transactionSize) {
                            demoDataService.createCustomerAndOrder(i, customerOffset, namePrefix, ordersPerCustomer, threads, transactionSize);
                        }
                    } catch (Exception e) {
                        exception.set(e);
                    }
                }
            });
        }

        try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);
            if (exception.get() != null)
                throw new RuntimeException(exception.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
