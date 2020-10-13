package app.qienuren;

import app.qienuren.controller.AuthorityRepository;
import app.qienuren.controller.GebruikerRepository;
import app.qienuren.controller.RoleRepository;
import app.qienuren.gebruikerDto.Roles;
import app.qienuren.gebruikerDto.Utils;
import app.qienuren.model.AuthorityEntity;
import app.qienuren.model.Gebruiker;
import app.qienuren.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

//spring will create an instance at startup app
@Component
public class InitialGebruikerSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    GebruikerRepository gebruikerRepository;

    //registers this class and method on application event with springframework, when sf broadcasts an event that app has initialised all beans and components(applicationready), which will make this method run
    @EventListener
    //transactional is needed because of multiple modified queries within the same onApplication and then function
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From app rdy event");
        AuthorityEntity readUserAuthority = createAuthority("READ:GEBRUIKER");
        AuthorityEntity createUserAuthority = createAuthority("CREATE:GEBRUIKER");
        AuthorityEntity updateUserAuthority = createAuthority("UPDATE:GEBRUIKER");
        AuthorityEntity deleteUserAuthority = createAuthority("DELETE:GEBRUIKER");

        AuthorityEntity readUrenformulierAuthority = createAuthority("READ:URENFORMULIER");
        AuthorityEntity createUrenformulierAuthority = createAuthority("CREATE:URENFORMULIER");
        AuthorityEntity updateUrenformulierAuthority = createAuthority("UPDATE:URENFORMULIER");
        AuthorityEntity deleteUrenformulierAuthority = createAuthority("DELETE:URENFORMULIER");
        AuthorityEntity approveUrenformulierAuthority = createAuthority("APPROVE:URENFORMULIER");

        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readUserAuthority, createUserAuthority,updateUserAuthority,deleteUserAuthority, readUrenformulierAuthority, createUrenformulierAuthority, updateUrenformulierAuthority, approveUrenformulierAuthority, deleteUrenformulierAuthority));
        RoleEntity roleMedewerker = createRole(Roles.ROLE_MEDEWERKER.name(), Arrays.asList(readUserAuthority,updateUserAuthority, readUrenformulierAuthority, createUrenformulierAuthority, updateUrenformulierAuthority));
        RoleEntity roleTrainee = createRole(Roles.ROLE_TRAINEE.name(), Arrays.asList(readUserAuthority,updateUserAuthority, readUrenformulierAuthority, createUrenformulierAuthority, updateUrenformulierAuthority));
        RoleEntity roleBedrijf = createRole(Roles.ROLE_BEDRIJF.name(), Arrays.asList(readUserAuthority, readUrenformulierAuthority, approveUrenformulierAuthority));

        //create userentity with adminrole
        if (gebruikerRepository.findByEmail("admin@qien.nl") == null) {
            Gebruiker adminUser = new Gebruiker();
            adminUser.setVoornaam("Admin");
            adminUser.setAchternaam("Qien");
            adminUser.setEmail("admin@qien.nl");
            adminUser.setRoles(Arrays.asList(roleAdmin));
            adminUser.setUserId(utils.generateUserId(6));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("admin"));
            gebruikerRepository.save(adminUser);
        } return;
    }

    
    //function to create authority in table and save it in db
    @Transactional
    public AuthorityEntity createAuthority(String name) {
        //check if record already exists to prevend duplicates
        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    //function to create role in table, and then add a collection of authorities to the field authorities in role and then save it in db
    @Transactional
    public RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        //check if record already exists to prevend duplicates
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
