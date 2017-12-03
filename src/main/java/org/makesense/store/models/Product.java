package org.makesense.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String image;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String parameters;
    private Double price;
    private Integer rating;
    private String[] tags;
    private String category;
    private String sales;
    private Integer ammount;

    public Product(String image, String name, String shortDescription, String fullDescription, String parameters, Double price, Integer rating, String[] tags, String category, String sales, Integer ammount) {
        this.image = image;
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.parameters = parameters;
        this.price = price;
        this.rating = rating;
        this.tags = tags;
        this.category = category;
        this.sales = sales;
        this.ammount = ammount;
    }
}
