package org.makesense.store.editing;

import org.makesense.store.models.Product;
import org.makesense.store.models.ProductDTO;
import org.makesense.store.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Product registerNewProduct(ProductDTO productDTO) throws ProductNameExistsException {
        Product product = productsRepository.findByName(productDTO.getName());
        if(product != null) throw new ProductNameExistsException("Продукт с таким именем уже существует!");
        return saveProduct(new Product(), productDTO);
    }

    private Product saveProduct(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setShortDescription(productDTO.getShortDescription());
        product.setFullDescription(productDTO.getFullDescription());
        product.setPrice(productDTO.getPrice());
        product.setAmmount(productDTO.getAmmount());
        product.setImageBytes(productDTO.getImageBytes());
        return productsRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) throws ProductNameNotExistsException {
        Product product = productsRepository.findByName(productDTO.getName());
        if(product == null) throw new ProductNameNotExistsException("Продукта с таким именем не найдено!");
        return saveProduct(product, productDTO);
    }
}
