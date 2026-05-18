package com.unbosque.paseadores.modules.events.service;

import com.unbosque.paseadores.modules.events.model.EventDocument;
import com.unbosque.paseadores.modules.events.model.EventType;
import com.unbosque.paseadores.modules.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventTrackingService {

    private final EventRepository repository;

    @Async
    public void track(
            EventType type,
            Long userId,
            Map<String, Object> payload
    ) {
        try {
            EventDocument event =
                    EventDocument.builder()
                            .type(
                                    type.name()
                            )
                            .userId(
                                    userId
                            )
                            .timestamp(
                                    LocalDateTime.now()
                            )
                            .payload(
                                    payload
                            )
                            .build();

            repository.save(
                    event
            );

        } catch (Exception ex) {
            System.err.println(
                    "Error registrando evento Mongo: "
                            + ex.getMessage()
            );
        }
    }
}
