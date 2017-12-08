package org.makesense.store.storage;

import org.makesense.store.models.Product;
import org.makesense.store.models.ProductDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface ImagesStorage {
    void store(MultipartFile file, ProductDTO product) throws IOException;
}
