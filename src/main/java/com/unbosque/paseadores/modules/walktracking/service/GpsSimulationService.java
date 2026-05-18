package com.unbosque.paseadores.modules.walktracking.service;

import com.unbosque.paseadores.core.database.mongo.service.MongoDatabaseService;
import com.unbosque.paseadores.modules.events.model.EventType;
import com.unbosque.paseadores.modules.events.service.EventTrackingService;
import com.unbosque.paseadores.modules.paseo.repository.PaseoRepository;
import com.unbosque.paseadores.modules.walktracking.model.RoutePoint;
import com.unbosque.paseadores.modules.walktracking.model.WalkTrackingDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GpsSimulationService {
    private final MongoDatabaseService mongoService;
    private final PaseoRepository paseoRepository;
    private final EventTrackingService eventService;
    private final TaskScheduler taskScheduler;
    private final Random random = new Random();

    public void startTracking(
            Long walkId
    ) {

        taskScheduler.scheduleAtFixedRate(
                () -> generatePoint(walkId),
                Duration.ofSeconds(5)
        );
    }

    private void generatePoint(
            Long walkId
    ) {

        try {

            boolean finished =
                    paseoRepository.isFinished(
                            walkId
                    );

            if (finished) {
                return;
            }

            RoutePoint point =
                    RoutePoint.builder()
                            .lat(
                                    generateLatitude()
                            )
                            .lng(
                                    generateLongitude()
                            )
                            .timestamp(
                                    LocalDateTime.now()
                            )
                            .build();

            Query query = new Query();

            query.addCriteria(
                    Criteria.where("walkId")
                            .is(walkId)
            );

            WalkTrackingDocument existing =
                    mongoService.findOne(
                            query,
                            WalkTrackingDocument.class
                    );

            if (existing == null) {

                WalkTrackingDocument document =
                        WalkTrackingDocument.builder()
                                .walkId(walkId)
                                .route(
                                        new ArrayList<>(
                                                List.of(point)
                                        )
                                )
                                .build();

                mongoService.save(
                        document
                );

            } else {

                Update update = new Update();

                update.push(
                        "route",
                        point
                );

                mongoService.update(
                        query,
                        update,
                        WalkTrackingDocument.class
                );
            }

            eventService.track(

                    EventType.GPS_UPDATED,

                    walkId,

                    Map.of(
                            "lat",
                            point.getLat(),

                            "lng",
                            point.getLng()
                    )
            );

        } catch (Exception ex) {

            System.err.println(
                    ex.getMessage()
            );
        }
    }

    private Double generateLatitude() {

        return 4.60 +
                (random.nextDouble() * 0.01);
    }

    private Double generateLongitude() {

        return -74.08 +
                (random.nextDouble() * 0.01);
    }
}
