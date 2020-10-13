package app.qienuren.controller;

import app.qienuren.exceptions.OnderwerkException;
import app.qienuren.exceptions.OverwerkException;
import app.qienuren.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UrenFormulierService {

    @Autowired
    UrenFormulierRepository urenFormulierRepository;

    @Autowired
    WerkdagRepository werkdagRepository;

    @Autowired
    WerkdagService werkdagService;

    @Autowired
    GebruikerRepository gebruikerRepository;

    @Autowired
    MailService mailService;

    public Iterable<UrenFormulier> getAllUrenFormulieren() {
        return urenFormulierRepository.findAll();
    }

    public List<UrenFormulier> urenFormulieren() {
        return (List<UrenFormulier>) urenFormulierRepository.findAll();
    }


    public Object addWorkDaytoUrenFormulier(UrenFormulier uf, long wdid) {
        Werkdag wd = werkdagRepository.findById(wdid).get();
        try {
            uf.addWerkdayToArray(wd);
            return urenFormulierRepository.save(uf);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public UrenFormulier addNewUrenFormulier(UrenFormulier uf) {
        int maand = uf.getMaand().ordinal() + 1;
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(uf.getJaar()), maand);
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int x = 1; x <= daysInMonth; x++) {
            Werkdag werkdag = werkdagService.addNewWorkday(new Werkdag(x));
            addWorkDaytoUrenFormulier(uf, werkdag.getId());
        }
        return urenFormulierRepository.save(uf);
    }

    public double getTotaalGewerkteUren(long id) {
        return urenFormulierRepository.findById(id).get().getTotaalGewerkteUren();
    }

    public double getZiekteUrenbyId(long id){
        return urenFormulierRepository.findById(id).get().getZiekteUren();
    }

    public Iterable<UrenFormulier> getUrenFormulierPerMaand(int maandid) {
        List<UrenFormulier> localUren = new ArrayList<>();
        for (UrenFormulier uren : urenFormulierRepository.findAll()) {
            if (uren.getMaand().ordinal() == maandid) {
                localUren.add(uren);
            }
        }
        return localUren;
    }

    public UrenFormulier getUrenFormulierById(long uid) {
        return urenFormulierRepository.findById(uid).get();
    }

    public double getGewerkteUrenByID(long id) {
    return 0.0;
    }

    public Object setStatusUrenFormulier(long urenformulierId, String welkeGoedkeurder){

        //deze methode zet de statusGoedkeuring van OPEN naar INGEDIEND_TRAINEE nadat deze
        // door de trainee is ingediend ter goedkeuring
        if (welkeGoedkeurder.equals("GEBRUIKER")) {
            checkMaximaalZiekuren(getZiekteUrenbyId(urenformulierId));
            try {
                enoughWorkedthisMonth(getTotaalGewerkteUren(urenformulierId));
            } catch(OnderwerkException onderwerkException) {
                urenFormulierRepository.save(urenFormulierRepository.findById(urenformulierId).get());
                System.out.println("je hebt te weinig uren ingevuld deze maand");
                return "onderwerk";
            } catch (OverwerkException overwerkexception){
                urenFormulierRepository.save(urenFormulierRepository.findById(urenformulierId).get());
                System.out.println("Je hebt teveel uren ingevuld deze maand!");
                return "overwerk";
            } catch (Exception e) {
                urenFormulierRepository.save(urenFormulierRepository.findById(urenformulierId).get());
                return "random exception";
            }
            getUrenFormulierById(urenformulierId).setStatusGoedkeuring(StatusGoedkeuring.INGEDIEND_GEBRUIKER);
            urenFormulierRepository.save(urenFormulierRepository.findById(urenformulierId).get());
            return "gelukt";
        }

        //deze methode zet de statusGoedkeuring van INGEDIEND_TRAINEE of INGEDIEND_MEDEWERKER naar
        // GOEDGEKEURD_ADMIN nadat deze door de trainee/medewerker is ingediend ter goedkeuring
        // (en door bedrijf is goedgekeurd indien Trainee)
        if(welkeGoedkeurder.equals("ADMIN")) {
            getUrenFormulierById(urenformulierId).setStatusGoedkeuring(StatusGoedkeuring.GOEDGEKEURD_ADMIN);
        }

        //deze methode zet de statusGoedkeuring van INGEDIEND_TRAINEE naar GOEDGEKEURD_BEDRIJF nadat deze
        //door de trainee/medewerker is ingediend ter goedkeuring. Medewerker slaat deze methode over
        //en gaat gelijk naar goedkeuring admin!
        if(welkeGoedkeurder.equals("BEDRIJF")) {
            getUrenFormulierById(urenformulierId).setStatusGoedkeuring(StatusGoedkeuring.GOEDGEKEURD_BEDRIJF);
        }

        return getUrenFormulierById(urenformulierId);
    }

    public UrenFormulier changeDetails(UrenFormulier urenFormulier, UrenFormulier urenFormulierUpdate) {
        if (urenFormulierUpdate.getOpmerking() != null) {
            urenFormulier.setOpmerking(urenFormulierUpdate.getOpmerking());
        }

        return urenFormulierRepository.save(urenFormulier);
    }

    public void ziekMelden(String id, UrenFormulier urenFormulier, String datumDag) {
        Gebruiker gebruiker = gebruikerRepository.findByUserId(id);
        for (UrenFormulier uf : gebruiker.getUrenFormulier()) {
            if (uf.getJaar().equals(urenFormulier.getJaar()) && uf.getMaand() == urenFormulier.getMaand()) {
                for (Werkdag werkdag : uf.getWerkdag()) {
                    if (werkdag.getDatumDag().equals(datumDag)) {
                        werkdagRepository.save(werkdag.ikBenZiek());
                    }
                }
            }
        }
    }

    public void enoughWorkedthisMonth(double totalHoursWorked) throws OnderwerkException {
        if (totalHoursWorked <= 139){
            throw new OnderwerkException("Je hebt te weinig uren ingevuld deze maand");
        } else if (totalHoursWorked >= 220){
            throw new OverwerkException("Je hebt teveel gewerkt, take a break");
        } else {
            return;
        }
    }
    // try catch blok maken als deze exception getrowt wordt. if false krijgt die een bericht terug. in classe urenformulierservice regel 80..

    public void checkMaximaalZiekuren(double getZiekurenFormulier){
        if (getZiekurenFormulier >= 64){
           Mail teveelZiekMailCora = new Mail();
           teveelZiekMailCora.setEmailTo("casparsteinebach@gmail.com");
           teveelZiekMailCora.setSubject("Een medewerker heeft meer dan 9 ziektedagen. Check het even!");
           teveelZiekMailCora.setText("Hoi Cora, Een medewerker is volgens zijn of haar ingediende urenformulier meer dag 9 dagen ziek geweest deze maand." +
                   " Wil je het formulier even checken? Log dan in bij het urenregistratiesysteem van Qien."
                   );
           mailService.sendEmail(teveelZiekMailCora);
            System.out.println("email verzonden omdat maximaal ziekteuren zijn overschreden");
        } else {
            System.out.println("ziekteuren zijn niet overschreden. toppiejoppie");
        }
    }
    // rij 85 voor dat we enough worked this month aanroepen een functie die de ziekteuren oproept.
}
