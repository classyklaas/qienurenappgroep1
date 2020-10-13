package app.qienuren.controller;

import app.qienuren.model.UrenFormulier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UrenFormulierRepository extends CrudRepository<UrenFormulier, Long> {
}