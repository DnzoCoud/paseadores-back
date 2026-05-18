package com.unbosque.paseadores.core.database.mongo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoDatabaseServiceImpl implements MongoDatabaseService {

    private final MongoTemplate mongoTemplate;

    @Override
    public <T> T save(T document) {
        return mongoTemplate.save(
                document
        );
    }

    @Override
    public <T> List<T> find(Query query, Class<T> documentClass) {
        return mongoTemplate.find(
                query,
                documentClass
        );
    }

    @Override
    public <T> T findOne(Query query, Class<T> documentClass) {
        return mongoTemplate.findOne(
                query,
                documentClass
        );
    }

    @Override
    public <T> boolean exists(Query query, Class<T> documentClass) {
        return mongoTemplate.exists(
                query,
                documentClass
        );
    }

    @Override
    public <T> void delete(Query query, Class<T> documentClass) {
        mongoTemplate.remove(
                query,
                documentClass
        );
    }

    @Override
    public <T> T update(Query query, Update update, Class<T> documentClass) {
        mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options()
                        .returnNew(true),
                documentClass
        );

        return mongoTemplate.findOne(
                query,
                documentClass
        );
    }
}
