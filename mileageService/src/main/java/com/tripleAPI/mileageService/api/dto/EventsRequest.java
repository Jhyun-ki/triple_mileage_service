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

    @NotEmpty(message = "type 값은 필수 입니다.")
    private String type;

    @NotNull(message = "action 값은 필수 입니다.")
    private Action action;

    @NotNull(message = "reviewId 값은 필수 입니다.")
    private UUID reviewId;

    private String content;

    private List<UUID> attachedPhotoIds = new ArrayList<>();

    @NotNull(message = "userId 값은 필수 입니다.")
    private UUID userId;

    @NotNull(message = "placeId 값은 필수 입니다.")
    private UUID placeId;
}
