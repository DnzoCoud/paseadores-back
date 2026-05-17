package com.unbosque.paseadores.modules.pets.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.pets.mapper.PerroRowMapper;
import com.unbosque.paseadores.modules.pets.model.Perro;
import com.unbosque.paseadores.modules.pets.queries.PerroQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerroRepository {
    private final RelationalDatabaseService dbService;
    private final PerroRowMapper mapper;

    public Perro create(
            Perro perro
    ) {
        return dbService.queryOne(
                PerroQueries.CREATE_PERRO,
                mapper,
                perro.getNombre(),
                perro.getRaza(),
                perro.getEdad(),
                perro.getPeso(),
                perro.getObservaciones(),
                perro.getFoto(),
                perro.getIdDueno(),
                true
        );
    }

    public List<Perro> findByOwnerId(
            Long ownerId
    ) {
        return dbService.query(
                PerroQueries.FIND_BY_OWNER_ID,
                mapper,
                ownerId
        );
    }

    public boolean existsByOwnerId(Long ownerId) {
        return dbService.exists(PerroQueries.EXISTS_BY_ID_AND_OWNER, ownerId);
    }

    public void delete(Long ownerId, Long perroId) {
        dbService.delete(PerroQueries.DELETE, perroId, ownerId);
    }
}
