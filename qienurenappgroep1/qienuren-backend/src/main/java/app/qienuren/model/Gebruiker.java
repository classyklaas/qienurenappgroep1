package app.qienuren.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "gebruiker")
public class Gebruiker {
    private static final long serialVersionUID = 132432L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = true, length = 50)
    private String voornaam;
    @Column(nullable = true, length = 50)
    private String achternaam;
    @Column(nullable = false, length = 100)
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    //join table with roles so a relation can be made, table columns:user_id(user.id from table user) AND roles_id(role.id from table role)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;
    @Column(nullable = false)
    private String encryptedPassword;
    private String emailVerificationToken;
    @Column(nullable = false)
    private boolean emailVerificationStatus = false;
    private String adres;
    private String evtToevoeging;
    private String postcode;
    private String woonplaats;
    private LocalDate geboorteDatum;
    private long telefoonNummer;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UrenFormulier> urenFormulier = new ArrayList<>();

    @OneToMany
    @JsonManagedReference
    private List<Bericht> berichtenLijst = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bedrijf_id")
    private Bedrijf bedrijf;

    public Gebruiker() {
    }

    public Gebruiker(String userId, String voornaam, String achternaam, String email, Collection<RoleEntity> roles, String encryptedPassword, String emailVerificationToken, boolean emailVerificationStatus, String adres, String evtToevoeging, String postcode, String woonplaats, LocalDate geboorteDatum, long telefoonNummer, List<UrenFormulier> urenFormulier, List<Bericht> berichtenLijst, Bedrijf bedrijf) {
        this.userId = userId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.roles = roles;
        this.encryptedPassword = encryptedPassword;
        this.emailVerificationToken = emailVerificationToken;
        this.emailVerificationStatus = emailVerificationStatus;
        this.adres = adres;
        this.evtToevoeging = evtToevoeging;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.geboorteDatum = geboorteDatum;
        this.telefoonNummer = telefoonNummer;
        this.urenFormulier = urenFormulier;
        this.berichtenLijst = berichtenLijst;
        this.bedrijf = bedrijf;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public boolean isEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
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

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public long getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(long telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public List<UrenFormulier> getUrenFormulier() {
        return urenFormulier;
    }

    public void setUrenFormulier(List<UrenFormulier> urenFormulier) {
        this.urenFormulier = urenFormulier;
    }

    public List<Bericht> getBerichtenLijst() {
        return berichtenLijst;
    }

    public void setBerichtenLijst(List<Bericht> berichtenLijst) {
        this.berichtenLijst = berichtenLijst;
    }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public void addUrenFormulierToArray(UrenFormulier uf) {
        urenFormulier.add(uf);
        uf.setGebruiker(this);
    }

    public String getOpdrachtgever(){
        if(this.bedrijf == null) return "";
        else return this.bedrijf.getBedrijfsNaam();
    }
}
