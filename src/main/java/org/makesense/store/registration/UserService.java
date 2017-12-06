package org.makesense.store.registration;

import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;

import java.util.List;

public interface UserService {
    User registerNewUser(UserDTO accountDto, boolean isManager) throws UserNameExistsException;

    List<String> getAllUsersByRoleList(String role);

}
