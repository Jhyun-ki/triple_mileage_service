package com.tripleAPI.mileageService.api.dto;
import com.tripleAPI.mileageService.web.domain.enums.PointState;
import com.tripleAPI.mileageService.web.domain.enums.PointType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class PointListByUserId {
    private UUID pointId;
    private int pointAmt;
    private UUID reviewId;
    private PointState pointState;
    private PointType pointType;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}