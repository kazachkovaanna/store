package org.makesense.store.registration;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
