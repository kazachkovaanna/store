package org.makesense.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String lastName;
    private String email;
    private Set<Role> roles;

    public User(String name, String password, String email, String lastName, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.lastName = lastName;
    }

    public User(String name, String password, String email, String lastName) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        roles = new HashSet<>();
    }
    public void addRole(Role role){
        roles.add(role);
    }
}
