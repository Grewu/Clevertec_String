package org.example.util;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.entity.Customer;
import org.example.entity.Order;
import org.example.entity.Product;

import java.time.LocalDate;

import java.time.Month;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class TestDataProduct {
    @Builder.Default
    private UUID uuid = UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671");
    @Builder.Default
    private String firstName = "firstName";
    @Builder.Default
    private String name = "name";
    @Builder.Default
    private String lastName = "firstName";
    @Builder.Default
    private LocalDate dateBirth = LocalDate.of(2023, Month.DECEMBER, 26);
    @Builder.Default
    private List<Order> orders = List.of(new Order
            (UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671"),
                    List.of(new Product
                            (UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671"), "name", 120.0)),
                    OffsetDateTime.MIN));
    @Builder.Default
    private Double price = 120.0;
    @Builder.Default
    private OffsetDateTime offsetDateTime = OffsetDateTime.MIN;
    @Builder.Default
    private List<Product> products = List.of(new Product
            (UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671"),
                    "name", 120.0));
    @Builder.Default
    private List<Product> productsList = List.of(new Product
            (UUID.fromString("b6e1d925-ebca-458e-b032-c3dd2b8f1671"),
                    "name", 120.0));

    public Product buildProduct() {
        return new Product(uuid, name, price);
    }
    public Product buildEmptyProduct() {
        return new Product();
    }


    public Order buildOrder() {
        return new Order(uuid, products, offsetDateTime);
    }

    public Customer buidCustomer() {
        return new Customer(uuid, firstName, lastName, dateBirth, orders);
    }
}

