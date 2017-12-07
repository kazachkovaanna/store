package org.makesense.store.editing;

import org.makesense.store.models.Product;
import org.makesense.store.models.ProductDTO;

public interface ProductService {
    Product registerNewProduct(ProductDTO productDTO) throws ProductNameExistsException;

    Product updateProduct(ProductDTO productDTO) throws ProductNameNotExistsException;
}
