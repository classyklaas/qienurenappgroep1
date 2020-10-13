package app.qienuren.controller;

import app.qienuren.model.Gebruiker;
import app.qienuren.gebruikerDto.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface GebruikerRepository extends CrudRepository<Gebruiker, Long> {

   Gebruiker findByEmail(String email);
   Gebruiker findByUserId(String userId);

   //Iterable<Gebruiker> findByRole(String role);
   //Deze methode doet hetzelfde als degene hieronder

   @Query(value = "FROM Gebruiker WHERE roles=?1")
   Iterable<Gebruiker> findByRole(Roles role);

   @Query(value = "FROM Gebruiker WHERE voornaam=?1" )
   Iterable<Gebruiker> findByVoornaam(String voornaam);

   @Query(value = "FROM Gebruiker WHERE achternaam=?1" )
   Iterable<Gebruiker> findByAchternaam(String achternaam);

   @Query(value = "FROM Gebruiker WHERE woonplaats=?1" )
   Iterable<Gebruiker> findByWoonplaats(String woonplaats);

   @Query(value = "FROM Gebruiker WHERE geboorteDatum=?1" )
   Iterable<Gebruiker> findByGeboorteDatum(LocalDate geboorteDatum);

//   @Query(value = "FROM Gebruiker WHERE emailadres=?1 ORDER BY naam ASC" )
//   Iterable<Gebruiker> findByEmail(String emailadres);
}
