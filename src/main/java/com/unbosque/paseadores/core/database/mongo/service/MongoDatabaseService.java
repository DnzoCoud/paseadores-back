package com.unbosque.paseadores.core.database.mongo.service;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface MongoDatabaseService {

    <T> T save(
            T document
    );

    <T> List<T> find(
            Query query,
            Class<T> documentClass
    );

    <T> T findOne(
            Query query,
            Class<T> documentClass
    );

    <T> boolean exists(
            Query query,
            Class<T> documentClass
    );

    <T> void delete(
            Query query,
            Class<T> documentClass
    );

    <T> T update(
            Query query,
            Update update,
            Class<T> documentClass
    );
}
