package com.tripleAPI.mileageService.api.dto;

import com.tripleAPI.mileageService.web.domain.enums.Action;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class EventsRequest {

    @NotEmpty
    private String type;

    @NotNull
    private Action action;

    @NotNull
    private UUID reviewId;

    private String content;

    private List<UUID> attachedPhotoIds = new ArrayList<>();

    @NotNull
    private UUID userId;

    @NotNull
    private UUID placeId;
}
