package org.makesense.store.security;

import org.makesense.store.models.User;
import org.makesense.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new MyUserPrincipal(user);
    }
}
