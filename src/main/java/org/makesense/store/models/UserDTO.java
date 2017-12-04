package org.makesense.store.models;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String matchingPassword;
}
