package org.makesense.store.registration;

public class UserNameExistsException extends Exception {
    public UserNameExistsException(String message) {
        super(message);
    }
}
