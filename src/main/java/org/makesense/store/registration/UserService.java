package org.makesense.store.registration;

import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;

public interface UserService {
    User registerNewUser(UserDTO accountDto) throws UserNameExistsException;
}
