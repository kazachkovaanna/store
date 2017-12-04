package org.makesense.store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    private  String id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
