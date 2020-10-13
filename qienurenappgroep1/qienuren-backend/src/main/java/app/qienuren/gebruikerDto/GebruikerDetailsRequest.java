package app.qienuren.gebruikerDto;

import java.time.LocalDate;

public class GebruikerDetailsRequest {

    private String voornaam;
    private String achternaam;
    private String email;
    private String password;
    private String adres;
    private String evtToevoeging;
    private String postcode;
    private String woonplaats;
    private LocalDate geboorteDatum;
    private long telefoonNummer;

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
}