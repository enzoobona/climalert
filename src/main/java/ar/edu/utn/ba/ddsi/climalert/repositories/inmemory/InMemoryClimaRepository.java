package ar.edu.utn.ba.ddsi.climalert.repositories.inmemory;

import ar.edu.utn.ba.ddsi.climalert.dtos.Clima;
import ar.edu.utn.ba.ddsi.climalert.repositories.ClimaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryClimaRepository implements ClimaRepository {

    private final List<Clima> historialDeClimas = new ArrayList<>();

    @Override
    public List<Clima> findAll() {
        return new ArrayList<>(historialDeClimas);
    }

    @Override
    public Optional<Clima> findLast() {
        return historialDeClimas.isEmpty()
            ? Optional.empty()
            : Optional.of(historialDeClimas.getLast());
    }

    @Override
    public Clima save(Clima clima) {
        if (clima.getId() == null) {
            clima.setId(historialDeClimas.toArray().length + 1);
            historialDeClimas.add(clima);
            return clima;
        }
        this.delete(clima);
        historialDeClimas.add(clima);

        return clima;
    }

    @Override
    public void delete(Clima clima) {
        if(clima.getId() == null) return;
        historialDeClimas.removeIf(c -> c.getId().equals(clima.getId()));
    }

}
