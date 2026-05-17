package com.unbosque.paseadores.modules.pets.service;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.core.exceptions.NotFoundException;
import com.unbosque.paseadores.modules.pets.dto.CreatePerroRequest;
import com.unbosque.paseadores.modules.pets.dto.PerroResponseDto;
import com.unbosque.paseadores.modules.pets.mapper.PerroMapper;
import com.unbosque.paseadores.modules.pets.model.Perro;
import com.unbosque.paseadores.modules.pets.repository.PerroRepository;
import com.unbosque.paseadores.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerroService {
    private final PerroRepository repository;
    private final PerroMapper mapper;
    private final UserService userService;
    private final RelationalDatabaseService dbService;

    public PerroResponseDto create(
            Long idDueno,
            CreatePerroRequest request
    ) {
        return dbService.executeTransaction(() -> {
            if (!userService.existsById(idDueno)) {
                throw new NotFoundException("El usuario no es dueño");
            }

            Perro perro =
                    mapper.toModel(request);
            perro.setIdDueno(idDueno);
            Perro savedPerro =
                    repository.create(perro);

            return mapper.toResponse(
                    savedPerro
            );
        });
    }

    public List<PerroResponseDto> findByOwnerId(
            Long ownerId
    ) {
        return mapper.toResponseList(
                repository.findByOwnerId(
                        ownerId
                )
        );
    }

    public void deleteByOwnerId(Long ownerId, Long perroId) {
        dbService.executeTransaction(() -> { repository.delete(ownerId, perroId); return null; });
    }
}
