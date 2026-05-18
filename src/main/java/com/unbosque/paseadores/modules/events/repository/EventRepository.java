package com.unbosque.paseadores.modules.events.repository;

import com.unbosque.paseadores.core.database.mongo.service.MongoDatabaseService;
import com.unbosque.paseadores.modules.events.model.EventDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final MongoDatabaseService mongoService;

    public EventDocument save(EventDocument event) {
        return mongoService.save(
                event
        );
    }

    public List<EventDocument> findByUserId(
            Long userId
    ) {
        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId").is(userId)
        );

        return mongoService.find(
                query,
                EventDocument.class
        );
    }

    public List<EventDocument> findByType(
            String type
    ) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("type")
                        .is(type)
        );

        return mongoService.find(
                query,
                EventDocument.class
        );
    }

    public List<EventDocument> findRecent() {

        Query query = new Query();

        query.with(
                Sort.by(
                        Sort.Direction.DESC,
                        "timestamp"
                )
        );

        query.limit(20);
        return mongoService.find(
                query,
                EventDocument.class
        );
    }
}
