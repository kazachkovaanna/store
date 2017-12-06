package org.makesense.store.models;


import lombok.Getter;
import lombok.Setter;
import org.makesense.store.registration.PasswordMatches;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
public class UserDTO {
    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String matchingPassword;
}
