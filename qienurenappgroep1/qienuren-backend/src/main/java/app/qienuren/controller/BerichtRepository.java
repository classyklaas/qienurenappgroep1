package app.qienuren.controller;

import app.qienuren.model.Bedrijf;
import app.qienuren.model.Bericht;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface BerichtRepository extends CrudRepository<Bericht, Long> {

}
