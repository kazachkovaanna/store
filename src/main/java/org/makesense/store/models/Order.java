package org.makesense.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    private String id;

    private String userEmail;
    private List<Product> products;

    public Order(String userEmail, List<Product> products) {
        this.userEmail = userEmail;
        this.products = products;
    }
}
