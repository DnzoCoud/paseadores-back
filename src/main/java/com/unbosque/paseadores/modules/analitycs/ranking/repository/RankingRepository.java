package com.unbosque.paseadores.modules.analitycs.ranking.repository;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.analitycs.ranking.mapper.RankingPaseadorRowMapper;
import com.unbosque.paseadores.modules.analitycs.ranking.model.RankingPaseador;
import com.unbosque.paseadores.modules.analitycs.ranking.queries.RankingQueries;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RankingRepository {

    private final RelationalDatabaseService dbService;

    private final RankingPaseadorRowMapper mapper;

    public List<RankingPaseador> findAll() {
        return dbService.query(
                RankingQueries.FIND_ALL,
                mapper
        );
    }

    public List<RankingPaseador> findTop10() {
        return dbService.query(
                RankingQueries.FIND_TOP_10,
                mapper
        );
    }

    public RankingPaseador findById(
            Long userId
    ) {
        return dbService.queryOne(
                RankingQueries.FIND_BY_ID,
                mapper,
                userId
        );
    }

    public void refreshRanking() {
        dbService.update(
                RankingQueries.REFRESH_RANKING
        );
    }
}