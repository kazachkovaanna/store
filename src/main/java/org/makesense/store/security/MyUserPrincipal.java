package org.makesense.store.security;

import lombok.Getter;
import lombok.Setter;
import org.makesense.store.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class MyUserPrincipal implements UserDetails {
    private User user;
    private List<GrantedAuthority> authorities;

    public MyUserPrincipal(User user) {
        this.user = user;
        List<String> roles = new ArrayList<>();
        String[] ra = new String[user.getRoles().size()];
        user.getRoles().stream().flatMap(role -> Stream.of(role.getRole())).forEach(s -> roles.add(s));
        for(int i = 0 ; i < ra.length; i++){
            ra[i]=roles.get(i);
        }
        authorities =  AuthorityUtils.createAuthorityList(ra);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
