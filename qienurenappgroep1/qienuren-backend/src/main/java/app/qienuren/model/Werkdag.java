package app.qienuren.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;

import javax.persistence.*;

@Entity
@Table(name = "werkdag")
public class Werkdag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String datumDag;
    private double opdrachtUren;
    private double overwerkUren;
    private double verlofUren;
    private double ziekteDag;
    private double trainingsUren;
    private double overigeUren;
    private String verklaring = "";

    public String getVerklaring() {
		return verklaring;
	}

	public void setVerklaring(String verklaring) {
		this.verklaring = verklaring;
	}

	@ManyToOne
    @JsonBackReference
    @JoinColumn(name = "urenformulier_id")
    private UrenFormulier urenformulier;

    public Werkdag() {

    };

    public Werkdag(int datumDag) {
        this.datumDag = String.valueOf(datumDag);
    }

    public long getId() {
        return id;
    }

    public String getDatumDag() {
        return datumDag;
    }

    public void setDatumDag(String datumDag) {
        this.datumDag = datumDag;
    }

    public UrenFormulier getUrenformulier() {
        return urenformulier;
    }

    public void setUrenformulier(UrenFormulier urenformulier) {
        this.urenformulier = urenformulier;
    }

    public double getOpdrachtUren() {
        return opdrachtUren;
    }

    public void setOpdrachtUren(double opdrachtUren) {
        this.opdrachtUren = opdrachtUren;
    }

    public double getOverwerkUren() {
        return overwerkUren;
    }

    public void setOverwerkUren(double overwerkUren) {
        this.overwerkUren = overwerkUren;
    }

    public double getVerlofUren() {
        return verlofUren;
    }

    public void setVerlofUren(double verlofUren) {
        this.verlofUren = verlofUren;
    }

    public double isZiekteDag() {
        return ziekteDag;
    }

    public void setZiekteDag(double ziekteDag) {
        this.ziekteDag = ziekteDag;
    }

    public double getTrainingsUren() {
        return trainingsUren;
    }

    public void setTrainingsUren(double trainingsUren) {
        this.trainingsUren = trainingsUren;
    }

    public double getZiekteDag() {
        return ziekteDag;
    }

    public double getOverigeUren() {
        return overigeUren;
    }

    public void setOverigeUren(double overigeUren) {
        this.overigeUren = overigeUren;
    }


    public double getUren() {
        return overigeUren;
    }
    public Werkdag ikBenZiek() {
        this.setZiekteDag(8);
        this.setOverigeUren(0);
        this.setTrainingsUren(0);
        this.setVerlofUren(0);
        this.setOpdrachtUren(0);
        this.setOverwerkUren(0);
        return this;
    }
}
