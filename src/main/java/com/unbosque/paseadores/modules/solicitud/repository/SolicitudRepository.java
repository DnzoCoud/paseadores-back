package com.unbosque.paseadores.modules.solicitud.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.paseador.queries.PaseadorQueries;
import com.unbosque.paseadores.modules.solicitud.dto.CreateSolicitudRequest;
import com.unbosque.paseadores.modules.solicitud.mapper.SolicitudEstadoRowMapper;
import com.unbosque.paseadores.modules.solicitud.mapper.SolicitudRowMapper;
import com.unbosque.paseadores.modules.solicitud.model.Solicitud;
import com.unbosque.paseadores.modules.solicitud.model.SolicitudEstado;
import com.unbosque.paseadores.modules.solicitud.queries.SolicitudQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SolicitudRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RelationalDatabaseService dbService;
    private final SolicitudRowMapper mapper;
    private final SolicitudEstadoRowMapper estadoMapper;

    public Long create(
            Long idDueno,
            CreateSolicitudRequest request
    ) {

        return jdbcTemplate.execute(

                (Connection connection) -> {

                    CallableStatement cs =
                            connection.prepareCall(
                                SolicitudQueries.CREATE_SOLICITUD
                            );

                    cs.setTime(
                            1,
                            Time.valueOf(
                                    request.horaSugerida()
                            )
                    );

                    cs.setString(
                            2,
                            request.puntoEncuentro()
                    );

                    cs.setString(
                            3,
                            request.observaciones()
                    );

                    cs.setLong(
                            4,
                            idDueno
                    );

                    cs.setLong(
                            5,
                            request.idPaseador()
                    );

                    Array perrosArray =
                            connection.createArrayOf(

                                    "bigint",

                                    request.perros().toArray()
                            );

                    cs.setArray(
                            6,
                            perrosArray
                    );

                    cs.registerOutParameter(
                            7,
                            Types.BIGINT
                    );

                    return cs;
                },

                (CallableStatement cs) -> {

                    cs.execute();

                    return cs.getLong(7);
                }
        );
    }

    public Solicitud findById(
            Long idSolicitud
    ) {

        return dbService.queryOne(

                SolicitudQueries.FIND_BY_ID,

                mapper,

                idSolicitud
        );
    }


    public Long aceptar(
            Long walkerId,
            Long requestId
    ) {

        return jdbcTemplate.execute(
                connection -> {
                    CallableStatement cs = connection.prepareCall(SolicitudQueries.ACEPTAR_SOLICITUD);
                    cs.setLong(1, requestId);
                    cs.setLong(2, walkerId);
                    cs.setLong(3, 0L);
                    cs.registerOutParameter(
                            3,
                            Types.BIGINT
                    );
                    return cs;
                },
                (CallableStatementCallback<Long>) callableStatement -> {
                    callableStatement.execute();
                    return callableStatement.getLong(3);
                }
        );
    }

    public void rechazar(
            Long walkerId,
            Long requestId
    ) {

        jdbcTemplate.update(

                connection -> {
                    CallableStatement cs =
                            connection.prepareCall(SolicitudQueries.RECHAZAR_SOLICITUD);
                    cs.setLong(
                            1,
                            requestId
                    );
                    cs.setLong(
                            2,
                            walkerId
                    );
                    return cs;
                }
        );
    }

    public void cancelar(
            Long ownerId,
            Long requestId
    ) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement cs =
                            connection.prepareCall(SolicitudQueries.CANCELAR_SOLICITUD);

                    cs.setLong(
                            1,
                            requestId
                    );

                    cs.setLong(
                            2,
                            ownerId
                    );

                    return cs;
                }
        );
    }

    public SolicitudEstado findEstadoById(
            Long idSolicitud
    ) {
        return dbService.queryOne(
                SolicitudQueries.FIND_ESTADO_BY_ID,
                estadoMapper,
                idSolicitud
        );
    }

    public List<Solicitud> findByWalkerId(
            Long walkerId
    ) {

        return dbService.query(

                SolicitudQueries.FIND_BY_WALKER_ID,

                mapper,

                walkerId
        );
    }

    public List<Solicitud> findByOwnerId(
            Long ownerId
    ) {
        return dbService.query(
                SolicitudQueries.FIND_BY_OWNER_ID,
                mapper,
                ownerId
        );
    }
}
