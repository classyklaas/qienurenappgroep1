package app.qienuren.rest;

import app.qienuren.controller.GebruikerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BerichtEndpoint {
    @Autowired
    GebruikerService gebruikerService;
}
