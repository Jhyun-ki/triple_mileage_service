package com.tripleAPI.mileageService.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PointListByUserIdResponse {
    private UUID userId;
    private String userName;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int pointBalance;

    private List<PointListByUserId> point = new ArrayList<>();
}
