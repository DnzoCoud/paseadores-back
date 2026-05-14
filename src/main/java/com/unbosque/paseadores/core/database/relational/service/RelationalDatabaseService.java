package com.unbosque.paseadores.core.database.relational.service;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.function.Supplier;

public interface RelationalDatabaseService {
    <T> List<T> query(
            String sql,
            RowMapper<T> mapper,
            Object... params
    );

    <T> T queryOne(
            String sql,
            RowMapper<T> mapper,
            Object... params
    );

    boolean exists(
            String sql,
            Object... params
    );

    <T> T queryOneWithArray(
            String sql,
            RowMapper<T> mapper,
            Object[] params,
            String arrayType,
            Object[] arrayValues
    );

    int update(
            String sql,
            Object... params
    );

    <T> T executeTransaction(
            Supplier<T> action
    );
}
