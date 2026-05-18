package com.unbosque.paseadores.modules.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "events")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDocument {

    @Id
    private String id;

    private String type;

    private Long userId;

    private LocalDateTime timestamp;

    private Map<String, Object> payload;
}
