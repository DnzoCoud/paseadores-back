package com.unbosque.paseadores.modules.walktracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "walk_tracking")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalkTrackingDocument {

    @Id
    private String id;

    private Long walkId;

    private List<RoutePoint> route;
}