package com.unbosque.paseadores.modules.paseador.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.core.exceptions.NotFoundException;
import com.unbosque.paseadores.modules.paseador.mapper.PaseadorRowMapper;
import com.unbosque.paseadores.modules.paseador.model.Paseador;
import com.unbosque.paseadores.modules.paseador.queries.PaseadorQueries;
import com.unbosque.paseadores.modules.users.mapper.UserRowMapper;
import com.unbosque.paseadores.modules.users.models.User;
import com.unbosque.paseadores.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaseadorRepository {
    private final RelationalDatabaseService dbService;
    private final PaseadorRowMapper paseadorRowMapper;
    private final UserRepository userRepository;

    public Paseador save(Paseador paseador) {
        Paseador newPaseador = dbService.queryOne(
                PaseadorQueries.REGISTRAR_PASEADOR,
                paseadorRowMapper,
                paseador.getIdUsuario(),
                paseador.getDescripcion()
        );

        User user = userRepository.findById(newPaseador.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("El usuario no existe"));

        newPaseador.setUsuario(user);
        return newPaseador;
    }

    public List<Paseador> findAll() {
        return dbService.query(
                PaseadorQueries.FIND_ALL,
                paseadorRowMapper
        );
    }
}
