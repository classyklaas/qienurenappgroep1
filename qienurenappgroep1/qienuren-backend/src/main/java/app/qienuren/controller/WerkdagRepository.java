package app.qienuren.controller;

import app.qienuren.model.Werkdag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface WerkdagRepository extends CrudRepository<Werkdag, Long> {
}
