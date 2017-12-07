package org.makesense.store.editing;

public class ProductNameExistsException extends Exception {
    public ProductNameExistsException(String message) {
        super(message);
    }
}
