package app.qienuren.controller;

import app.qienuren.model.Bedrijf;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface BedrijfRepository extends CrudRepository<Bedrijf, Long> {

    @Query(value = "FROM Bedrijf WHERE bedrijfsNaam=?1" )
    Iterable<Bedrijf> findByNaam(String bedrijfsNaam);
}
