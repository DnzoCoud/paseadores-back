package com.unbosque.paseadores.modules.paseo.services;

import com.unbosque.paseadores.modules.paseo.dto.PaseoResponseDto;
import com.unbosque.paseadores.modules.paseo.mapper.PaseoMapper;
import com.unbosque.paseadores.modules.paseo.repository.PaseoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaseoService {
    private final PaseoRepository repository;
    private final PaseoMapper mapper;

    public List<PaseoResponseDto> findByOwnerId(
            Long ownerId
    ) {

        return mapper.toResponseList(
                repository.findByOwnerId(
                        ownerId
                )
        );
    }

    public List<PaseoResponseDto> findByWalkerId(
            Long walkerId
    ) {

        return mapper.toResponseList(
                repository.findByWalkerId(
                        walkerId
                )
        );
    }
}
