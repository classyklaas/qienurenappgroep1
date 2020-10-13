package app.qienuren.controller;

import app.qienuren.gebruikerDto.GebruikerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface GebruikerServiceInterface extends UserDetailsService {
    GebruikerDto createUser(GebruikerDto user);
    GebruikerDto getUser(String email);
    GebruikerDto getUserByUserId(String userId);
    GebruikerDto updateGebruiker(String userId, GebruikerDto gebruiker);
    void deleteGebruiker(String userId);
}