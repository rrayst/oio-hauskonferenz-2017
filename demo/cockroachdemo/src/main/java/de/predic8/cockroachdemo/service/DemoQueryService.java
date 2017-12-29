package de.predic8.cockroachdemo.service;

import de.predic8.cockroachdemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoQueryService {

    @Autowired
    CustomerRepository customerRepository;

    public int runQuery1(int customerId) {
        return customerRepository.findOrder1(customerId).size();
    }

    public int runQuery2(int customerId) {
        return customerRepository.findOrder2(customerId, customerId).size();
    }

}
