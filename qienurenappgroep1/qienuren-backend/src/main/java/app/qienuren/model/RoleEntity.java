package app.qienuren.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {
    private static final long serialVersionUID = 122332L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 25)
    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Collection<Gebruiker> users;

    //join table with authorities so a relation can be made, table columns:roles_id(role.id from table role) AND authorities_id(authority.id from table authority)
    //@JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Gebruiker> getUsers() {
        return users;
    }

    public void setUsers(Collection<Gebruiker> users) {
        this.users = users;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return name;
    }
}
