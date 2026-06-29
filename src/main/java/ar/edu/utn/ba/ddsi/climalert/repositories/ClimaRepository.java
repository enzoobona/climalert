package ar.edu.utn.ba.ddsi.climalert.repositories;

import ar.edu.utn.ba.ddsi.climalert.domain.Clima;
import java.util.List;
import java.util.Optional;

public interface ClimaRepository {

    List<Clima> findAll();

    Optional<Clima> findLast();

    Clima save(Clima clima);

    void delete(Clima clima);

}
