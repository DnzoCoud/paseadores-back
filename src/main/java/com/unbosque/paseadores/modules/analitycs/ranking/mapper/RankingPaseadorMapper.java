package com.unbosque.paseadores.modules.analitycs.ranking.mapper;

import com.unbosque.paseadores.modules.analitycs.ranking.dto.RankingPaseadorResponseDto;
import com.unbosque.paseadores.modules.analitycs.ranking.model.RankingPaseador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RankingPaseadorMapper {

    RankingPaseadorResponseDto toResponse(
            RankingPaseador ranking
    );

    List<RankingPaseadorResponseDto> toResponseList(
            List<RankingPaseador> rankings
    );
}