package com.unbosque.paseadores.modules.direccion.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.direccion.mapper.DireccionRowMapper;
import com.unbosque.paseadores.modules.direccion.model.Direccion;
import com.unbosque.paseadores.modules.direccion.queries.DireccionQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DireccionRepository {

    private final RelationalDatabaseService dbService;

    private final DireccionRowMapper mapper;

    public Direccion create(
            Direccion direccion
    ) {

        return dbService.queryOne(
                DireccionQueries.CREATE_DIRECCION,
                mapper,
                direccion.getDetalle(),
                direccion.getBarrio(),
                direccion.getCiudad(),
                direccion.getLatitud(),
                direccion.getLongitud()
        );
    }

    public void createOwnerProfile(
            Long userId,
            Long idDireccion
    ) {

        dbService.update(
                DireccionQueries.CREATE_DUENO,
                userId,
                idDireccion
        );
    }

    public List<Direccion> findByUserId(
            Long userId
    ) {
        return dbService.query(
                DireccionQueries.FIND_BY_USER_ID,
                mapper,
                userId
        );
    }
}
