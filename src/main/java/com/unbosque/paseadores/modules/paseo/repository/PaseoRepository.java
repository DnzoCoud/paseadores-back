package com.unbosque.paseadores.modules.paseo.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.paseo.dto.CreateCalificacionRequest;
import com.unbosque.paseadores.modules.paseo.mapper.PaseoEstadoRowMapper;
import com.unbosque.paseadores.modules.paseo.mapper.PaseoRowMapper;
import com.unbosque.paseadores.modules.paseo.model.Paseo;
import com.unbosque.paseadores.modules.paseo.model.PaseoEstado;
import com.unbosque.paseadores.modules.paseo.queries.PaseoQueries;
import com.unbosque.paseadores.modules.solicitud.queries.SolicitudQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaseoRepository {
    private final RelationalDatabaseService dbService;
    private final PaseoRowMapper mapper;
    private final PaseoEstadoRowMapper estadoMapper;
    private final JdbcTemplate jdbcTemplate;

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

    public void finalizar(
            Long paseoId,
            Long walkerId
    ) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement cs =
                            connection.prepareCall(PaseoQueries.FINALIZAR_PASEO);

                    cs.setLong(
                            1,
                            paseoId
                    );

                    cs.setLong(
                            2,
                            walkerId
                    );

                    return cs;
                }
        );
    }

    public PaseoEstado findEstadoById(
            Long paseoId
    ) {

        return dbService.queryOne(
                PaseoQueries.FIND_ESTADO_BY_ID,
                estadoMapper,
                paseoId
        );
    }

    public void calificar(
            Long paseoId,
            Long ownerId,
            CreateCalificacionRequest request

    ) {

        jdbcTemplate.execute(
                (CallableStatementCreator) connection -> {

                    CallableStatement cs =
                            connection.prepareCall(PaseoQueries.CALIFICAR_PASEO);

                    cs.setLong(
                            1,
                            paseoId
                    );

                    cs.setLong(
                            2,
                            ownerId
                    );

                    cs.setInt(
                            3,
                            request.puntaje()
                    );

                    cs.setString(
                            4,
                            request.comentario()
                    );

                    return cs;
                },
                callableStatement -> {
                    callableStatement.execute();
                    return null;
                }
        );
    }

    public boolean isFinished(
            Long walkId
    ) {

        Boolean result =
                jdbcTemplate.queryForObject(
                        PaseoQueries.PASEO_FINALIZADO,
                        Boolean.class,
                        walkId
                );

        return Boolean.TRUE.equals(result);
    }
}
