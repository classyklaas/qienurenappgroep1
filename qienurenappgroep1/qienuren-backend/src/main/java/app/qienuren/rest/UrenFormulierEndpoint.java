package app.qienuren.rest;

import app.qienuren.controller.GebruikerService;
import app.qienuren.controller.UrenFormulierRepository;
import app.qienuren.controller.UrenFormulierService;
import app.qienuren.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/urenformulier")
public class UrenFormulierEndpoint {

    @Autowired
    UrenFormulierService urenFormulierService;

    @Autowired
    GebruikerService gebruikerService;

    @Autowired
    UrenFormulierRepository urenFormulierRepository;

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/get")
    public Iterable<UrenFormulier> getUrenformulieren() {
        return urenFormulierService.getAllUrenFormulieren();
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @PostMapping("/new")
    public UrenFormulier addNewUrenFormulier(@RequestBody UrenFormulier urenFormulier) {
        return urenFormulierService.addNewUrenFormulier(urenFormulier);
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/gewerkteuren/{id}")
    public double getTotaalGewerkteUren(@PathVariable(value = "id") long id) {
        return urenFormulierService.getTotaalGewerkteUren(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/permaand/{maandid}")
    public Iterable<UrenFormulier> getUrenFormulierPerMaand(@PathVariable(value = "maandid") int maandid) {
        return urenFormulierService.getUrenFormulierPerMaand(maandid);
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/{id}")
    public UrenFormulier getUrenFormulierById(@PathVariable(value = "id") long id) {
        System.out.println("endpoint called");
        return urenFormulierService.getUrenFormulierById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINEE')or #id == principal.userId")
    @PutMapping("/gebruiker/{urenformulierid}/setstatus-indienentrainee")
    public UrenFormulier setStatusFormulierIngediendTrainee(@PathVariable(value = "urenformulierid") long urenformulierid) {
        urenFormulierService.setStatusUrenFormulier(urenformulierid, "TRAINEE");
        return urenFormulierService.getUrenFormulierById(urenformulierid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDEWERKER')or #id == principal.userId")
    @PutMapping("/gebruiker/{urenformulierid}/setstatus-indienenmedewerker")
    public UrenFormulier setStatusFormulierIngediendMedewerker(@PathVariable(value = "urenformulierid") long urenformulierid) {
        urenFormulierService.setStatusUrenFormulier(urenformulierid, "MEDEWERKER");
        return urenFormulierService.getUrenFormulierById(urenformulierid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINEE','MEDEWERKER')or #id == principal.userId")
    @PutMapping("/gebruiker/{urenformulierid}/setstatus-indienengebruiker")
    //Als iemand met de rol Gebruiker deze methode aanroept,
    // zet deze de statusGoedkeuring van OPEN naar INGEDIEND_GEBRUIKER
    public Object setStatusFormulierIngediendGebruiker(@PathVariable(value = "urenformulierid") long urenformulierid) {
        return urenFormulierService.setStatusUrenFormulier(urenformulierid, "GEBRUIKER");
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @PutMapping("/admin/{urenformulierid}/setstatus-goedkeuring-admin")
    //Als iemand met de rol Admin deze methode aanroept,
    // zet deze de statusGoedkeuring van GOEDGEKEURD_BEDRIJF of INGEDIEND_MEDEWERKER naar
    //GOEDGEKEURD_ADMIN
    public UrenFormulier setStatusGoedkeuringAdmin(@PathVariable(value = "urenformulierid") long urenformulierid) {
        urenFormulierService.setStatusUrenFormulier(urenformulierid, "ADMIN");
        return urenFormulierService.getUrenFormulierById(urenformulierid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BEDRIJF')or #id == principal.userId")
    @PutMapping("/bedrijf/{urenformulierid}/setstatus-goedkeuring-bedrijf")
    //Als iemand met de rol Bedrijf deze methode aanroept,
    //zet deze de statusGoedkeuring van INGEDIEND_TRAINEE naar de
    //GOEDGEKEURD_BEDRIJF
    public UrenFormulier setStatusGoedkeuringBedrijf(@PathVariable(value = "urenformulierid") long urenformulierid) {
        urenFormulierService.setStatusUrenFormulier(urenformulierid, "BEDRIJF");
        return urenFormulierService.getUrenFormulierById(urenformulierid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BEDRIJF')or #id == principal.userId")
    @PutMapping("/{urenformulierid}/setstatus-afkeuring")
    public UrenFormulier setAfkeuring(@PathVariable(value = "urenformulierid") long urenformulierid) {
        //Als iemand met de rol Admin of Bedrijf deze methode aanroept,
        // zet deze de statusGoedkeuring terug naar OPEN nadat deze
        // door een bedrijf of admin is afgekeurd.
        getUrenFormulierById(urenformulierid).setStatusGoedkeuring(StatusGoedkeuring.OPEN);
        return getUrenFormulierById(urenformulierid);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BEDRIJF')or #id == principal.userId")
    @PutMapping("/afkeur-opmerking/{id}")
    public void afkeurOpmerking(@PathVariable(value = "id") long id, @RequestBody UrenFormulier urenFormulier) {
        urenFormulierService.changeDetails(urenFormulierRepository.findById(id).get(), urenFormulier);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/klaarzetten")
    public void urenFormulierenKlaarzetten(@RequestBody UrenFormulier newUrenFormulier) {
        gebruikerService.urenFormulierenKlaarzetten(newUrenFormulier);
    }

    @PreAuthorize("hasAnyRole('GEBRUIKER') or #id == principal.userId")
    @PutMapping("/ziekmelden/{id}/{datumDag}")
        public void ziekMelden(@PathVariable(value = "id") String id, @RequestBody UrenFormulier urenFormulier, @PathVariable(value = "datumDag") String datumDag) {
        urenFormulierService.ziekMelden(id, urenFormulier, datumDag);
    }

}


