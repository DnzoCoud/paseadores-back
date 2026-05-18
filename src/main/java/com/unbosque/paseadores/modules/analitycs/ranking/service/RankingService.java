package com.unbosque.paseadores.modules.analitycs.ranking.service;

import com.unbosque.paseadores.modules.analitycs.ranking.dto.RankingPaseadorResponseDto;
import com.unbosque.paseadores.modules.analitycs.ranking.mapper.RankingPaseadorMapper;
import com.unbosque.paseadores.modules.analitycs.ranking.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository repository;
    private final RankingPaseadorMapper mapper;

    public List<RankingPaseadorResponseDto> findAll() {

        return mapper.toResponseList(
                repository.findAll()
        );
    }

    public List<RankingPaseadorResponseDto> findTop10() {

        return mapper.toResponseList(
                repository.findTop10()
        );
    }

    public RankingPaseadorResponseDto findById(
            Long userId
    ) {

        return mapper.toResponse(
                repository.findById(
                        userId
                )
        );
    }

    public void refreshRanking() {
        repository.refreshRanking();
    }
}
