package app.qienuren.model;

import app.qienuren.exceptions.OnderwerkException;
import app.qienuren.exceptions.OverwerkException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "urenFormulier")
public class UrenFormulier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double totaalGewerkteUren;
    private double ziekteUren;

    @Enumerated(EnumType.STRING)
    private Maand maand;
    private String jaar;
    private String opmerking = "";
    @Enumerated(EnumType.STRING)
    private StatusGoedkeuring statusGoedkeuring;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "gebruiker_id")
    private Gebruiker gebruiker;

    @OneToMany
    @JsonManagedReference
    private List<Werkdag> werkdag = new ArrayList<>();

    public UrenFormulier(UrenFormulier newUrenFormulier) {
        this.setStatusGoedkeuring(StatusGoedkeuring.OPEN);
        this.maand = newUrenFormulier.getMaand();
        this.jaar = newUrenFormulier.getJaar();
    }

    public UrenFormulier(){
        this.setStatusGoedkeuring(StatusGoedkeuring.OPEN);
    }

    //GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public String getJaar() {
        return jaar;
    }

    public void setJaar(String jaar) {
        this.jaar = jaar;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public List<Werkdag> getWerkdag() {
        return werkdag;
    }

    public void setWerkdag(List<Werkdag> werkdag) {
        this.werkdag = werkdag;
    }

    public double getTotaalGewerkteUren() {
        this.totaalGewerkteUren = 0.0;
        for(Werkdag wd : this.werkdag){
            this.totaalGewerkteUren += wd.getOpdrachtUren();
            this.totaalGewerkteUren += wd.getOverigeUren();
            this.totaalGewerkteUren += wd.getTrainingsUren();
            this.totaalGewerkteUren += wd.getVerlofUren();
            this.totaalGewerkteUren += wd.getZiekteDag();
            this.totaalGewerkteUren += wd.getOverwerkUren();
        }
        return totaalGewerkteUren;
    }


    public void setTotaalGewerkteUren(double totaalGewerkteUren) {
        this.totaalGewerkteUren = totaalGewerkteUren;
    }

    public Maand getMaand() {
        return maand;
    }

    public void setMaand(Maand maand) {
        this.maand = maand;
    }

    public void addWerkdayToArray(Werkdag wd) throws Exception {
        werkdag.add(wd);
        wd.setUrenformulier(this);
    }

    public StatusGoedkeuring getStatusGoedkeuring() {
        return statusGoedkeuring;
    }

    public void setStatusGoedkeuring(StatusGoedkeuring statusGoedkeuring) {
        this.statusGoedkeuring = statusGoedkeuring;
    }

    public double getZiekteUren() {
        this.ziekteUren = 0;
        for (Werkdag wd : this.werkdag){
            this.ziekteUren += wd.getZiekteDag();
        }
        return ziekteUren;
    }
}
