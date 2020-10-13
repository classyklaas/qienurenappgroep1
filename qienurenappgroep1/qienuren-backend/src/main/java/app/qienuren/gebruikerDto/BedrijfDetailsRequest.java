package app.qienuren.gebruikerDto;

import java.time.LocalDate;

public class BedrijfDetailsRequest {
    private String bedrijfsNaam;
    private String contactPersoon;
    private String email;
    private String password;
    private String adres;
    private String evtToevoeging;
    private String postcode;
    private String woonplaats;
//    private LocalDate geboorteDatum;
    private long telefoonNummer;

    public String getBedrijfsNaam() {
        return bedrijfsNaam;
    }

    public void setBedrijfsNaam(String bedrijfsNaam) {
        this.bedrijfsNaam = bedrijfsNaam;
    }

    public String getContactPersoon() {
        return contactPersoon;
    }

    public void setContactPersoon(String contactPersoon) {
        this.contactPersoon = contactPersoon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

//    public LocalDate getGeboorteDatum() {
//        return geboorteDatum;
//    }
//
//    public void setGeboorteDatum(LocalDate geboorteDatum) {
//        this.geboorteDatum = geboorteDatum;
//    }

    public long getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(long telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }
}