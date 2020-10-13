package app.qienuren.rest;

import app.qienuren.controller.AdminService;
import app.qienuren.controller.GebruikerRepository;
import app.qienuren.controller.GebruikerService;
import app.qienuren.controller.UrenFormulierService;
import app.qienuren.model.Gebruiker;
import app.qienuren.model.UrenFormulier;
import app.qienuren.model.Werkdag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminEndpoint {
    @Autowired
    AdminService adminService;

    @Autowired
    GebruikerService gebruikerService;

    @Autowired
    UrenFormulierService urenFormulierService;

    @Autowired
    GebruikerRepository gebruikerRepository;

//    /*nieuwe gebruikers*/
//    @PostMapping("/new-gebruiker")
//    public Gebruiker addGebruiker(@RequestBody Gebruiker gebruiker) {
//        return gebruikerService.addGebruiker(gebruiker);
//    }

    /*Wijzigen gebruikers*/
    //@PutMapping("users/changedetails/{id}")
    //public void changeDetailsById(@PathVariable(value = "id") long id, @RequestBody Gebruiker gebruiker) {
    // gebruikerService.changeDetails(gebruikerRepository.findById(id).get(), gebruiker);
    //}

    /*Rol gebruiker veranderen*/

    /*Verwijderen gebruikers*/

    @PreAuthorize("hasAuthority('DELETE:GEBRUIKER')")
    @DeleteMapping("/delete/{id}")
    public String deleteGebruikerById(@PathVariable(value = "id") long id) {
        gebruikerService.deleteGebruikerById(id);
        return "Gebruiker met id " + id + " verwijderd";
    }

    /*Overzichten gebruikers*/
    // @GetMapping("/all-trainees")
    // public Iterable<Trainee> getTrainees(){
    //  return traineeService.getAllTrainees();
    //}

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all-users")
    public Iterable<Gebruiker> getUsers() {
        return gebruikerService.getAllUsers();
    }

    //@GetMapping("/all-trainees/{rol}")
    //public Gebruiker getGebruikerTrainee(@PathVariable(value="roltrainee") String rolTrainee) ;
    //Gebruiker gebruikerTrainee = GebruikerService.getGebruikerTrainee(rolTrainee);
    //return gebruikerTrainee;


    /* Urenformulieren */
    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/{gebruikerid}/{urenformulierid}/{werkdagid}")
    public Werkdag getWerkdagGebruiker(@PathVariable(value = "gebruikerid") long gid, @PathVariable(value = "urenformulierid") long ufid, @PathVariable(value = "werkdagid") long wdid) {
        Werkdag wd = gebruikerService.getWerkdagGebruiker(gid, ufid, wdid);
        return wd;
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/{gebruikerid}/{urenformulierid}")
    public UrenFormulier getUrenformulierGebruiker(@PathVariable(value = "gebruikerid") long gebruikerid, @PathVariable(value = "urenformulierid") long urenformulierid) {
        UrenFormulier urenFormulier = gebruikerService.getUrenformulierGebruiker(gebruikerid, urenformulierid);
        return urenFormulier;
    }


    /*Aanmaken Urenformulier*/
    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @PostMapping("urenformulier/new")
    public UrenFormulier addNewUrenFormulier(@RequestBody UrenFormulier urenFormulier) {
        return urenFormulierService.addNewUrenFormulier(urenFormulier);
    }

}

