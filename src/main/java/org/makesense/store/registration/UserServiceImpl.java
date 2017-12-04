package org.makesense.store.registration;

import org.makesense.store.models.Role;
import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;
import org.makesense.store.repository.RolesRepository;
import org.makesense.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User registerNewUser(UserDTO accountDto) throws UserNameExistsException{
        if(usersRepository.findByName(accountDto.getName())!= null)
            throw new UserNameExistsException("Such user already exists!");
        Role role = rolesRepository.findByRole("User");
        if(role==null) role = rolesRepository.save(new Role("User"));
        User user = new User(accountDto.getName(), encoder.encode(accountDto.getPassword()));
        user.addRole(role);
        return usersRepository.save(user);
    }
}
