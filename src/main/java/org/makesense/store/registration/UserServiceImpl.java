package org.makesense.store.registration;

import org.makesense.store.models.Role;
import org.makesense.store.models.User;
import org.makesense.store.models.UserDTO;
import org.makesense.store.repository.RolesRepository;
import org.makesense.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User registerNewUser(UserDTO accountDto, boolean isManager) throws UserNameExistsException{
        if(usersRepository.findByName(accountDto.getName())!= null)
            throw new UserNameExistsException("Such user already exists!");
        Role role;
        String roleName;
        if(isManager){
            roleName = "Manager";
        }
        else{
            roleName = "User";
        }
        role = rolesRepository.findByRole(roleName);
        if(role==null) role = rolesRepository.save(new Role(roleName));
        User user = new User(accountDto.getName(), encoder.encode(accountDto.getPassword()));
        user.addRole(role);
        return usersRepository.save(user);
    }

    @Override
    public List<String> getAllUsersByRoleList(String role) {
        Role role1 = rolesRepository.findByRole(role);
        return usersRepository.findUsersByRolesIsContaining(role1).stream().flatMap(user -> Stream.of(user.getName())).collect(Collectors.toList());
    }
}
