package com.tripleAPI.mileageService.web.controller;

import lombok.Data;
import java.util.UUID;

@Data
public class ReadyReviewForm {
    private UUID reviewId;
    private UUID userId;
    private UUID placeId;
    private String content;
    private int photoCount;

}
