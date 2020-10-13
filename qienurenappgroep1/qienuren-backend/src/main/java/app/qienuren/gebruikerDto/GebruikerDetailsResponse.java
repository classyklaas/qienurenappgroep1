package app.qienuren.gebruikerDto;


import app.qienuren.model.Bedrijf;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class GebruikerDetailsResponse {
    private String userId;
    private String voornaam;
    private String achternaam;
    private String email;
    private LocalDate geboorteDatum;
    private long telefoonNummer;
    private String adres;
    private String evtToevoeging;
    private String postcode;
    private String woonplaats;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "bedrijf_id")
    private Bedrijf bedrijf;


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

    public LocalDate getGeboorteDatum() { return geboorteDatum; }

    public void setGeboorteDatum(LocalDate geboorteDatum) { this.geboorteDatum = geboorteDatum; }

    public long getTelefoonNummer() { return telefoonNummer; }

    public void setTelefoonNummer(long telefoonNummer) { this.telefoonNummer = telefoonNummer; }

    public String getAdres() { return adres; }

    public void setAdres(String adres) { this.adres = adres; }

    public String getEvtToevoeging() { return evtToevoeging; }

    public void setEvtToevoeging(String evtToevoeging) { this.evtToevoeging = evtToevoeging; }

    public String getPostcode() { return postcode; }

    public void setPostcode(String postcode) { this.postcode = postcode; }

    public String getWoonplaats() { return woonplaats; }

    public void setWoonplaats(String woonplaats) { this.woonplaats = woonplaats; }

    public Bedrijf getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Bedrijf bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getOpdrachtgever(){
        if(this.bedrijf == null) return "";
        else return this.bedrijf.getBedrijfsNaam();
    }
}