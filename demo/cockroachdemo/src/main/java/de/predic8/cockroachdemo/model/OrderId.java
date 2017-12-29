package de.predic8.cockroachdemo.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {

    @JoinColumn(name = "customer")
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    private int id;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}