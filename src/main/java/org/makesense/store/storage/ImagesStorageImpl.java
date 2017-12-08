package org.makesense.store.storage;

import org.makesense.store.models.Product;
import org.makesense.store.models.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImagesStorageImpl implements ImagesStorage {

    @Override
    public void store(MultipartFile file, ProductDTO product) throws IOException {
        product.setImageBytes(Base64Utils.encodeToString(file.getBytes()));
    }
}
