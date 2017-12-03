package org.makesense.store.cart;

import org.makesense.store.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class Cart {
    private List<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }
    public void add(Product product){
        products.add(product);
    }

    public void remove(String name){
        final Product[] toDelete = {null};
        products.stream().forEach(product -> {
            if(product.getName().equals(name)) toDelete[0] = product;
        });
        if(toDelete[0]!=null)products.remove(toDelete[0]);
    }
    public double summ(){
        return products.stream().flatMapToDouble(product -> DoubleStream.of(product.getPrice())).sum();
    }
}
