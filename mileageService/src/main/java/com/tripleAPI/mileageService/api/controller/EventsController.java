package com.tripleAPI.mileageService.api.controller;


import com.tripleAPI.mileageService.api.dto.CommonResponse;
import com.tripleAPI.mileageService.api.dto.EventsRequest;
import com.tripleAPI.mileageService.api.dto.EventsResponse;
import com.tripleAPI.mileageService.api.service.EventsService;
import com.tripleAPI.mileageService.exception.CommonException;
import com.tripleAPI.mileageService.web.service.MemberService;
import com.tripleAPI.mileageService.web.service.PlaceService;
import com.tripleAPI.mileageService.web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class EventsController {
    private final MemberService memberService;
    private final PlaceService placeService;
    private final ReviewService reviewService;
    private final EventsService eventsService;

    //리뷰 추가 이벤트
    //리뷰 추가에 대한 point지급 api를 호출함.
    @PostMapping("/events")
    public ResponseEntity events(@RequestBody @Valid EventsRequest eventsRequest) {
        EventsResponse eventsResponse;

        switch (eventsRequest.getAction()) {
            case ADD :
                eventsResponse = eventsService.addReview(eventsRequest);
                break;
            case MOD :
                eventsResponse = eventsService.modReview(eventsRequest);
                break;
            case DELETE:
                eventsResponse = eventsService.deleteReview(eventsRequest);
                break;
            default:
                throw new CommonException("지원하는 Action이 아닙니다.");
        }

        return new ResponseEntity<>(new CommonResponse(0, "OK", eventsResponse), HttpStatus.OK);
    }
}
