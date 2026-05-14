package com.unbosque.paseadores.core.database.relational.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RelationalDatabaseServiceImpl implements RelationalDatabaseService {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Override
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
        return jdbcTemplate.query(sql, mapper, params);
    }

    @Override
    public <T> T queryOne(String sql, RowMapper<T> mapper, Object... params) {
        List<T> result = jdbcTemplate.query(sql, mapper, params);

        return result.isEmpty()
                ? null
                : result.getFirst();
    }

    @Override
    public <T> T queryOneWithArray(
            String sql,
            RowMapper<T> mapper,
            Object[] params,
            String arrayType,
            Object[] arrayValues

    ) {

        return jdbcTemplate.execute(
                (Connection connection) -> {
                    PreparedStatement ps =
                            connection.prepareStatement(sql);

                    int index = 1;
                    for (Object param : params) {
                        ps.setObject(index++, param);
                    }

                    Array sqlArray =
                            connection.createArrayOf(
                                    arrayType,
                                    arrayValues
                            );
                    ps.setArray(index, sqlArray);
                    return ps;
                },

                (PreparedStatement ps) -> {
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return mapper.mapRow(rs, 1);
                    }
                    return null;
                }
        );
    }

    @Override
    public boolean exists(
            String sql,
            Object... params
    ) {
        Boolean result =
                jdbcTemplate.queryForObject(
                        sql,
                        Boolean.class,
                        params
                );

        return Boolean.TRUE.equals(result);
    }

    @Override
    public int update(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public <T> T executeTransaction(Supplier<T> action) {
        return transactionTemplate.execute(status -> {
            try {
                return action.get();
            } catch (Exception ex) {
                status.setRollbackOnly();
                throw ex;
            }
        });
    }
}
