package app.qienuren.controller;

import app.qienuren.gebruikerDto.GebruikerDto;
import app.qienuren.gebruikerDto.Roles;
import app.qienuren.gebruikerDto.Utils;
import app.qienuren.model.*;
import app.qienuren.security.GebruikerPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class GebruikerService implements GebruikerServiceInterface {

    @Autowired
    GebruikerRepository gebruikerRepository;

    @Autowired
    UrenFormulierRepository urenformulierrepository;

    @Autowired
    WerkdagRepository werkdagrepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UrenFormulierService urenformulierService;


    //FOUTE FUNCTIE, GEBRUIKER.ADDURENFORMULIERTPARRAY??
    public void addUrenFormulierToGebruiker(long gebruikerId, long urenformulierId) {
        Gebruiker gebruiker = gebruikerRepository.findById(gebruikerId).get();
        UrenFormulier uf = urenformulierrepository.findById(urenformulierId).get();
        gebruiker.addUrenFormulierToArray(uf);
        gebruikerRepository.save(gebruiker);
    }

    public Werkdag getWerkdagGebruiker(long gebruikerid, long urenformulierid, long werkdagid) {
       /* Gebruiker g = gr.findById(gebruikerid).get();
        UrenFormulier uf = ufr.findById(urenformulierid).get();*/
        Werkdag wd = werkdagrepository.findById(werkdagid).get();
        return wd;
    }

    public Gebruiker getGebruikerById(long id) {
        return gebruikerRepository.findById(id).get();
    }

    public Iterable<Gebruiker> getByRole(Roles role) {
        return this.gebruikerRepository.findByRole(role);
    }

    public Iterable<Gebruiker> getByVoornaam(String voornaam) {
        return this.gebruikerRepository.findByVoornaam(voornaam);
    }

    public Iterable<Gebruiker> getByAchternaam(String achternaam) {
        return this.gebruikerRepository.findByAchternaam(achternaam);
    }

    public Iterable<Gebruiker> getByWoonplaats(String woonplaats) {
        return this.gebruikerRepository.findByWoonplaats(woonplaats);
    }

    public Iterable<Gebruiker> getByGeboorteDatum(LocalDate geboorteDatum) {
        return this.gebruikerRepository.findByGeboorteDatum(geboorteDatum);
    }

    public Gebruiker getByEmail(String email) {
        return this.gebruikerRepository.findByEmail(email);
    }

    public void deleteGebruikerById(long id) {
        gebruikerRepository.deleteById(id);
    }

    public Iterable<Gebruiker> getAllUsers() {
        return gebruikerRepository.findAll();
    }

    public UrenFormulier getUrenformulierGebruiker(long gebruikerid, long urenformulierid) {
        UrenFormulier urenformulier = urenformulierrepository.findById(urenformulierid).get();
        return urenformulier;
    }

    public Gebruiker addTrainee(Gebruiker gebruiker) {
        return gebruikerRepository.save(gebruiker);

    }
    public Gebruiker addMedewerker(Gebruiker gebruiker) {
        return gebruikerRepository.save(gebruiker);

    }

    public Iterable<UrenFormulier> getUrenformulierenVanGebruiker(String id) {
        return gebruikerRepository.findByUserId(id).getUrenFormulier();
    }

    public UrenFormulier changestatusUrenFormulierGebruiker(UrenFormulier urenFormulier) {
        urenFormulier.setStatusGoedkeuring(StatusGoedkeuring.INGEDIEND_GEBRUIKER);
        return urenFormulier;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email);
        if (gebruiker == null) throw new UsernameNotFoundException(email);
        return new GebruikerPrincipal(gebruiker);

    }

    @Override
    public GebruikerDto getUser(String email) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email);
        if (gebruiker == null) throw new UsernameNotFoundException(email);
        GebruikerDto returnValue = new GebruikerDto();
        BeanUtils.copyProperties(gebruiker, returnValue);
        return returnValue;
    }

    @Override
    public GebruikerDto getUserByUserId(String userId) {
        GebruikerDto returnValue = new GebruikerDto();
        Gebruiker gebruiker = gebruikerRepository.findByUserId(userId);
        if (gebruiker == null) {
            throw new UsernameNotFoundException("Gebruiker met userId: " + userId + " niet gevonden!");
        }
        BeanUtils.copyProperties(gebruiker, returnValue);

        return returnValue;
    }

    @Override
    public GebruikerDto createUser(GebruikerDto gebruikerDto) {
        if (gebruikerRepository.findByEmail(gebruikerDto.getEmail()) != null)
            throw new RuntimeException("Record already exists");

        Gebruiker gebruiker = new Gebruiker();
        BeanUtils.copyProperties(gebruikerDto, gebruiker);

        String publicUserId = utils.generateUserId(25);
        gebruiker.setUserId(publicUserId);
        gebruiker.setEncryptedPassword(bCryptPasswordEncoder.encode(gebruikerDto.getPassword()));

        //set role
        Collection<RoleEntity> roleEntities = new HashSet<>();
        for (String role : gebruikerDto.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        gebruiker.setRoles(roleEntities);

        Gebruiker opgeslagenGebruikerDetails = gebruikerRepository.save(gebruiker);

        GebruikerDto returnValue = new GebruikerDto();
        BeanUtils.copyProperties(opgeslagenGebruikerDetails, returnValue);

        return returnValue;
    }

    public GebruikerDto updateGebruiker(String userId, GebruikerDto gebruikerDto) {
        GebruikerDto returnValue = new GebruikerDto();
        Gebruiker gebruiker = gebruikerRepository.findByUserId(userId);

        if (gebruiker == null) {
            throw new RuntimeException("GEEN GEBRUIKER GEVONDEN");
        }
        if (gebruikerDto.getVoornaam() != null && gebruikerDto.getVoornaam() !="")
            gebruiker.setVoornaam(gebruikerDto.getVoornaam());
        if (gebruikerDto.getAchternaam() != null && gebruikerDto.getAchternaam() !="")
            gebruiker.setAchternaam(gebruikerDto.getAchternaam());
        if (gebruikerDto.getEmail() != null && gebruikerDto.getEmail() !="")
            gebruiker.setEmail(gebruikerDto.getEmail());
        if (gebruikerDto.getAdres() != null && gebruikerDto.getAdres() !="")
            gebruiker.setAdres(gebruikerDto.getAdres());
        if (gebruikerDto.getEvtToevoeging() != null && gebruikerDto.getEvtToevoeging() !="")
            gebruiker.setEvtToevoeging(gebruikerDto.getEvtToevoeging());
        if (gebruikerDto.getPostcode() != null && gebruikerDto.getPostcode() !="")
            gebruiker.setPostcode(gebruikerDto.getPostcode());
        if (gebruikerDto.getWoonplaats() != null && gebruikerDto.getWoonplaats() !="")
            gebruiker.setWoonplaats(gebruikerDto.getWoonplaats());
        if (gebruikerDto.getGeboorteDatum() != null)
            gebruiker.setGeboorteDatum(gebruikerDto.getGeboorteDatum());
        if (gebruikerDto.getTelefoonNummer() != 0)
            gebruiker.setTelefoonNummer(gebruikerDto.getTelefoonNummer());

        Gebruiker updatedGebruiker = gebruikerRepository.save(gebruiker);

        BeanUtils.copyProperties(gebruiker, returnValue);

        return returnValue;
    }

    @Override
    public void deleteGebruiker(String userId) {
        Gebruiker gebruiker = gebruikerRepository.findByUserId(userId);
        if(gebruiker == null) {
            throw new RuntimeException("NO RECORD FOUND");
        }
        gebruikerRepository.delete(gebruiker);
    }

    public void urenFormulierenKlaarzetten(UrenFormulier newUrenFormulier) {
        boolean exists = false;
        Iterable<Gebruiker> gebruikers = getAllUsers();
        for (Gebruiker gebruiker : gebruikers) {
            RoleEntity[] gebruikerRol = gebruiker.getRoles().toArray(new RoleEntity[0]);
            if (gebruikerRol[0].getName().equals("ROLE_MEDEWERKER") || gebruikerRol[0].getName().equals("ROLE_TRAINEE")) {
                Iterable<UrenFormulier> medewerkerUrenformulier = gebruiker.getUrenFormulier();
                for (UrenFormulier uf : medewerkerUrenformulier) {
                    if (uf.getJaar().equals(newUrenFormulier.getJaar()) & uf.getMaand() == newUrenFormulier.getMaand()) {
                        exists = true;
                    }
                }
                if (!exists) {
                    gebruiker.addUrenFormulierToArray(urenformulierService.addNewUrenFormulier(new UrenFormulier(newUrenFormulier)));
                    gebruikerRepository.save(gebruiker);
                }
            }
        }
    }

    public Gebruiker getGebruikerByuserId(String id) {
        return gebruikerRepository.findByUserId(id);
    }
}

