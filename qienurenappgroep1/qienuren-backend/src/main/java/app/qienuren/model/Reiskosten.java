package app.qienuren.model;

import javax.persistence.*;

@Entity
@Table(name = "reisKosten")
public class Reiskosten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @Enumerated(EnumType.STRING)
    private Maand maand;
}
