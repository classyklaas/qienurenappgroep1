package app.qienuren.security;

import app.qienuren.model.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class GebruikerPrincipal implements UserDetails {
    private static final long serialVersionUID = 92941243L;

    private Bedrijf bedrijf;
    private Gebruiker gebruiker;
    private String userId;

    public GebruikerPrincipal(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
        this.userId = bedrijf.getUserId();
    }

    public GebruikerPrincipal(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        this.userId = gebruiker.getUserId();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //make the getfunction get roles and authorities.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();
        //get user roles
        Collection<RoleEntity> roles = gebruiker.getRoles();
        if(roles == null) return authorities;

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach((authorityEntity) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        });
        return authorities;

    }

    @Override
    public String getPassword() {
        return this.gebruiker.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.gebruiker.getEmail();
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