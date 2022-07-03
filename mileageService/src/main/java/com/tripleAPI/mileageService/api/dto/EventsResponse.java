package com.tripleAPI.mileageService.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class EventsResponse {
    private UUID reviewId;
    private UUID userId;
    private int pointBalance;

    public EventsResponse(){}

    public EventsResponse(UUID reviewId, UUID userId, int pointBalance) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.pointBalance = pointBalance;
    }
}
