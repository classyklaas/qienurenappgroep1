package app.qienuren.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "bedrijf")
public class Bedrijf extends Gebruiker {
    private static final long serialVersionUID = 132432L;

    private String bedrijfsNaam;
    private String contactPersoon;
    private long telefoonNummer;
    private String email;
    private String adres;
    private String evtToevoeging;
    private String postcode;
    private String plaats;

    @OneToMany
    @JsonManagedReference
    private List<Gebruiker> lijstGebruikers = new ArrayList<>();


    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    //join table with roles so a relation can be made, table columns:user_id(user.id from table user) AND roles_id(role.id from table role)
    @JoinTable(name = "bedrijf_roles",
            joinColumns = @JoinColumn(name = "bedrijf_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    public String getBedrijfsNaam() {
        return bedrijfsNaam;
    }

    public void setBedrijfsNaam(String bedrijfsNaam) {
        this.bedrijfsNaam = bedrijfsNaam;
    }

    public long getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(long telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEvtToevoeging() {
        return evtToevoeging;
    }

    public void setEvtToevoeging(String evtToevoeging) {
        this.evtToevoeging = evtToevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getContactPersoon() {
        return contactPersoon;
    }

    public void setContactPersoon(String contactPersoon) {
        this.contactPersoon = contactPersoon;
    }

    public List<Gebruiker> getLijstGebruikers() {
        return lijstGebruikers;
    }

    public void setLijstGebruikers(List<Gebruiker> lijstGebruikers) {
        this.lijstGebruikers = lijstGebruikers;
    }

    public void addGebruikerToLijst(Gebruiker gebruiker) {
        lijstGebruikers.add(gebruiker);
        gebruiker.setBedrijf(this);
    }

    @Override
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
