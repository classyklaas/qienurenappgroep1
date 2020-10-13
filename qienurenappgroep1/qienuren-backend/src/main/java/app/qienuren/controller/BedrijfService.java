package app.qienuren.controller;

import app.qienuren.gebruikerDto.GebruikerDto;
import app.qienuren.gebruikerDto.Utils;
import app.qienuren.model.Bedrijf;
import app.qienuren.model.Gebruiker;
import app.qienuren.model.RoleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class BedrijfService {

    @Autowired
    BedrijfRepository bedrijfRepository;

    @Autowired
    GebruikerRepository gebruikerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Bedrijf addBedrijfbyID(Bedrijf bedrijf) {
        return bedrijfRepository.save(bedrijf);
    }

    public Iterable<Bedrijf> getAllBedrijven() {
        return bedrijfRepository.findAll();
    }

    public void deleteBedrijfById(long id) {
        bedrijfRepository.deleteById(id);
    }

    public Bedrijf changeDetails(Bedrijf bedrijf, Bedrijf bedrijfUpdate) {
        if (bedrijfUpdate.getBedrijfsNaam() != null) {
            bedrijf.setBedrijfsNaam(bedrijfUpdate.getBedrijfsNaam());
        }
        if (bedrijfUpdate.getAdres() != null) {
            bedrijf.setAdres(bedrijfUpdate.getAdres());
        }
        if (bedrijfUpdate.getEvtToevoeging() != null) {
            bedrijf.setEvtToevoeging(bedrijfUpdate.getEvtToevoeging());
        }
        if (bedrijfUpdate.getPostcode() != null) {
            bedrijf.setPostcode(bedrijfUpdate.getPostcode());
        }
        if (bedrijfUpdate.getPlaats() != null) {
            bedrijf.setPlaats(bedrijfUpdate.getPlaats());
        }
        if (bedrijfUpdate.getTelefoonNummer() != 0) {
            bedrijf.setTelefoonNummer(bedrijfUpdate.getTelefoonNummer());
        }
        if (bedrijfUpdate.getContactPersoon() != null) {
            bedrijf.setContactPersoon(bedrijfUpdate.getContactPersoon());
        }
        if (bedrijfUpdate.getEmail() != null) {
            bedrijf.setEmail(bedrijfUpdate.getEmail());
        }
        return bedrijfRepository.save(bedrijf);
    }

    public void addGebruikerToBedrijf(long bedrijfId, long gebruikerId) {
        Gebruiker gebruiker = gebruikerRepository.findById(gebruikerId).get();
        if (gebruiker.getBedrijf() != null) {
            gebruiker.setBedrijf(null);
            Bedrijf bedrijf = bedrijfRepository.findById(bedrijfId).get();
            for (Gebruiker gebruiker1 : bedrijf.getLijstGebruikers()) {
                if(gebruiker1.equals(gebruiker)) {
                    bedrijf.getLijstGebruikers().remove(gebruiker);
                }
            }
        }
        bedrijfRepository.findById(bedrijfId).get().addGebruikerToLijst(gebruikerRepository.findById(gebruikerId).get());
    }

    public Iterable<Bedrijf> getByBedrijfsNaam(String bedrijfsNaam) {
        return this.bedrijfRepository.findByNaam(bedrijfsNaam);
    }

    public GebruikerDto createBedrijf(GebruikerDto gebruikerDto) {
        if (gebruikerRepository.findByEmail(gebruikerDto.getEmail()) != null)
            throw new RuntimeException("Record already exists");

        Bedrijf bedrijf = new Bedrijf();
        BeanUtils.copyProperties(gebruikerDto, bedrijf);

        String publicUserId = utils.generateUserId(25);
        bedrijf.setUserId(publicUserId);
        bedrijf.setEncryptedPassword(bCryptPasswordEncoder.encode(gebruikerDto.getPassword()));

        //set role
        Collection<RoleEntity> roleEntities = new HashSet<>();
        for (String role : gebruikerDto.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        bedrijf.setRoles(roleEntities);


        Bedrijf storedUserDetails = gebruikerRepository.save(bedrijf);

        GebruikerDto returnValue = new GebruikerDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }


    public GebruikerDto getBedrijfByUserId(String userId) {
        GebruikerDto returnValue = new GebruikerDto();
        Bedrijf bedrijf = (Bedrijf) gebruikerRepository.findByUserId(userId);
        if (bedrijf == null) {
            throw new UsernameNotFoundException("Gebruiker met userId: " + userId + " niet gevonden!");
        }
        BeanUtils.copyProperties(bedrijf, returnValue);

        return returnValue;
    }
}


