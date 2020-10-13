package app.qienuren.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "bericht")
public class Bericht {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String datum;
    String bericht;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="berichtId")
    private Gebruiker gebruiker;

    public long getId() {
        return id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }
}
