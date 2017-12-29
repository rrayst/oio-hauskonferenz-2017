package de.predic8.cockroachdemo.controller;

import de.predic8.cockroachdemo.service.DemoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoDataController {

    @Autowired
    DemoDataService demoDataService;

    @PostMapping("/data/demo")
    public String demo(
            @RequestParam("customerCount") int customerCount,
            @RequestParam("customerOffset") int customerOffset,
            @RequestParam("ordersPerCustomer") int ordersPerCustomer,
            @RequestParam("nameLength") int nameLength,
            @RequestParam("transactionSize") int transactionSize
            ) {
        demoDataService.create(customerCount, customerOffset, ordersPerCustomer, nameLength, transactionSize);
        return "OK";
    }
}
