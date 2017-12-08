package org.makesense.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String image;
    private Double price;
    private Integer ammount;
    private String imageBytes;

    public ProductDTO(Product product){
        name = product.getName();
        shortDescription = product.getShortDescription();
        fullDescription = product.getFullDescription();
        price = product.getPrice();
        ammount = product.getAmmount();
        imageBytes = product.getImageBytes();
    }
}
