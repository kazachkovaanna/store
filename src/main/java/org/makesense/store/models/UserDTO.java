package org.makesense.store.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.makesense.store.registration.PasswordMatches;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String matchingPassword;

    public UserDTO(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = null;
        this.matchingPassword = null;
    }

    public UserDTO(User user){
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = null;
        this.matchingPassword = null;
    }
}
