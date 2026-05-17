package com.unbosque.paseadores.modules.paseo.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.paseo.mapper.PaseoRowMapper;
import com.unbosque.paseadores.modules.paseo.model.Paseo;
import com.unbosque.paseadores.modules.paseo.queries.PaseoQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaseoRepository {
    private final RelationalDatabaseService dbService;
    private final PaseoRowMapper mapper;

    public List<Paseo> findByOwnerId(
            Long ownerId
    ) {

        return dbService.query(
                PaseoQueries.FIND_BY_OWNER_ID,
                mapper,
                ownerId
        );
    }

    public List<Paseo> findByWalkerId(
            Long walkerId
    ) {

        return dbService.query(
                PaseoQueries.FIND_BY_WALKER_ID,
                mapper,
                walkerId
        );
    }
}